package com.adj.happypet.Model;

import com.adj.happypet.HomeFragment;

import java.util.ArrayList;

public class PetGroomingListUser {
    String groomingshopname, id;

    public PetGroomingListUser(String petGroomingUserName, String id){
        this.groomingshopname = petGroomingUserName;
        this.id = id;
    }



    public String getGroomingshopname() {
        return groomingshopname;
    }

    public void setGroomingshopname(String petGroomingUserName) {
        this.groomingshopname = petGroomingUserName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
