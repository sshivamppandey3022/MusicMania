package com.shivam.musicplayer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shivam.musicplayer.Activities.SongsPlayingActivity;
import com.shivam.musicplayer.Models.MusicFiles;
import com.shivam.musicplayer.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumDetailAdapter extends RecyclerView.Adapter<AlbumDetailAdapter.ViewHolder> {
    private  Context ctx;
    public  static  ArrayList<MusicFiles> mAlbums;

    public AlbumDetailAdapter(Context ctx, ArrayList<MusicFiles> mAlbums) {
        this.ctx = ctx;
        this.mAlbums = mAlbums;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  layoutInflater.inflate(R.layout.songs_fragment_design,parent,false);
        return new AlbumDetailAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.song_name.setText(mAlbums.get(position).getTitle());
        holder.song_artist.setText(mAlbums.get(position).getArtist());
        final byte[] image = getAlbumArt(mAlbums.get(position).getPath());
        if (image != null)
        {
            Glide.with(ctx).asBitmap()
                    .load(image)
                    .into(holder.song_img);
        }
        else {
            Glide.with(ctx)
                    .load(R.drawable.music_design)
                    .into(holder.song_img);
        }
        holder.song_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, SongsPlayingActivity.class);
                intent.putExtra("sender", "albumDetail");
                intent.putExtra("position", position);
                ctx.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView song_name, song_artist;
        ImageView song_img;
        LinearLayout song_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            song_name=itemView.findViewById(R.id.song_name);
            song_artist=itemView.findViewById(R.id.song_artist);
            song_img=itemView.findViewById(R.id.song_img);
            song_layout=itemView.findViewById(R.id.song_layout);
        }
    }
    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }

}
