package com.sadataljony.app.android.sqlitecrud.model;

/**
 * Created by Muhammad Sadat Al-Jony on 02-April-2020.
 */

public class Person {
    private int id;
    private String name;
    private int age;
    private String phone;
    private String email;
    private String address;
    private double salary;

    // default constructor
    public Person() {
    }

    // argument constructor to create person object
    public Person(int id, String name, int age, String phone, String email, String address, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.salary = salary;
    }

    // all getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public double getSalary() {
        return salary;
    }
}
