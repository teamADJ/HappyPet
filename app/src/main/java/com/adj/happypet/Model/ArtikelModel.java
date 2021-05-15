package com.adj.happypet.Model;

public class ArtikelModel {
    String artikel_id, description, title, source;
    public ArtikelModel(){

    }



    public ArtikelModel(String artikel_id, String description, String source, String title) {
        this.artikel_id = artikel_id;
        this.description = description;
        this.source = source;
        this.title = title;

    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getSource(){
        return  source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
