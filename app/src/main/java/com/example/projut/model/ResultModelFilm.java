package com.example.projut.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultModelFilm {
    @SerializedName("result")
    private ArrayList<ModelFilm> result;

    public ArrayList<ModelFilm> getArrayModel() {
        return result;
    }
}
