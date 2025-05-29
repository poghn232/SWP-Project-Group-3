package com.example.demo.model;

import java.util.Date;

public class StaffInfo {
    private int staffId;
    private String fullName;
    private String gender;
    private Date birthdate;
    private String address;
    private String identityNumber;
    private Date hireDate;
    private double salary;

    public StaffInfo(int staffId, String fullName, String gender, Date birthdate, String address,
            String identityNumber, Date hireDate, double salary) {
        this.staffId = staffId;
        this.fullName = fullName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.address = address;
        this.identityNumber = identityNumber;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
