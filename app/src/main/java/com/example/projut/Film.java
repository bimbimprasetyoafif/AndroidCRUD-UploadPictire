package com.example.projut;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.projut.api.ApiRequest;
import com.example.projut.api.Client;
import com.example.projut.model.ModelFilm;
import com.example.projut.model.ResultModelFilm;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Film extends Fragment{

    private static TextInputEditText judul;
    private static TextInputEditText tahun;
    private Button tombol;
    private static RecyclerView rv;
    private static FilmAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film, container, false);

        rv = view.findViewById(R.id.rv_film);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshFilm();

        judul = view.findViewById(R.id.mJudul);
        tahun = view.findViewById(R.id.mTahun);
        tombol = view.findViewById(R.id.tSimpan);
        tombol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFilm();
            }
        });
        return view;
    }

    private void createFilm(){

        if (TextUtils.isEmpty(judul.getText().toString().trim())) {
            judul.setError("Silahkan masukkan judul");
            judul.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(tahun.getText().toString().trim())) {
            tahun.setError("Silahkan masukkan tahun");
            tahun.requestFocus();
            return;
        }

        String Judul = judul.getText().toString();
        int Tahun = Integer.parseInt(tahun.getText().toString());

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("judul",Judul);
        payload.put("tahun",Tahun);

        Retrofit retrofit = Client.getClient();
        retrofit.create(ApiRequest.class).postFilm(payload)
             .enqueue(new Callback<ModelFilm>() {
                 @Override
                 public void onResponse(Call<ModelFilm> call, Response<ModelFilm> response) {
                     refreshFilm();
                     judul.setText(null);
                     tahun.setText(null);
                     Toast.makeText(getActivity(), "Berhasil tersimpan", Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onFailure(Call<ModelFilm> call, Throwable t) {
                     Toast.makeText(getActivity(), "Gagal menyimpan", Toast.LENGTH_SHORT).show();
                 }
             });
    }

    public static void refreshFilm(){
        Retrofit retrofit = Client.getClient();
        retrofit.create(ApiRequest.class).getFilm()
                .enqueue(new Callback<ResultModelFilm>() {
                    @Override
                    public void onResponse(Call<ResultModelFilm> call, Response<ResultModelFilm> response) {
                        ArrayList<ModelFilm> filmlist;
                        filmlist = response.body().getArrayModel();

                        adapter = new FilmAdapter(filmlist);
                        rv.setAdapter(adapter);
                    }
                    @Override
                    public void onFailure(Call<ResultModelFilm> call, Throwable t) {

                    }
                });
    }

    public static void deleteFilm(String i){
        Retrofit retrofit = Client.getClient();
        retrofit.create(ApiRequest.class).deleteFilm(i)
                .enqueue(new Callback<ModelFilm>() {
                    @Override
                    public void onResponse(Call<ModelFilm> call, Response<ModelFilm> response) {
                        refreshFilm();
                    }

                    @Override
                    public void onFailure(Call<ModelFilm> call, Throwable t) {

                    }

                });
    }

    public static void updateFilm(String i){

        if (TextUtils.isEmpty(judul.getText().toString().trim())) {
            judul.setError("Silahkan masukkan judul");
            judul.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(tahun.getText().toString().trim())) {
            tahun.setError("Silahkan masukkan tahun");
            tahun.requestFocus();
            return;
        }

        String Judul = judul.getText().toString();
        int Tahun = Integer.parseInt(tahun.getText().toString());

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("judul",Judul);
        payload.put("tahun",Tahun);

        Retrofit retrofit = Client.getClient();
        retrofit.create(ApiRequest.class).updateFilm(i,payload)
                .enqueue(new Callback<ModelFilm>() {
                    @Override
                    public void onResponse(Call<ModelFilm> call, Response<ModelFilm> response) {
                        refreshFilm();
                        judul.setText(null);
                        tahun.setText(null);
                    }

                    @Override
                    public void onFailure(Call<ModelFilm> call, Throwable t) {

                    }

                });
    }
}
