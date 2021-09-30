package com.shivam.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shivam.musicplayer.R;

public class SupportDevelopment extends AppCompatActivity {
    Button support_Dev_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_development);
        support_Dev_back = findViewById(R.id.support_Dev_back);

        support_Dev_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SupportDevelopment.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
