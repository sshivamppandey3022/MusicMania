package com.shivam.musicplayer.Fragments;



import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.shivam.musicplayer.Models.MusicFiles;
import com.shivam.musicplayer.R;
import com.shivam.musicplayer.Adapters.SongsFragmentAdapter;


import java.util.ArrayList;

import static com.shivam.musicplayer.Activities.MainActivity.musicFiles;


public class SongsFragments extends Fragment {
    LinearLayout song_layout;
    RecyclerView song_recyclerView;
    SongsFragmentAdapter songsFragmentAdapter;
    static ArrayList<MusicFiles> musicList = new ArrayList<>();
 @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        song_layout=view.findViewById(R.id.song_layout);
        song_recyclerView = view.findViewById(R.id.song_recyclerView);
        song_recyclerView.setHasFixedSize(true);
        if (!(musicFiles.size() < 1))
        {
            songsFragmentAdapter = new SongsFragmentAdapter(musicFiles, getContext());
            song_recyclerView.setAdapter(songsFragmentAdapter);
            song_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));

        }

        return view;
 }
}


