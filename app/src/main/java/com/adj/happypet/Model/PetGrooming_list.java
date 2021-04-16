package com.adj.happypet.Model;

public class PetGrooming_list {
    String ownerId, groomingshopname, contact, address, description, status;

    public PetGrooming_list() {
    }

    public PetGrooming_list(String ownerId, String groomingshopname, String contact, String address, String description, String status) {
        this.ownerId = ownerId;
        this.groomingshopname = groomingshopname;
        this.contact = contact;
        this.address = address;
        this.description = description;
        this.status = status;
    }

    public PetGrooming_list(String ownerId, String groomingshopname, String status) {
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getGroomingshopname() {
        return groomingshopname;
    }

    public void setGroomingshopname(String groomingshopname) {
        this.groomingshopname = groomingshopname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
