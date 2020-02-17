package com.qcq.dorm.enums;

import lombok.Getter;

public enum UserEnum {
    STUDENT("学生"), DORM_OFFICER("宿管");

    UserEnum(String name) {
        this.name = name;
    }


    @Getter
    private String name;
}
