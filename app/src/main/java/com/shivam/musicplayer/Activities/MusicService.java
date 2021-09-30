package com.shivam.musicplayer.Activities;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.shivam.musicplayer.Models.MusicFiles;

import java.util.ArrayList;

import androidx.annotation.Nullable;

import static com.shivam.musicplayer.Activities.SongsPlayingActivity.listSongs;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {

    IBinder mBinder = new Binder();
    MediaPlayer mediaPlayer;
    ArrayList<MusicFiles> musicFiles = new ArrayList<>();
    Uri uri;
    int position = -1;
    ActionPlaying actionPlaying;
    @Override
    public void onCreate() {
        super.onCreate();
        musicFiles = listSongs;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Bind", "Method");
        return mBinder;
    }
    public class MyBinder extends Binder {
        MusicService getService(){
            return MusicService.this;
        }
    }
    void start(){
        mediaPlayer.start();
    }
    boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }
    void stop(){
        mediaPlayer.stop();
    }
    void release(){
        mediaPlayer.release();
    }
    int getDuration(){
        return mediaPlayer.getDuration();
    }
    void seekTo(int position)
    {
        mediaPlayer.seekTo(position);
    }
    int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    void pause(){
        mediaPlayer.pause();
    }

    void  createMediaPlayer(int position){
        uri = Uri.parse(musicFiles.get(position).getPath());
        mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}
