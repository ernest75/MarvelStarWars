package com.example.ernestschneiderolcina.marvelstarwars.networking.apismodels;

public class DogApiCharacter {

    public String name;

    public String universe;

    public String pictureUrl;

    public DogApiCharacter(String name, String universe, String pictureUrl) {
        this.name = name;
        this.universe = universe;
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniverse() {
        return universe;
    }

    public void setUniverse(String universe) {
        this.universe = universe;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
