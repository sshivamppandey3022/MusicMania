package com.shivam.musicplayer.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.shivam.musicplayer.Adapters.TabAdapter;
import com.shivam.musicplayer.Models.MusicFiles;
import com.shivam.musicplayer.Models.SongModel;
import com.shivam.musicplayer.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    CardView card;
    Button menu;
    Button more;
    Button search;
    DrawerLayout drawer_layout;
    LinearLayout about, support_Dev;
    CircleImageView profile;
    public static ArrayList<MusicFiles> musicFiles;
    public static final int REQUEST_CODE = 1;
    //static boolean shuffleBoolean = false, repeatBoolean = false;


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        card=findViewById(R.id.card);
        drawer_layout=findViewById(R.id.drawer_layout);
        more=findViewById(R.id.more);
        menu=findViewById(R.id.menu);
        search=findViewById(R.id.search);
        about=findViewById(R.id.about);
        profile = findViewById(R.id.profile);
        support_Dev=findViewById(R.id.support_Dev);


        tabLayout.addTab(tabLayout.newTab().setText("Songs"));
        tabLayout.addTab(tabLayout.newTab().setText("Albums"));
        //tabLayout.addTab(tabLayout.newTab().setText("Artists"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabAdapter tabAdapter=new TabAdapter(getSupportFragmentManager(),getApplicationContext(),tabLayout.getTabCount());
        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        permissions();





        menu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drawer_layout.openDrawer(GravityCompat.START);




                    }
                }
        );

        more.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        PopupMenu popup = new PopupMenu(MainActivity.this, more);
                        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {

                                if (R.id.shuffle==item.getItemId()){

                                }
                                else if (R.id.sortBy== item.getItemId()){


                                }
                                else if (R.id.equalizer== item.getItemId()){
                                }
                                return true;
                            }
                        });

                        popup.show();
                    }
                }
        );


        search.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void permissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);
        }
        else{
            //Toast.makeText(this, "Permission Granted !",Toast.LENGTH_SHORT).show();
            musicFiles  = getAllAudioFromDevice(this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // do whatever you want permission related
                //Toast.makeText(this, "Permission Granted !",Toast.LENGTH_SHORT).show();
                musicFiles  = getAllAudioFromDevice(this);
            }
            else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE);
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static ArrayList<MusicFiles> getAllAudioFromDevice(final Context context) {

        final ArrayList<MusicFiles> tempAudioList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA, //path
                MediaStore.Audio.Media.ARTIST

        };
        Cursor c = context.getContentResolver()
                .query(uri, projection, null, null, null);


        if (c != null) {
            while (c.moveToNext()) {

                String album = c.getString(0);
                String title = c.getString(1);
                String duration = c.getString(2);
                String path = c.getString(3);
                String artist = c.getString(4);

               MusicFiles musicFiles = new MusicFiles(path, title, artist, album, duration);
               Log.e("Path : " + path, "Album : " + album);
               tempAudioList.add(musicFiles);
                }
            c.close();
            }
        return tempAudioList;
    }






    public void onLibButtonClick(View v){
        Toast.makeText(getApplicationContext(), "Under Process", Toast.LENGTH_SHORT).show();

    }
    public void onplaylistButtonClick(View v){
        Toast.makeText(getApplicationContext(), "Under Process", Toast.LENGTH_SHORT).show();

    }
    public void onFolderButtonClick(View v){
        Toast.makeText(getApplicationContext(), "Under Process", Toast.LENGTH_SHORT).show();

    }
    public void onNowPlayButtonClick(View v){
        Toast.makeText(getApplicationContext(), "Under Process", Toast.LENGTH_SHORT).show();

    }
    public void onAboutButtonClick(View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(R.layout.activity_dailog_about);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void onSettingButtonClick(View v){
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
    public void onSupportButtonClick(View v){
        Intent intent = new Intent(MainActivity.this, SupportDevelopment.class);
        startActivity(intent);
    }
}
