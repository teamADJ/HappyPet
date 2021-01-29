package com.adj.happypet;

public class User {
    String fullName,age,email,password;

    // empty constructor for firebase
    public User(String fullname, String email, String password) {
    }


    //constructor

    public User(String fullName, String age, String email, String password) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    // Getter and Setter


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
