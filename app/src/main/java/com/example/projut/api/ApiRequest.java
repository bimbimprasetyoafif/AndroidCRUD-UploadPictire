package com.example.projut.api;


import com.example.projut.model.ModelFilm;
import com.example.projut.model.ModelGambar;
import com.example.projut.model.ResultModelFilm;
import com.example.projut.model.ResultModelGambar;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiRequest {

    @POST("film")
    Call<ModelFilm> postFilm(@Body Map<String,Object> payload);

    @GET("film")
    Call<ResultModelFilm> getFilm();

    @DELETE("film/{id}")
    Call<ModelFilm> deleteFilm(@Path("id") String id);

    @PUT("film/{id}")
    Call<ModelFilm> updateFilm(@Path("id") String id,
                    @Body Map<String,Object> payload
                    );

    @Multipart
    @POST("gambar/upload")
    Call<ResultModelGambar> postGambar(@Part MultipartBody.Part gambar);

    @DELETE("gambar/{id}")
    Call<ModelGambar> deleteGambar(@Path("id") String id);
}
