package com.tiarintsoa.restaurant.pojo;

public class Client {

    protected int id;
    protected String lastname;
    protected String firstname;
    protected String phone;
    protected String password;
    protected String type;

    public Client() {}
    public Client(int id, String lastname, String firstname, String phone, String password, String type) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.phone = phone;
        this.password = password;
        this.type = type;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return "Client [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + ", phone=" + phone
                + ", password=" + password + ", type=" + type + "]";
    }

}