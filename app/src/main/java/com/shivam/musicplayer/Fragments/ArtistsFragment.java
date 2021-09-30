package com.shivam.musicplayer.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shivam.musicplayer.Adapters.ArtistFragmentAdapter;
import com.shivam.musicplayer.R;


public class ArtistsFragment extends Fragment {
    RecyclerView artist_recyclerView;

    int[] artist_img;
    String[] artist_txt1;
    String[] artist_txt2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artists, container, false);


        artist_recyclerView = view.findViewById(R.id.artist_recyclerView);

        artist_txt1 = new String[]{"Shivam001", "Shivam002", "Shivam003", "Shivam004", "Shivam005", "Shivam006", "Shivam007", "Shivam008"};
        artist_txt2 = new String[]{"Shivam", "Shivam", "Shivam", "Shivam", "Shivam", "Shivam", "Shivam", "Shivam"};

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        artist_recyclerView.setHasFixedSize(true);
        artist_recyclerView.setLayoutManager(gridLayoutManager);
        ArtistFragmentAdapter artistFragmentAdapter = new ArtistFragmentAdapter(artist_img, artist_txt1, artist_txt2, getActivity());
        artist_recyclerView.setAdapter(artistFragmentAdapter);
        return view;


    }

}
