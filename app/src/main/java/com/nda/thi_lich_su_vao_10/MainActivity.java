package com.nda.thi_lich_su_vao_10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.nda.thi_lich_su_vao_10.fn.DeOnThi.DeOnThi_System;
import com.nda.thi_lich_su_vao_10.fn.DeThi.DeThi_System;
import com.nda.thi_lich_su_vao_10.fn.Note.Note_System;
import com.nda.thi_lich_su_vao_10.fn.UserDe.UserDe_System;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

public class MainActivity extends AppCompatActivity {

    ViewFlipper vf_main;
    Animation slideIn, slideOut;
    CardView cv_OnThi ,cv_DeThi, cv_YourQuiz, cv_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartAppSDK.setTestAdsEnabled(true);
        StartAppAd.disableSplash();

        mappting();
        initiate();
        slideShow();
    }

    private void slideShow() {
        slideIn= AnimationUtils.loadAnimation(this,R.anim.slide_in);
        slideOut= AnimationUtils.loadAnimation(this,R.anim.slide_out);

        int[] image = {R.drawable.ic_slide_1, R.drawable.ic_slide_2, R.drawable.ic_slide_3};

        for(int i = 0 ; i <image.length; i++)
        {
            ImageView imgView = new ImageView(MainActivity.this);
            imgView.setImageResource(image[i]);
            vf_main.addView(imgView);
        }
        vf_main.setInAnimation(slideIn);
        vf_main.setOutAnimation(slideOut);
        vf_main.setAutoStart(true);
        vf_main.setFlipInterval(6000);
        vf_main.startFlipping();
    }

    private void initiate() {
        cv_OnThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DeOnThi_System.class));
            }
        });

        cv_DeThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DeThi_System.class));
            }
        });

        cv_YourQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserDe_System.class));
            }
        });

        cv_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Note_System.class));
            }
        });
    }

    private void mappting() {
        vf_main = (ViewFlipper) findViewById(R.id.vf_main);

        cv_OnThi     = (CardView) findViewById(R.id.cv_OnThi);
        cv_DeThi     = (CardView) findViewById(R.id.cv_DeThi);
        cv_YourQuiz  = (CardView) findViewById(R.id.cv_YourQuiz);
        cv_note      = (CardView) findViewById(R.id.cv_note);

    }
}