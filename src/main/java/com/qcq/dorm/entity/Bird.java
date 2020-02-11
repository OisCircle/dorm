package com.qcq.dorm.entity;

import com.baomidou.mybatisplus.annotations.TableField;

/**
 * @Author O
 * @Description mybatis_plus test entity
 * @Date 2018/4/25 14:11
 * @Version 1.0
 */
public class Bird {
	private int id;
	private String name;
	private int age;
	@TableField("bird_father_name")
	private String birdFatherName;

	public Bird() {

	}

	@Override
	public String toString() {
		return "Bird{" +
				"id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				", birdFatherName='" + birdFatherName + '\'' +
				'}';
	}

	public String getBirdFatherName() {
		return birdFatherName;
	}

	public void setBirdFatherName(String birdFatherName) {
		this.birdFatherName = birdFatherName;
	}

	public Bird(int id, String name, int age, String birdFatherName) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.birdFatherName = birdFatherName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
