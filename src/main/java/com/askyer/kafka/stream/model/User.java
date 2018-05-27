package com.askyer.kafka.stream.model;

public class User {
	private String user_name;
	private String user_address;
	private String gender;
	private int age;

	public User() {}

	public User(String user_name, String user_address, String gender, int age) {
		this.user_name = user_name;
		this.user_address = user_address;
		this.gender = gender;
		this.age = age;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
