package com.qcq.dorm.bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Cat {
	@NotNull
	private String name;
	@Max(value = 10,message = "年龄最大不能高于10")
	@Min(value = 0,message = "年龄最小不能低于0")
	private int age;

	@Override
	public String toString() {
		return "Cat{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
