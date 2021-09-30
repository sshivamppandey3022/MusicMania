package com.shivam.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shivam.musicplayer.Models.MusicFiles;
import com.shivam.musicplayer.R;

import java.util.ArrayList;
import java.util.Random;

import static com.shivam.musicplayer.Activities.ApplicationClass.CHANNEL_ID_2;
import static com.shivam.musicplayer.Activities.MainActivity.musicFiles;
import static com.shivam.musicplayer.Adapters.AlbumDetailAdapter.mAlbums;

public class SongsPlayingActivity extends AppCompatActivity implements  ServiceConnection, ActionPlaying {
    Button play_back, backward_btn, next_btn;
    Button  play_shuffle, play_repeat;
    TextView play_name, play_artist, timeStart, timeEnd;
    ImageView playPauseBtn, cover_art;
    SeekBar seekBar;
    MediaSessionCompat mediaSessionCompat;
    int position = -1;
    static ArrayList<MusicFiles> listSongs = new ArrayList<>();
    static Uri uri;
    public static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Thread playThread, prevThread, nextThread;
    MusicService musicService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_playing);
        mediaSessionCompat = new MediaSessionCompat(getBaseContext(), "MusicMania...");
        initViews();
        getIntentMethod();
        play_name.setText(listSongs.get(position).getTitle());
        play_artist.setText(listSongs.get(position).getArtist());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        SongsPlayingActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    timeStart.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });


    }
    @Override
    protected void onResume(){
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
        playThreadBtn();
        prevThreadBtn();
        nextThreadBtn();
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    private void nextThreadBtn() {
        nextThread = new Thread(){
            @Override
            public void run() {
                super.run();
                next_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextBtnClicked();
                    }
                });
            }
        };
        nextThread.start();

    }
    public void nextBtnClicked() {
        if (mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) % listSongs.size());
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

            metaData(uri);
            play_name.setText(listSongs.get(position).getTitle());
            play_artist.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() /1000);
            SongsPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            showNotification(R.drawable.ic_pause_circle);
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
        }
        else
            {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position + 1) % listSongs.size());
                uri = Uri.parse(listSongs.get(position).getPath());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                metaData(uri);
                play_name.setText(listSongs.get(position).getTitle());
                play_artist.setText(listSongs.get(position).getArtist());
                seekBar.setMax(mediaPlayer.getDuration() /1000);
                SongsPlayingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null){
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                        }
                        handler.postDelayed(this, 1000);
                    }
                });
                showNotification(R.drawable.ic_play_circle);
                playPauseBtn.setImageResource(R.drawable.ic_play_arrow_white);
            }
    }
    private void prevThreadBtn() {
        prevThread = new Thread(){
            @Override
            public void run() {
                super.run();
                backward_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prevBtnClicked();
                    }
                });
            }
        };
        prevThread.start();

    }
    public void prevBtnClicked() {
        if (mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? ( listSongs.size() - 1) : (position - 1));
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            play_name.setText(listSongs.get(position).getTitle());
            play_artist.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() /1000);
            SongsPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            showNotification(R.drawable.ic_pause_circle);
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
        }
        else
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? ( listSongs.size() - 1) : (position - 1));
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            play_name.setText(listSongs.get(position).getTitle());
            play_artist.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() /1000);
            SongsPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            showNotification(R.drawable.ic_play_circle);
            playPauseBtn.setImageResource(R.drawable.ic_play_arrow_white);
        }
    }
    private void playThreadBtn() {
        playThread = new Thread(){
            @Override
            public void run() {
                super.run();
                playPauseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playPauseBtnClicked();
                    }
                });
            }
        };
        playThread.start();

    }
    public void playPauseBtnClicked() {
        if (mediaPlayer.isPlaying()){
            showNotification(R.drawable.ic_play_circle);
            playPauseBtn.setImageResource(R.drawable.ic_play_arrow_white);
            mediaPlayer.pause();
            seekBar.setMax(mediaPlayer.getDuration() /1000);
            SongsPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
        else {
            showNotification(R.drawable.ic_pause_circle);
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration() /1000);
            SongsPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
    }
    private String formattedTime(int mCurrentPosition){
        String totalOut = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalOut = minutes + ":" + seconds;
        totalNew = minutes + ":" + "0" + seconds;
        if (seconds.length() == 1){
            return totalNew;
        }
        else {
            return totalOut;
        }

    }

    private void getIntentMethod()
    {
        position = getIntent().getIntExtra("position", -1);
        String sender = getIntent().getStringExtra("sender");
        if (sender != null && sender.equals("albumDetail"))
        {
            listSongs = mAlbums;
        }
        else {
            listSongs = musicFiles;
        }
        if (listSongs != null){
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            uri = Uri.parse(listSongs.get(position).getPath());
        }
        showNotification(R.drawable.ic_pause_circle);
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();

            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        }
        else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

            mediaPlayer.start();
        }

        seekBar.setMax(mediaPlayer.getDuration() / 1000);
        metaData(uri);

    }
    private void initViews()
    {
        play_name=findViewById(R.id.play_name);
        play_artist=findViewById(R.id.play_artist);
        play_back=findViewById(R.id.play_back);
        play_shuffle= findViewById(R.id.play_shuffle);
        play_repeat= findViewById(R.id.play_repeat);
        backward_btn=findViewById(R.id.backward_btn);
        next_btn=findViewById(R.id.nextBtn);
        seekBar=findViewById(R.id.seekbar);
        playPauseBtn=findViewById(R.id.playPauseBtn);
        cover_art=findViewById(R.id.cover_art);
        timeStart=findViewById(R.id.timeStart);
        timeEnd=findViewById(R.id.timeEnd);

        play_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SongsPlayingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        play_name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        play_name.setSelected(true);
    }
    private void metaData(Uri uri)
    {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int durationTotal = Integer.parseInt(listSongs.get(position).getDuration()) /1000;
        timeEnd.setText(formattedTime(durationTotal));
        byte[] art = retriever.getEmbeddedPicture();
        if (art != null){
            Glide.with(this).asBitmap()
                    .load(art)
                    .into(cover_art);
        }
        else {
            Glide.with(this).asBitmap()
                    .load(R.drawable.music_design)
                    .into(cover_art);

        }
    }

    void showNotification(int playPauseBtn)
    {
        Intent intent = new Intent(this, SongsPlayingActivity.class);
        PendingIntent contentIntent = PendingIntent.getBroadcast(this, 0, intent,0);

        Intent prevIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent prevPending = PendingIntent.getBroadcast(this, 0, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent pausetIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent pausePending = PendingIntent.getBroadcast(this, 0, pausetIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent nextPending = PendingIntent.getBroadcast(this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        byte[] picture = null;
        Bitmap thumb = null;
        picture = getAlbumArt(listSongs.get(position).getPath());
        if (picture != null){
            thumb = BitmapFactory.decodeByteArray(picture,0, picture.length);
        }
        else {
            thumb = BitmapFactory.decodeResource(getResources(), R.drawable.music_design);
        }
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_2)
                .setSmallIcon(playPauseBtn)
                .setLargeIcon(thumb)
                .setContentTitle(listSongs.get(position).getTitle())
                .setContentText(listSongs.get(position).getArtist())
                .addAction(R.drawable.ic_skip_previous_black_24dp, "Previous", prevPending)
                .addAction(playPauseBtn, "Pause", pausePending)
                .addAction(R.drawable.ic_skip_next_black_24dp, "Next", nextPending)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOnlyAlertOnce(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }



    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
//        MusicService.MyBinder myBinder = (MusicService.MyBinder) service;
//        musicService = myBinder.getService();
        Toast.makeText(this,"Hello Connected..." + musicService,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        musicService = null;

    }
}
