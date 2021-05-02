package com.adj.happypet.Model;

public class ClientOrderModel {

    String orderId, userId, name, contact, time, address, status, ownerId;

    public ClientOrderModel(String orderId, String userId, String name, String contact, String time, String address, String status, String ownerId) {
        this.orderId = orderId;
        this.userId = userId;
        this.name = name;
        this.contact = contact;
        this.time = time;
        this.address = address;
        this.status = status;
        this.ownerId = ownerId;
    }

    public ClientOrderModel() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
