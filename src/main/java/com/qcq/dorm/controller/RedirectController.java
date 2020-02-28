package com.qcq.dorm.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qcq.dorm.dto.BedDetailDTO;
import com.qcq.dorm.dto.DormChangeFormDTO;
import com.qcq.dorm.dto.DormSelectionDTO;
import com.qcq.dorm.dto.FixFormDTO;
import com.qcq.dorm.entity.*;
import com.qcq.dorm.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * @author O
 * @since 2020/2/18
 */
@Controller
public class RedirectController {
    @Resource
    private BuildingService buildingService;
    @Resource
    private StudentService studentService;
    @Resource
    private DormChangeFormService dormChangeFormService;
    @Resource
    private FixFormService fixFormService;
    @Resource
    private BedService bedService;
    @Resource
    private DormService dormService;
    @Resource
    private ItemService itemService;
    @Resource
    private DormOfficerService officerService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/index")
    public String index1() {
        return "index";
    }

    @GetMapping("/studentRegister")


    public String studentRegister() {
        return "studentRegister";
    }

    @GetMapping("/dormOfficerRegister")
    public String dormOfficerRegister(Model model) {
        model.addAttribute("buildings", buildingService.selectList(new EntityWrapper<>()));
        return "dormOfficerRegister";
    }

    @GetMapping("/studentIndex")
    public String studentIndex(Long id, Model model) {
        final Student student = studentService.selectById(id);
        final Bed bed = bedService.selectById(student.getBedId());
        final Dorm dorm = dormService.selectById(bed.getDormId());
        final Building building = buildingService.selectById(dorm.getBuildingId());
        model.addAttribute("student", student);
        model.addAttribute("id", id);
        model.addAttribute("dormChangeFormCount", dormChangeFormService.getDormChangeFormCount(id));
        model.addAttribute("fixFormCount", fixFormService.getDormFixForms(dorm.getId()).size());
        model.addAttribute("electricityBill", dorm.getElectricityBill());
        model.addAttribute("building", building);
        final Integer cleanScore = dorm.getCleanScore();
        model.addAttribute("cleanScore", cleanScore == null ? "未评比" : cleanScore);
        model.addAttribute("dorm", dorm);
        model.addAttribute("bedNumber", student.getBedId() == null ? "未选择宿舍" : bed.getNumber());
        boolean isMoveIn;
        if (student.getBedId() == null) {
            isMoveIn = false;
        } else {
            isMoveIn = bed.getIsMoveIn() == 1;
        }
        model.addAttribute("isMoveIn", isMoveIn ? "是" : "否");
        model.addAttribute("isSelectDorm", student.getBedId() == null ? "否" : "是");
        return "studentIndex";
    }

