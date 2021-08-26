package com.nda.thi_lich_su_vao_10.fn.DeThi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nda.thi_lich_su_vao_10.BuildConfig;
import com.nda.thi_lich_su_vao_10.R;
import com.nda.thi_lich_su_vao_10.fn.DeOnThi.DeOnThi;
import com.nda.thi_lich_su_vao_10.fn.DeOnThi.adapterPracticeDethi;
import com.nda.thi_lich_su_vao_10.fn.TamLy.TamLy;
import com.nda.thi_lich_su_vao_10.fn.nativeAds.AdapterWithNativeAd;
import com.startapp.sdk.ads.nativead.NativeAdPreferences;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class DeThi_System extends AppCompatActivity {
    private static final String LOG_TAG = "native_ads";
    /**
     Regarding native ads
     */
    @Nullable
    protected AdapterWithNativeAd adapter;
    RecyclerView rcv_nativeAds;
    CardView cv_nativeAds;

    /**
     * Activity fn
     */
    ImageView imgBack;
    TextView txt_title;

    /**
     * Setup RCV
     */
    RecyclerView rcv_showExamTopic;
    List<DeThi> deThiList;
    AdapterThi mAdapterThi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);
        mapting();
        initiate();
        setupRecycleView();

        nativeAds();

    }

    /**
     *  Method used to load topic to rcv
     */
    private void setupRecycleView() {
        deThiList  = new ArrayList<>();
        mAdapterThi = new AdapterThi(this,deThiList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcv_showExamTopic.setLayoutManager(linearLayoutManager);
        rcv_showExamTopic.setAdapter(mAdapterThi);

        readTopic_deThi();
    }
    private void initiate() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt_title.setText("Đề Thi Chính Thức");
    }
    private void readTopic_deThi() {

        InputStream inputStream = getResources().openRawResource(R.raw.thi_topic);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null)
            {
                String[] split =  line.split(";");
                DeThi deThi;
//||
                if (split[0].length() > 0  && split[1].length() > 0)
                {
                    deThi = new DeThi(split[0],split[1], split[2]);
                }
                else
                {
                    deThi = new DeThi("X","X","X");
                }

                deThiList.add(deThi);

            }
            mAdapterThi.notifyDataSetChanged();

        } catch (Exception e)
        {
            Toast.makeText(this, "Có Lỗi Xảy Ra : Load Topic Practice !", Toast.LENGTH_SHORT).show();
        }

    }

    private void mapting() {
        deThiList  = new ArrayList<>();
        mAdapterThi = new AdapterThi(this,deThiList);

        imgBack             = (ImageView) findViewById(R.id.imgBack);
        txt_title           = (TextView) findViewById(R.id.txt_title);
        rcv_showExamTopic   = (RecyclerView) findViewById(R.id.rcv_showExamTopic);

    }

    private void nativeAds() {
        // NOTE always use test ads during development and testing
        //StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);

//        setContentView(R.layout.recycler_view);

        cv_nativeAds  = (CardView) findViewById(R.id.cv_nativeAds);
        rcv_nativeAds = (RecyclerView) findViewById(R.id.rcv_nativeAds);
        rcv_nativeAds.setLayoutManager(new LinearLayoutManager(DeThi_System.this, RecyclerView.VERTICAL, false));
        rcv_nativeAds.setAdapter(adapter = new AdapterWithNativeAd(DeThi_System.this));

        loadData();
        loadNativeAd();
    }

    private void loadNativeAd() {
        final StartAppNativeAd nativeAd = new StartAppNativeAd(DeThi_System.this);

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
