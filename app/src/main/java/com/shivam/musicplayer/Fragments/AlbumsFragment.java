package com.shivam.musicplayer.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shivam.musicplayer.Adapters.AlbumsFragmentAdapter;
import com.shivam.musicplayer.Adapters.SongsFragmentAdapter;
import com.shivam.musicplayer.R;

import static com.shivam.musicplayer.Activities.MainActivity.musicFiles;


public class AlbumsFragment extends Fragment {

    RecyclerView album_recyclerView;
    AlbumsFragmentAdapter albumsFragmentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        album_recyclerView = view.findViewById(R.id.album_recyclerView);
        album_recyclerView.setHasFixedSize(true);
        if (!(musicFiles.size() < 1))
        {
            albumsFragmentAdapter = new AlbumsFragmentAdapter(musicFiles, getContext());
            album_recyclerView.setAdapter(albumsFragmentAdapter);
            album_recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        }

        return view;

    }
}