    @GetMapping("/studentTables")
    public String studentTables(Long id, Model model) {
        final Student student = studentService.selectById(id);
        List<BedDetailDTO> bedsDetail = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        List<FixForm> fixForms;
        List<DormChangeFormDTO> dormChangeForms = new ArrayList<>();
        List<FixFormDTO> fixFormDTOList = new ArrayList<>();
        if (student.getBedId() != null) {
            final Bed bed = bedService.selectById(student.getBedId());
            final EntityWrapper<Bed> bedWrapper = new EntityWrapper<>();
            bedWrapper.eq("dorm_id", bed.getDormId());
            final List<Bed> beds = bedService.selectList(bedWrapper);
            bedsDetail = toBedDetail(beds);
            final EntityWrapper<Item> itemWrapper = new EntityWrapper<>();
            itemWrapper.eq("dorm_id", bed.getDormId());
            items = itemService.selectList(itemWrapper);
            final EntityWrapper<FixForm> fixFormWrapper = new EntityWrapper<>();
            fixFormWrapper.in("item_id", items.stream().map(Item::getId).collect(Collectors.toList()));
            if (items.size() < 1) {
                fixForms = new ArrayList<>();
            } else {
                fixForms = fixFormService.selectList(fixFormWrapper);
            }
            final Map<Long, Item> itemMap = items.stream().collect(toMap(Item::getId, item -> item));
            fixFormDTOList = fixForms.stream()
                    .map(fixForm -> {
                        int tag;
                        //待完成
                        if (fixForm.getState() < 2) {
                            tag = 0;
                        } else if (fixForm.getSatisfaction() != null) {
                            tag = 2;
                        } else {
                            tag = 1;
                        }
                        return FixFormDTO.builder()
                                .itemName(itemMap.get(fixForm.getItemId()).getName())
                                .desc(fixForm.getDesc())
                                .satisfactionTag(tag)
                                .submitTime(fixForm.getSubmitTime())
                                .id(fixForm.getId())
                                .finishTime(fixForm.getFinishTime())
                                .satisfaction(fixForm.getSatisfaction())
                                .state(fixForm.getState())
                                .build();
                    }).collect(Collectors.toList());
            final EntityWrapper<DormChangeForm> dormChangeFormWrapper = new EntityWrapper<>();
            dormChangeFormWrapper.eq("student_id", id);
            dormChangeForms = dormChangeFormService.selectList(dormChangeFormWrapper)
                    .stream()
                    .map(this::getDormChangeFormDetail).collect(Collectors.toList());

        }

        model.addAttribute("dormChangeForms", dormChangeForms);
        model.addAttribute("items", items);
        model.addAttribute("beds", bedsDetail);
        model.addAttribute("student", student);
        model.addAttribute("fixForms", fixFormDTOList);
        return "studentTables";
    }

    @GetMapping("/studentForms")
    public String studentForms(Long id, Model model) {
        final Student student = studentService.selectById(id);
        List<Item> items = new ArrayList<>();
        final List<Building> buildings = buildingService.selectList(new EntityWrapper<>());
        if (student.getBedId() != null) {
            final Bed bed = bedService.selectById(student.getBedId());
            final Dorm dorm = dormService.selectById(bed.getDormId());
            final EntityWrapper<Item> itemWrapper = new EntityWrapper<>();
            itemWrapper.eq("dorm_id", bed.getDormId());
            items = itemService.selectList(itemWrapper);

        }

        model.addAttribute("buildings", buildings);
        model.addAttribute("items", items);
        model.addAttribute("student", student);
        return "studentForms";
    }

    @GetMapping("/dormOfficerIndex")
    public String dormOfficerIndex(Long id, Model model) {
        final DormOfficer officer = officerService.selectById(id);
        final List<Student> students = buildingService.getStudents(officer.getBuildingId());
        final EntityWrapper<Bed> bedWrapper = new EntityWrapper<>();
        final List<Bed> beds;
        bedWrapper.in("id", students.stream().map(Student::getBedId).collect(Collectors.toList()));
        if (students.size() < 1) {
            beds = new ArrayList<>();
        } else {
            beds = bedService.selectList(bedWrapper);
        }
        final List<FixForm> buildingFixForms = fixFormService.getBuildingFixForms(officer.getBuildingId());
        final List<DormChangeForm> dormChangeForms = dormChangeFormService.getDormChangeForms(officer.getBuildingId());
        model.addAttribute("totalCount", students.size());
        model.addAttribute("moveInCount", beds.stream().filter(bed -> bed.getIsMoveIn() == 1).count());
        model.addAttribute("fixFormCount", buildingFixForms.size());
        model.addAttribute("dormChangeFormCount", dormChangeForms.size());
        model.addAttribute("officer", officer);
        return "dormOfficerIndex";
    }

    @GetMapping("/dormFragment")
    public String dormFragment(Long buildingId, Model model) {
        final EntityWrapper<Dorm> dormWrapper = new EntityWrapper<>();
        dormWrapper.eq("building_id", buildingId);
        final List<Dorm> dorms = dormService.selectList(dormWrapper);

        model.addAttribute("dorms", dorms);
        return "studentForms::dormFragment";
    }

