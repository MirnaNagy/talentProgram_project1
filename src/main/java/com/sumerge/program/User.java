package com.sumerge.program;

public class User {

    public String name;
    public int id;
    public String email;

    public User() {

    }


    public User(String name, int id, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
    }


    public void setName(String n) {
        name = n;
    }

    public void setId(int idd) {
        id = idd;
    }

    public void setEmail(String e){
        email = e;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }

}
