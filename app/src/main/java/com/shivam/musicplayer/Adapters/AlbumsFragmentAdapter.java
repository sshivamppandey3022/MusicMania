package com.shivam.musicplayer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shivam.musicplayer.Activities.AlbumDetails;
import com.shivam.musicplayer.Models.MusicFiles;
import com.shivam.musicplayer.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumsFragmentAdapter extends RecyclerView.Adapter<AlbumsFragmentAdapter.ViewHolder> {

   ArrayList<MusicFiles> mAlbums;
   Context ctx;

    public AlbumsFragmentAdapter(ArrayList<MusicFiles> mAlbums, Context ctx) {
        this.mAlbums = mAlbums;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AlbumsFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  layoutInflater.inflate(R.layout.album_fragment_design,parent,false);
        return new AlbumsFragmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumsFragmentAdapter.ViewHolder holder, final int position) {
        holder.album_name.setText(mAlbums.get(position).getAlbum());
        final byte[] image = getAlbumArt(mAlbums.get(position).getPath());
        if (image != null)
        {
            Glide.with(ctx).asBitmap()
                    .load(image)
                    .into(holder.album_img);
        }
        else {
            Glide.with(ctx)
                    .load(R.drawable.music_design)
                    .into(holder.album_img);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, AlbumDetails.class);
                intent.putExtra("albumName", mAlbums.get(position).getAlbum());
                ctx.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView album_name;
        ImageView album_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            album_name=itemView.findViewById(R.id.album_name);
            album_img=itemView.findViewById(R.id.album_img);
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