    @GetMapping("/bedFragment")
    public String bedFragment(Long dormId, Model model) {
        final EntityWrapper<Bed> bedWrapper = new EntityWrapper<>();
        bedWrapper.eq("dorm_id", dormId);
        final List<Bed> beds = bedService.selectList(bedWrapper)
                .stream()
                .filter(bed -> bed.getIsSelected() == 0)
                .collect(Collectors.toList());
        model.addAttribute("beds", beds);
        return "studentForms::bedFragment";
    }

    @GetMapping("/chooseDorm")
    public String chooseDorm(Long id, Model model) {
        final Student student = studentService.selectById(id);
        final DormSelectionDTO selectionStatus = dormService.getSelectionStatus();
        LocalDateTime date = LocalDateTime.parse(selectionStatus.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        //活动开启没有过期
        if (selectionStatus.getIsOpen() && date.compareTo(LocalDateTime.now()) > 0) {
            final List<Building> buildings = buildingService.selectList(new EntityWrapper<>());
            model.addAttribute("pageTag", 3);
            model.addAttribute("buildings", buildings);
        } else if (selectionStatus.getIsOpen() && date.compareTo(LocalDateTime.now()) < 0) {
            //活动开启并且过期
            model.addAttribute("pageTag", 2);
        } else {
            //活动关闭
            model.addAttribute("pageTag", 1);
        }
        model.addAttribute("selectionStatus", selectionStatus);
        model.addAttribute("student", student);
        return "chooseDorm";
    }

    @GetMapping("/dormFragment4Selection")
    public String dormFragment4Selection(Long buildingId, Model model) {
        final EntityWrapper<Dorm> dormWrapper = new EntityWrapper<>();
        dormWrapper.eq("building_id", buildingId);
        final List<Dorm> dorms = dormService.selectList(dormWrapper);

        model.addAttribute("dorms", dorms);
        return "chooseDorm::dormFragment";
    }

    @GetMapping("/bedFragment4Selection")
    public String bedFragment4Selection(Long dormId, Model model) {
        List<BedDetailDTO> bedsDetail;
        final EntityWrapper<Bed> bedWrapper = new EntityWrapper<>();
        bedWrapper.eq("dorm_id", dormId);
        final List<Bed> beds = bedService.selectList(bedWrapper);
        bedsDetail = toBedDetail(beds);
        model.addAttribute("beds", bedsDetail);
        return "chooseDorm::bedFragment";
    }

    @GetMapping("/bedFragment4Officer")
    public String bedFragment4Officer(Long dormId, Model model) {
        List<BedDetailDTO> bedsDetail;
        final EntityWrapper<Bed> bedWrapper = new EntityWrapper<>();
        bedWrapper.eq("dorm_id", dormId);
        final List<Bed> beds = bedService.selectList(bedWrapper);
        bedsDetail = toBedDetail(beds);
        model.addAttribute("beds", bedsDetail);
        return "officerTables::bedFragment";
    }

    private List<BedDetailDTO> toBedDetail(List<Bed> beds) {
        return beds.stream()
                .map(b -> {
                    final EntityWrapper<Student> stuWrapper = new EntityWrapper<>();
                    stuWrapper.eq("bed_id", b.getId());
                    final Student bedOwner = studentService.selectOne(stuWrapper);
                    String ownerName = bedOwner == null ? "" : bedOwner.getName();
                    String habit = bedOwner == null ? "" : bedOwner.getHabit();
                    return BedDetailDTO.builder()
                            .id(b.getId())
                            .isMoveIn(b.getIsMoveIn() == 1)
                            .number(b.getNumber())
                            .isSelected(b.getIsSelected() == 1)
                            .ownerName(ownerName)
                            .isSmoke(bedOwner != null ? bedOwner.getIsSmoke() == 1 : null)
                            .isEarlySleep(bedOwner != null ? bedOwner.getIsEarlySleep() == 1 : null)
                            .habit(habit)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/officerTables")
    public String officerTables(Long id, Model model) {
        final DormOfficer officer = officerService.selectById(id);
        final List<Building> buildings = buildingService.selectList(new EntityWrapper<>());
        final List<Dorm> dorms = dormService.getDorms(officer.getBuildingId());


        model.addAttribute("dorms", dorms);
        model.addAttribute("officer", officer);
        model.addAttribute("buildings", buildings);
        return "officerTables";
    }

    @GetMapping("/officerForms")
    public String officerForms(Long id, Model model) {
        final DormOfficer officer = officerService.selectById(id);
        final List<Dorm> dorms = dormService.getDorms(officer.getBuildingId());
        final List<Long> dormIds = dorms.stream().map(Dorm::getId).collect(Collectors.toList());
        final List<Student> students = buildingService.getStudents(officer.getBuildingId());
        List<Item> items;
        final EntityWrapper<Item> itemWrapper = new EntityWrapper<>();
        itemWrapper.in("dorm_id", dormIds);
        if (dorms.size() < 1) {
            items = new ArrayList<>();
        } else {
            items = itemService.selectList(itemWrapper);
        }
        final Map<Long, Item> itemMap = items.stream().collect(toMap(Item::getId, item -> item));
        final Map<Long, Dorm> dormMap = dorms.stream().collect(toMap(Dorm::getId, dorm -> dorm));
        final List<FixForm> fixForms = fixFormService.getBuildingFixForms(officer.getBuildingId());
        final List<FixFormDTO> fixFormDTOS = fixForms.stream().map(form -> FixFormDTO.builder()
                .desc(form.getDesc())
                .state(form.getState())
                .satisfaction(form.getSatisfaction())
                .submitTime(form.getSubmitTime())
                .finishTime(form.getSubmitTime())
                .itemName(itemMap.get(form.getItemId()).getName())
                .dormNumber(dormMap.get(itemMap.get(form.getItemId()).getDormId()).getNumber())
                .id(form.getId())
                .build()
        )
                .sorted(Comparator.comparingLong(FixFormDTO::getDormNumber))
                .collect(Collectors.toList());

        List<DormChangeFormDTO> dormChangeForms = new ArrayList<>();

        final EntityWrapper<DormChangeForm> dormChangeFormWrapper = new EntityWrapper<>();
        dormChangeFormWrapper.in("student_id", students.stream().map(Student::getId).collect(Collectors.toList()));
        if (students.size() > 0) {
            dormChangeForms = dormChangeFormService.selectList(dormChangeFormWrapper)
                    .stream()
                    .map(this::getDormChangeFormDetail).collect(Collectors.toList());
        }

        model.addAttribute("dormChangeForms", dormChangeForms);
        model.addAttribute("fixForms", fixFormDTOS);
        model.addAttribute("officer", officer);
        model.addAttribute("selectionStatus", dormService.getSelectionStatus());
        return "officerForms";
    }

    private DormChangeFormDTO getDormChangeFormDetail(DormChangeForm form) {
        final Bed oldBed = bedService.selectById(form.getOldBedId());
        final Dorm oldDorm = dormService.selectById(oldBed.getDormId());
        final Building oldBuilding = buildingService.selectById(oldDorm.getBuildingId());
        final Student student = studentService.selectById(form.getStudentId());
        final Bed newBed = bedService.selectById(form.getNewBedId());
        final Dorm newDorm = dormService.selectById(newBed.getDormId());
        final Building newBuilding = buildingService.selectById(newDorm.getBuildingId());
        return DormChangeFormDTO.builder()
                .id(form.getId())
                .oldLocation(oldBuilding.getName() + " - " + oldDorm.getNumber() + " - " + oldBed.getNumber() + '床')
                .newLocation(newBuilding.getName() + " - " + newDorm.getNumber() + " - " + newBed.getNumber() + '床')
                .reason(form.getReason())
                .name(student.getName())
                .state(form.getState())
                .build();
    }
}
