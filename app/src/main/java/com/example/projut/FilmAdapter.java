package com.example.projut;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projut.model.ModelFilm;

import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmAdapterViewHolder> {

    private ArrayList<ModelFilm> mFilmList;

    public FilmAdapter(ArrayList<ModelFilm> mFilm) {
        this.mFilmList = mFilm;
    }

    @NonNull
    @Override
    public FilmAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_film, parent,false);
        FilmAdapterViewHolder favh = new FilmAdapterViewHolder(v);
        return favh;
    }

    @Override
    public void onBindViewHolder(@NonNull FilmAdapterViewHolder holder, int position) {
        ModelFilm modelf = mFilmList.get(position);
        holder.tJudul.setText(modelf.getJudul());
        holder.tTahun.setText(modelf.getTahun());
        holder.tId.setText(modelf.getId());

    }

    @Override
    public int getItemCount() {
        return mFilmList.size();
    }

    public static class FilmAdapterViewHolder extends RecyclerView.ViewHolder{
        public TextView tId;
        public TextView tJudul;
        public TextView tTahun;
        public Button tUpdate;
        public Button tDelete;


        public FilmAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tJudul = itemView.findViewById(R.id.tv_judul);
            tTahun = itemView.findViewById(R.id.tv_tahun);
            tId = itemView.findViewById(R.id.tv_id);
            tDelete = itemView.findViewById(R.id.tv_delete);
            tUpdate = itemView.findViewById(R.id.tv_update);

            tDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Film.deleteFilm(tId.getText().toString());
                }
            });

            tUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Film.updateFilm(tId.getText().toString());
                }
            });

         }
    }
}

