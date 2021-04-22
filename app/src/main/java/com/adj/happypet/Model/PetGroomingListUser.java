package com.adj.happypet.Model;

import com.adj.happypet.HomeFragment;

import java.util.ArrayList;

public class PetGroomingListUser {
    String groomingshopname, id, contact_petgrooming_user, address_petgrooming_user, description_petgrooming_user, status_petgrooming_user;

    public PetGroomingListUser(String petGroomingUserName, String id, String contact_petgrooming_user, String address_petgrooming_user, String description_petgrooming_user, String status_petgrooming_user){
        this.groomingshopname = petGroomingUserName;
        this.id = id;
        this.contact_petgrooming_user = contact_petgrooming_user;
        this.address_petgrooming_user = address_petgrooming_user;
        this.description_petgrooming_user = description_petgrooming_user;
        this.status_petgrooming_user = status_petgrooming_user;
    }

    public PetGroomingListUser(String ownerId, String groomingshopname) {
    }

    public String getAddress_petgrooming_user() {
        return address_petgrooming_user;
    }

    public void setAddress_petgrooming_user(String address_petgrooming_user) {
        this.address_petgrooming_user = address_petgrooming_user;
    }

    public String getContact_petgrooming_user() {
        return contact_petgrooming_user;
    }

    public void setContact_petgrooming_user(String contact_petgrooming_user) {
        this.contact_petgrooming_user = contact_petgrooming_user;
    }

    public String getDescription_petgrooming_user() {
        return description_petgrooming_user;
    }

    public void setDescription_petgrooming_user(String description_petgrooming_user) {
        this.description_petgrooming_user = description_petgrooming_user;
    }

    public String getStatus_petgrooming_user() {
        return status_petgrooming_user;
    }

    public void setStatus_petgrooming_user(String status_petgrooming_user) {
        this.status_petgrooming_user = status_petgrooming_user;
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
