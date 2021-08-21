package com.nda.thi_lich_su_vao_10.fn.DeOnThi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nda.thi_lich_su_vao_10.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class DeOnThi_System extends AppCompatActivity {
    ImageView imgBack;
    TextView txt_title;

    List<DeThi> deThiList;
    adapterPracticeDethi mAdapterPracticeDethi;
    RecyclerView rcv_showExamTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);
        mapting();
        initiate();

        setupRecycleView();


    }
    private void setupRecycleView() {
        deThiList  = new ArrayList<>();
        mAdapterPracticeDethi = new adapterPracticeDethi(this,deThiList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcv_showExamTopic.setLayoutManager(linearLayoutManager);
        rcv_showExamTopic.setAdapter(mAdapterPracticeDethi);

        readTopic_LuyenThi();
    }
    private void readTopic_LuyenThi() {
        InputStream inputStream = getResources().openRawResource(R.raw.luyen_thi_topic);
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
                    deThi = new DeThi(split[0],split[1]);
                }
                else
                {
                    deThi = new DeThi("0","0");


                }

                deThiList.add(deThi);
                
            }
            mAdapterPracticeDethi.notifyDataSetChanged();

        } catch (Exception e)
        {
            Toast.makeText(this, "Có Lỗi Xảy Ra : Load Topic Practice !", Toast.LENGTH_SHORT).show();
        }

    }

    private void initiate() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt_title.setText("Đề Ôn Thi Vào 10");
    }

    private void mapting() {
        imgBack             = (ImageView) findViewById(R.id.imgBack);
        txt_title           = (TextView) findViewById(R.id.txt_title);
        rcv_showExamTopic   = (RecyclerView) findViewById(R.id.rcv_showExamTopic);

    }

}