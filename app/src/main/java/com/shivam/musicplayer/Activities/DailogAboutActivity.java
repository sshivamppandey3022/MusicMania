package com.shivam.musicplayer.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shivam.musicplayer.R;

public class DailogAboutActivity extends AppCompatActivity {

    TextView dailog_Txt1,  dailog_Txt2, dailog_Txt3, dailog_Txt4, dailog_Txt5, dailog_Txt6, dailog_Ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailog_about);
        dailog_Txt1=findViewById(R.id.dailog_Txt1);
        dailog_Txt2=findViewById(R.id.dailog_Txt2);
        dailog_Txt3=findViewById(R.id.dailog_Txt3);
        dailog_Txt4=findViewById(R.id.dailog_Txt4);
        dailog_Txt5=findViewById(R.id.dailog_Txt5);
        dailog_Txt6=findViewById(R.id.dailog_Txt6);
        dailog_Ok=findViewById(R.id.dailog_Ok);

    }



}
