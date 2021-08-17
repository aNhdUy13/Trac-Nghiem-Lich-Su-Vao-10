package com.nda.thi_lich_su_vao_10.fn.DeOnThi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nda.thi_lich_su_vao_10.R;

public class DeOnThi_System extends AppCompatActivity {
    ImageView imgBack;
    TextView txt_title;
    RecyclerView rcv_showExamTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);
        mapting();
        initiate();
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