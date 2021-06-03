package com.adj.happypet.Model;

public class GroomingOwnerInfoModel {
    String id, petGroomerName, groomingShopName, petGroomerDesc, location_petshop, status;

    public GroomingOwnerInfoModel() {
    }

    public GroomingOwnerInfoModel(String id, String petGroomerName, String groomingShopName, String petGroomerDesc, String location_petshop) {
        this.id = id;
        this.petGroomerName = petGroomerName;
        this.groomingShopName = groomingShopName;
        this.petGroomerDesc = petGroomerDesc;
        this.location_petshop = location_petshop;
    }

    public GroomingOwnerInfoModel(String id, String petGroomerName, String groomingShopName, String petGroomerDesc, String location_petshop, String status) {
        this.id = id;
        this.petGroomerName = petGroomerName;
        this.groomingShopName = groomingShopName;
        this.petGroomerDesc = petGroomerDesc;
        this.location_petshop = location_petshop;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPetGroomerName() {
        return petGroomerName;
    }

    public void setPetGroomerName(String petGroomerName) {
        this.petGroomerName = petGroomerName;
    }

    public String getGroomingShopName() {
        return groomingShopName;
    }

    public void setGroomingShopName(String groomingShopName) {
        this.groomingShopName = groomingShopName;
    }

    public String getPetGroomerDesc() {
        return petGroomerDesc;
    }

    public void setPetGroomerDesc(String petGroomerDesc) {
        this.petGroomerDesc = petGroomerDesc;
    }

    public String getLocation_petshop() {
        return location_petshop;
    }

    public void setLocation_petshop(String petGroomerDesc) {
        this.location_petshop = location_petshop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
