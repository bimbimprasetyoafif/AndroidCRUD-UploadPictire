package com.example.projut.model;

import com.google.gson.annotations.SerializedName;

public class ModelFilm {
    @SerializedName("id")
    private int id;
    @SerializedName("judul")
    private String judul;
    @SerializedName("tahun")
    private int tahun;

    public ModelFilm(String judul, int tahun) {
        this.judul = judul;
        this.tahun = tahun;
    }
    public String getId() {
        return String.valueOf(id);
    }
    public String getJudul() {
        return judul;
    }
    public String getTahun() {
        return String.valueOf(tahun);
    }
}
