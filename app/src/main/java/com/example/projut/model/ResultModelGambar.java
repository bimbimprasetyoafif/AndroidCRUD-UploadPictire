package com.example.projut.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultModelGambar {
    @SerializedName("result")
    private ArrayList<ModelGambar> result;

    public ArrayList<ModelGambar> getMg() {
        return result;
    }
}
