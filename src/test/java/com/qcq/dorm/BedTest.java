package com.qcq.dorm;

import com.qcq.dorm.entity.Bed;
import com.qcq.dorm.entity.Student;
import com.qcq.dorm.response.CommonResult;
import com.qcq.dorm.service.BedService;
import com.qcq.dorm.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@Rollback
public class BedTest {
    @Resource
    private BedService bedService;
    @Resource
    private StudentService studentService;

    @Test
    public void select() throws Exception {
        CountDownLatch latch = new CountDownLatch(5);
        CyclicBarrier barrier = new CyclicBarrier(5);
        final Bed bed = bedService.selectById(6);
        System.out.println("竞争的床位信息: " + bed);

        for (int i = 1; i <= 5; i++) {
            final Thread thread = new Thread(new Request((long) i, 6L, studentService, latch, barrier));
            thread.setName(String.valueOf(i));
            thread.start();
        }
        barrier.await();
    }
}

class Request implements Runnable {
    private Long stuId;
    private Long bedId;
    private StudentService studentService;
    private CountDownLatch latch;
    private CyclicBarrier barrier;

    public Request(Long stuId, Long bedId, StudentService studentService, CountDownLatch latch, CyclicBarrier barrier) {
        this.barrier = barrier;
        this.stuId = stuId;
        this.bedId = bedId;
        this.studentService = studentService;
        this.latch = latch;
    }

    @Override
    @Transactional
    @Rollback
    public void run() {
        latch.countDown();
        System.out.println("latch: " + latch.getCount());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程 " + Thread.currentThread().getName() + " 开始执行");
        try {
            final CommonResult commonResult = studentService.chooseBed(stuId, bedId);
            System.out.println("线程 " + Thread.currentThread().getName() + " 结果: " + commonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}