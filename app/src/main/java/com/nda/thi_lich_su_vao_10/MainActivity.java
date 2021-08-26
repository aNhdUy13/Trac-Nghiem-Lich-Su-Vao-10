package com.nda.thi_lich_su_vao_10;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.nda.thi_lich_su_vao_10.fn.DeOnThi.DeOnThi_System;
import com.nda.thi_lich_su_vao_10.fn.DeOnThi.DeOnThi;
import com.nda.thi_lich_su_vao_10.fn.DeThi.DeThi_System;
import com.nda.thi_lich_su_vao_10.fn.Note.Note_System;
import com.nda.thi_lich_su_vao_10.fn.TamLy.TamLy;
import com.nda.thi_lich_su_vao_10.fn.db;
import com.nda.thi_lich_su_vao_10.fn.nativeAds.AdapterWithNativeAd;
import com.startapp.sdk.ads.nativead.NativeAdPreferences;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "native_ads";
    /**
     Regarding native ads
     */
    @Nullable
    protected AdapterWithNativeAd adapter;
    RecyclerView rcv_nativeAds;
    CardView cv_nativeAds;

    public static db database;

    ViewFlipper vf_main;
    Animation slideIn, slideOut;
    CardView cv_OnThi ,cv_DeThi, cv_YourQuiz, cv_note, cv_tamly, cv_share;

    List<DeOnThi> deThiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //StartAppSDK.setTestAdsEnabled(true);
        StartAppAd.disableSplash();

        mappting();
        initiate();
        slideShow();

        createDatabase();

        nativeAds();

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

        int[] image = {R.drawable.ic_slide_2,R.drawable.ic_slide_4,R.drawable.ic_slide_1,  R.drawable.ic_slide_3, R.drawable.ic_slide_5};

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

//        cv_YourQuiz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, UserDe_System.class));
//            }
//        });

        cv_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Note_System.class));
            }
        });

        cv_tamly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TamLy.class));
            }
        });

        shareApp();


    }

    private void shareApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String shareBody = "https://play.google.com/store/apps/details?id=com.nda.thi_lich_su_vao_10";
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        cv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(Intent.createChooser(intent, getString(R.string.share_using)));

//                    startActivity(sendIntent);
                } catch (Exception e)
                { e.printStackTrace();}
            }
        });
    }

    private void mappting() {
        vf_main = (ViewFlipper) findViewById(R.id.vf_main);

        cv_OnThi     = (CardView) findViewById(R.id.cv_OnThi);
        cv_DeThi     = (CardView) findViewById(R.id.cv_DeThi);
        //cv_YourQuiz  = (CardView) findViewById(R.id.cv_YourQuiz);
        cv_note      = (CardView) findViewById(R.id.cv_note);

        cv_tamly     = (CardView) findViewById(R.id.cv_tamly);
        cv_share     = (CardView) findViewById(R.id.cv_share);
    }


    private void nativeAds() {
        // NOTE always use test ads during development and testing
        //StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);

//        setContentView(R.layout.recycler_view);

        cv_nativeAds  = (CardView) findViewById(R.id.cv_nativeAds);
        rcv_nativeAds = (RecyclerView) findViewById(R.id.rcv_nativeAds);
        rcv_nativeAds.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        rcv_nativeAds.setAdapter(adapter = new AdapterWithNativeAd(MainActivity.this));

        loadData();
        loadNativeAd();
    }

    private void loadNativeAd() {
        final StartAppNativeAd nativeAd = new StartAppNativeAd(MainActivity.this);

        nativeAd.loadAd(new NativeAdPreferences()
                .setAdsNumber(1)
                .setAutoBitmapDownload(true)
                .setPrimaryImageSize(2), new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                if (adapter != null) {
                    cv_nativeAds.setVisibility(View.VISIBLE);
                    adapter.setNativeAd(nativeAd.getNativeAds());
                }
            }

            @Override
            public void onFailedToReceiveAd(Ad ad) {
                if (BuildConfig.DEBUG) {
                    Log.v(LOG_TAG, "onFailedToReceiveAd: " + ad.getErrorMessage());
                }
            }
        });
    }

    // TODO example of loading JSON array, change this code according to your needs
    @UiThread
    private void loadData() {
        if (adapter != null) {
//            adapter.setData(Collections.singletonList("Loading..."));
        }

        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            @WorkerThread
            public void run() {
                String url = "https://raw.githubusercontent.com/StartApp-SDK/StartApp_InApp_SDK_Example/master/app/data.json";

                final List<String> data = new ArrayList<>();

                try (InputStream is = new URL(url).openStream()) {
                    if (is != null) {
                        JsonReader reader = new JsonReader(new InputStreamReader(is));
                        reader.beginArray();

                        while (reader.peek() == JsonToken.STRING) {
                            data.add(reader.nextString());
                        }

                        reader.endArray();
                    }
                } catch (RuntimeException | IOException ex) {
                    data.clear();
                    data.add(ex.toString());
                } finally {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (adapter != null) {
//                                adapter.setData(data);
//                            }
//                        }
//                    });
                }
            }
        });
    }
}