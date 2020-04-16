package com.example.projut;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projut.api.ApiRequest;
import com.example.projut.api.Client;
import com.example.projut.model.ModelGambar;
import com.example.projut.model.ResultModelGambar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;


public class Gambar extends Fragment {
    private Button tombol, tombolUpload;
    private TextView nama_gambar;
    private static RecyclerView rv;
    private static GambarAdapter adapter;
    private static RequestQueue mRequest;
    public static ArrayList<ModelGambar> mGambar;
    private Uri uri;

    String imagePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gambar, container, false);

        mGambar = new ArrayList<>();
        rv = view.findViewById(R.id.rv_gambar);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRequest = Volley.newRequestQueue(getActivity());

        refreshGambar();

        nama_gambar = view.findViewById(R.id.g_nama);
        tombol = view.findViewById(R.id.tSimpanGambar);
        tombolUpload = view.findViewById(R.id.tUploadGambar);
        tombol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/");
                startActivityForResult(intent,0);
            }
        });

        tombolUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postGambar();
            }
        });

        return view;
    }

    public void refreshGambar(){
        String url = "http://192.168.43.81:8000/api/gambar";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jArray = response.getJSONArray("result");
                            mGambar.clear();
                            setAdapterRv(mGambar);

                            for (int i = 0; i < jArray.length(); i++){
                                JSONObject hit = jArray.getJSONObject(i);

                                String gambar = hit.getString("gambar");
                                int id = hit.getInt("id");

                                mGambar.add(new ModelGambar(id,gambar));
                            }

                            setAdapterRv(mGambar);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        mRequest.add(request);
    }

    public void postGambar(){

        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("gambar", file.getName(), requestBody);
        Retrofit retrofit = Client.getClient();
        retrofit.create(ApiRequest.class).postGambar(body)
                .enqueue(new Callback<ResultModelGambar>() {
                    @Override
                    public void onResponse(Call<ResultModelGambar> call, retrofit2.Response<ResultModelGambar> response) {
                        refreshGambar();
                        nama_gambar.setText(null);
                        tombolUpload.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Berhasil disimpan", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResultModelGambar> call, Throwable t) {

                    }
                });
    }

    public static void deleteGambar(String i, int p){
        Retrofit retrofit = Client.getClient();
        retrofit.create(ApiRequest.class).deleteGambar(i)
                .enqueue(new Callback<ModelGambar>() {
                    @Override
                    public void onResponse(Call<ModelGambar> call, retrofit2.Response<ModelGambar> response) {
                        adapter.notifyItemRemoved(p);
                    }

                    @Override
                    public void onFailure(Call<ModelGambar> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if (data == null){
                Toast.makeText(getActivity(), "Unable to choose image!", Toast.LENGTH_SHORT).show();
                return;
            }

            Uri imageUri = data.getData();
            imagePath = getRealPathFromUri(imageUri);
            File file = new File(imagePath);
            nama_gambar.setText(file.getName());
            tombolUpload.setVisibility(View.VISIBLE);
        }
    }

    private String getRealPathFromUri(Uri uri){
        this.uri = uri;
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_idx);
        cursor.close();
        return result;
    }

    public void setAdapterRv(ArrayList<ModelGambar> a){
        adapter = new GambarAdapter(getActivity() ,a);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
