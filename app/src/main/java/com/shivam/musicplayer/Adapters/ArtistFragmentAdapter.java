package com.shivam.musicplayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shivam.musicplayer.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArtistFragmentAdapter extends RecyclerView.Adapter<ArtistFragmentAdapter.ViewHolder> {
    int[] artist_img;
    String[] artist_txt1;
    String[] artist_txt2;
    Context ctx;

    public ArtistFragmentAdapter(int[] artist_img, String[] artist_txt1, String[] artist_txt2, Context ctx) {
        this.artist_img = artist_img;
        this.artist_txt1 = artist_txt1;
        this.artist_txt2 = artist_txt2;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ArtistFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.artist_fragment_design,parent,false);
        return new ArtistFragmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistFragmentAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return artist_txt1.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView artist_txt1;
        TextView artist_txt2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            artist_txt1=itemView.findViewById(R.id.artist_txt1);
            artist_txt2=itemView.findViewById(R.id.artist_txt2);
        }
    }
}
