package com.example.ernestschneiderolcina.marvelstarwars.networking.apismodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DogPictureApi {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private List<String> dogPictures = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getDogPictures() {
        return dogPictures;
    }

    public void setMessage(List<String> message) {
        this.dogPictures = message;
    }


}
