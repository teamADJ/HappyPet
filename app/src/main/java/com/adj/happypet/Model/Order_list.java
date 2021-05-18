package com.adj.happypet.Model;

public class Order_list {
    private String orderId, userId, ownerId, nama_owner, alamat, contact, jam_mulai, status;

    public Order_list() {
    }

    public Order_list(String orderId, String userId, String ownerId, String nama_owner, String alamat, String contact, String jam_mulai, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.ownerId = ownerId;
        this.nama_owner = nama_owner;
        this.alamat = alamat;
        this.contact = contact;
        this.jam_mulai = jam_mulai;
        this.status = status;
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

    public String getNama_owner() {
        return nama_owner;
    }

    public void setNama_owner(String nama_owner) {
        this.nama_owner = nama_owner;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getJam_mulai() {
        return jam_mulai;
    }

    public void setJam_mulai(String jam_mulai) {
        this.jam_mulai = jam_mulai;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
