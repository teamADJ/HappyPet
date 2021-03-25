package com.adj.happypet.Model;

public class Order_list {
    private String petShopName;
    private String status;

    public Order_list(String petShopName, String status){
        this.petShopName = petShopName;
        this.status = status;
    }

    public String getPetShopName() {
        return petShopName;
    }

    public void setPetShopName(String petShopName) {
        this.petShopName = petShopName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }






}
