package com.shivam.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.shivam.musicplayer.R;

public class SearchActivity extends AppCompatActivity {
    CardView searchCard;
    Button searchBackBtn;
    LinearLayout searchLayout;
    Button searchMoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchCard = findViewById(R.id.searchCard);
        searchLayout = findViewById(R.id.searchLayout);
        searchBackBtn = findViewById(R.id.searchBackBtn);
        searchMoreBtn = findViewById(R.id.searchMoreBtn);


        searchBackBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
