package com.nda.thi_lich_su_vao_10.fn.Note;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nda.thi_lich_su_vao_10.BuildConfig;
import com.nda.thi_lich_su_vao_10.MainActivity;
import com.nda.thi_lich_su_vao_10.R;
import com.nda.thi_lich_su_vao_10.fn.TamLy.TamLy;
import com.nda.thi_lich_su_vao_10.fn.nativeAds.AdapterWithNativeAd;
import com.startapp.sdk.ads.nativead.NativeAdPreferences;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NoteDetail extends AppCompatActivity {
    private static final String LOG_TAG = "native_ads";
    /**
     Regarding native ads
     */
    @Nullable
    protected AdapterWithNativeAd adapter;
    RecyclerView rcv_nativeAds;
    CardView cv_nativeAds;


    Intent intent;
    Bundle bundle;
    TextView txt_titleNoteDetail;
    ImageView imgBack, img_cancel, img_add_update_note;
    EditText edt_noteTitle, edt_noteContent;

    String title, content;

    int noteId;
    String noteTitle, noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        mapting();
        initiate();

        nativeAds();
    }

    private void initiate()
    {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        intent = getIntent();
        bundle = intent.getExtras();

        if (bundle.containsKey("AddNote"))
        {
            addNote();

        }

        if (bundle.containsKey("UpdateNOte"))
        {
            updateNote();

        }
    }

    private void updateNote() {
        txt_titleNoteDetail.setText("Cập Nhập Ghi Chú");

        noteId = intent.getIntExtra("updateId",0);
        noteTitle = intent.getStringExtra("updateTitle");
        noteContent = intent.getStringExtra("updateContent");

        edt_noteTitle.setText(noteTitle);
        edt_noteContent.setText(noteContent);

        img_add_update_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = edt_noteTitle.getText().toString().trim();
                content = edt_noteContent.getText().toString().trim();

                if (title.isEmpty() )
                {
                    edt_noteTitle.setError("Không Được Trống !");
                }

                else
                {
                    //Toast.makeText(NoteDetail.this, "id : " + noteId, Toast.LENGTH_SHORT).show();

                    MainActivity.database.QueryData("UPDATE note_table SET note_title = '" + title
                            + "' , note_Content = '" + content + "'  WHERE note_Id = '" + noteId + "' ");

                   startActivity(new Intent(NoteDetail.this,Note_System.class));
                }


            }
        });
    }

    private void addNote() {
        txt_titleNoteDetail.setText("Thêm Ghi Chú");

        img_add_update_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = edt_noteTitle.getText().toString().trim();
                content = edt_noteContent.getText().toString().trim();

                if (title.isEmpty())
                {
                    edt_noteTitle.setError("Không Được Trống !");
                }

                else
                {
                    MainActivity.database.INSERT_NOTE(title, content);
                    startActivity(new Intent(NoteDetail.this,Note_System.class));
                }


            }
        });

    }

    private void mapting() {
        txt_titleNoteDetail = (TextView) findViewById(R.id.txt_titleNoteDetail);

        imgBack                  = (ImageView) findViewById(R.id.imgBack);
        img_cancel               = (ImageView) findViewById(R.id.img_cancel);
        img_add_update_note      = (ImageView) findViewById(R.id.img_add_update_note);


        edt_noteTitle     = (EditText) findViewById(R.id.edt_noteTitle);
        edt_noteContent   = (EditText) findViewById(R.id.edt_noteContent);

    }

    private void nativeAds() {
        // NOTE always use test ads during development and testing
        //StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);

//        setContentView(R.layout.recycler_view);

        cv_nativeAds  = (CardView) findViewById(R.id.cv_nativeAds);
        rcv_nativeAds = (RecyclerView) findViewById(R.id.rcv_nativeAds);
        rcv_nativeAds.setLayoutManager(new LinearLayoutManager(NoteDetail.this, RecyclerView.VERTICAL, false));
        rcv_nativeAds.setAdapter(adapter = new AdapterWithNativeAd(NoteDetail.this));

        loadData();
        loadNativeAd();
    }

    private void loadNativeAd() {
        final StartAppNativeAd nativeAd = new StartAppNativeAd(NoteDetail.this);

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