package com.adj.happypet.Model;

public class GroomingOwnerInfoModel {
    String id, petGroomerName, groomingShopName, petGroomerDesc;

    public GroomingOwnerInfoModel() {
    }

    public GroomingOwnerInfoModel(String id, String petGroomerName, String groomingShopName, String petGroomerDesc) {
        this.id = id;
        this.petGroomerName = petGroomerName;
        this.groomingShopName = groomingShopName;
        this.petGroomerDesc = petGroomerDesc;
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
}
