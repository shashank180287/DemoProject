package com.mobile.health.demo.entity;

import com.mobile.health.demo.manager.DBManger;

public class PersonDetails {

	public int id;
	public String firstName;
	public String lastName;
	public String gender;
	public int age;
	public String address;
	public String panchayat;
	
	public PersonDetails(String firstName, String lastName, String gender,
			int age, String address, String panchayat) {
		super();
		this.id = DBManger.generateId();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
		this.address = address;
		this.panchayat = panchayat;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPanchayat() {
		return panchayat;
	}
	public void setPanchayat(String panchayat) {
		this.panchayat = panchayat;
	}

    /**
     * @return
     */
    public Object[] toObjectArray() {
        return new Object[] { firstName, lastName, gender, age, address, panchayat };
    }

}
