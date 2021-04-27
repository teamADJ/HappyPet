package com.adj.happypet.Model;

public class Order_list {
    private String id;
    private String petShopName;
    private String ownerName;
    private String status;

    public Order_list() {
    }

    public Order_list(String id, String petShopName, String ownerName, String status) {
        this.id = id;
        this.petShopName = petShopName;
        this.ownerName = ownerName;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPetShopName() {
        return petShopName;
    }

    public void setPetShopName(String petShopName) {
        this.petShopName = petShopName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
