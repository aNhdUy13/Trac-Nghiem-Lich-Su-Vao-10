package com.nda.thi_lich_su_vao_10.fn.DeThi;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nda.thi_lich_su_vao_10.R;

public class DeThi_System extends AppCompatActivity {
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

        txt_title.setText("Đề Thi Chính Thức");
    }

    private void mapting() {
        imgBack             = (ImageView) findViewById(R.id.imgBack);
        txt_title           = (TextView) findViewById(R.id.txt_title);
        rcv_showExamTopic   = (RecyclerView) findViewById(R.id.rcv_showExamTopic);

    }
}
