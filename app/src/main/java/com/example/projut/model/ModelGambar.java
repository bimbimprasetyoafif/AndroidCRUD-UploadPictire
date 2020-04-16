package com.example.projut.model;

import com.google.gson.annotations.SerializedName;

public class ModelGambar {
    @SerializedName("id")
    private int id;

    @SerializedName("gambar")
    private String gambar;

    public ModelGambar(int id, String gambar) {
        this.id = id;
        this.gambar = gambar;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public String getGambar() {
        return gambar;
    }
}
