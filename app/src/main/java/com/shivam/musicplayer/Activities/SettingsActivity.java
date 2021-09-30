package com.shivam.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.shivam.musicplayer.R;

public class SettingsActivity extends AppCompatActivity {
    LinearLayout setting_support_dev;
    Button setting_backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setting_support_dev = findViewById(R.id.setting_support_dev);
        setting_backBtn = findViewById(R.id.setting_backBtn);

        setting_support_dev.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SettingsActivity.this, SupportDevelopment.class);
                        startActivity(intent);
                    }
                }
        );
        setting_backBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
