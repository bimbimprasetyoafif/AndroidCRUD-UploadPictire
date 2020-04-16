package com.example.projut;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.projut.model.ModelGambar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GambarAdapter extends RecyclerView.Adapter<GambarAdapter.GambarAdapterViewHolder> {

    private ArrayList<ModelGambar> mGambar;
    private Context context;

    public GambarAdapter(Context context, ArrayList<ModelGambar> mGambar) {
        this.context = context;
        this.mGambar = mGambar;
    }

    @NonNull
    @Override
    public GambarAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_gambar, parent, false);
        return new GambarAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GambarAdapterViewHolder holder, int position) {
        ModelGambar mg = mGambar.get(position);
        holder.tIdImage.setText(mg.getId());

        String imgaeUrl =  "http://192.168.43.81:8000" + mg.getGambar();

        Picasso.with(context).load(imgaeUrl).fit().centerInside().into(holder.tImage);
    }

    @Override
    public int getItemCount() {
        return mGambar.size();
    }

    public class GambarAdapterViewHolder extends RecyclerView.ViewHolder{
        public ImageView tImage;
        public TextView tIdImage;
        public Button tDelete;

        public GambarAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tImage = itemView.findViewById(R.id.g_gambar);
            tIdImage = itemView.findViewById(R.id.g_id);
            tDelete = itemView.findViewById(R.id.g_delete);
            tDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gambar.deleteGambar(tIdImage.getText().toString(), getAdapterPosition());
                }
            });
        }
    }
}
