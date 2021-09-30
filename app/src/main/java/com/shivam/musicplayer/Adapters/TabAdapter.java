package com.shivam.musicplayer.Adapters;

import android.content.Context;

import com.shivam.musicplayer.Fragments.AlbumsFragment;
import com.shivam.musicplayer.Fragments.ArtistsFragment;
import com.shivam.musicplayer.Fragments.SongsFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {


    int totalTab;
    Context ctx;
    public TabAdapter(@NonNull FragmentManager fm, Context ctx, int totalTab) {
        super(fm);
        this.ctx=ctx;
        this.totalTab=totalTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                SongsFragments songsFragments = new SongsFragments();
                return songsFragments;
            case 1:
                AlbumsFragment albumsFragment = new AlbumsFragment();
                return albumsFragment;
            /*case 2:
                ArtistsFragment artistsFragment = new ArtistsFragment();
                return artistsFragment;*/
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return totalTab;
    }
}
