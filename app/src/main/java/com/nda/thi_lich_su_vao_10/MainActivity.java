package com.nda.thi_lich_su_vao_10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.nda.thi_lich_su_vao_10.fn.DeOnThi.DeOnThi_System;
import com.nda.thi_lich_su_vao_10.fn.DeOnThi.DeThi;
import com.nda.thi_lich_su_vao_10.fn.DeThi.DeThi_System;
import com.nda.thi_lich_su_vao_10.fn.Note.Note_System;
import com.nda.thi_lich_su_vao_10.fn.UserDe.UserDe_System;
import com.nda.thi_lich_su_vao_10.fn.db;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static db database;

    ViewFlipper vf_main;
    Animation slideIn, slideOut;
    CardView cv_OnThi ,cv_DeThi, cv_YourQuiz, cv_note;

    List<DeThi> deThiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartAppSDK.setTestAdsEnabled(true);
        StartAppAd.disableSplash();

        mappting();
        initiate();
        slideShow();

        createDatabase();
    }



    private void createDatabase() {
        database = new db(this, db.dbName,null,1);

        /*
            Note
         */
        database.QueryData("CREATE TABLE IF NOT EXISTS " + db.note_table
                + "(" + db.column1_noteId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + db.column2_noteTitle + " VARCHAR(200), "
                + db.column3_noteContent + " VARCHAR(200) )");
        //database.QueryDate("DROP TABLE IF EXISTS " + db.note_table);
        /*
            (END) Note
         */


        /*
            User create exam
         */
        database.QueryData("CREATE TABLE IF NOT EXISTS " + db.userDe_table
                + "(" + db.column1_userDe_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + db.column2_userDe_numberDe + " VARCHAR(200), "
                + db.column3_userDe_titleDe + " VARCHAR(200), "
                + db.column4_userDe_nguoiTao + " VARCHAR(200) )");
        //database.QueryDate("DROP TABLE IF EXISTS " + db.userDe_table);

        database.QueryData("CREATE TABLE IF NOT EXISTS " + db.detailDe_table
                + "(" + db.column1_detailDe_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + db.column2_foreignKey_userDe_id + " INTEGER , "
                + db.column3_detailDe_questionNumber + " VARCHAR(200), "
                + db.column4_detailDe_question + " VARCHAR(200) ,"
                + db.column5_detailDe_ansA + " VARCHAR(200), "
                + db.column6_detailDe_ansB + " VARCHAR(200), "
                + db.column7_detailDe_ansC + " VARCHAR(200), "
                + db.column8_detailDe_ansD + " VARCHAR(200), "
                + db.column9_detailDe_correctAns + " VARCHAR(200) )");
        //database.QueryDate("DROP TABLE IF EXISTS " + db.detailDe_table);

        /*
            (END)  User create exam
         */


    }

    private void slideShow() {
        slideIn= AnimationUtils.loadAnimation(this,R.anim.slide_in);
        slideOut= AnimationUtils.loadAnimation(this,R.anim.slide_out);

        int[] image = {R.drawable.ic_slide_2,R.drawable.ic_slide_1,  R.drawable.ic_slide_3};

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