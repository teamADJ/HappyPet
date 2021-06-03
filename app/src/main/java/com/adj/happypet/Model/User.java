package com.adj.happypet.Model;

public class User {
    String fullName,email,password;



    // empty constructor for firebase
    public User() {
    }


    //constructor
    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }



//    public User(String fullName, String age, String email) {
//        this.fullName = fullName;
//        this.age = age;
//        this.email = email;
//    }


    // Getter and Setter


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
