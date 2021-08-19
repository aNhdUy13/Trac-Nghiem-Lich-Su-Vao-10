package com.nda.thi_lich_su_vao_10.fn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nda.thi_lich_su_vao_10.R;
import com.nda.thi_lich_su_vao_10.fn.DetailDe.AdapterDetailDe;
import com.nda.thi_lich_su_vao_10.fn.DetailDe.DetailDe;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class DetailDe_System extends AppCompatActivity {
    // Dialog Submit - Timeout
    TextView txt_showNumberOfCorrectAns ,txt_content, txt_titleSubmitTimeout;
    Button btn_showResult  , btn_out;

    // Countdown Time
    TextView txt_timeOut;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliSecond = 5400000; // 90 min


    ImageView imgBack, img_submitExam;

    Button btn_outExam, btn_cancelOutExam;
    TextView txt_title, txt_titleStartOut;
    Intent intent;
    Bundle bundle;
    String topicNumber, examTitle;

    List<DetailDe> detailDeList;
    AdapterDetailDe mAdapterDetailDe;
    RecyclerView rcv_showDetailExamTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_de_system);
        mapting();
        initiate();

    }

    private void initiate() {
        dialogReady();

        img_submitExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSubmit();
            }
        });

        intent = getIntent();
        bundle = intent.getExtras();
        if (bundle.containsKey("practice"))
        {
            doPractice();
        }
        if (bundle.containsKey("deThiChinhThuc"))
        {
            doDeThiChinhThuc();
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOutExam();
            }
        });
    }


    private void dialogReady() {
        Dialog dialog = new Dialog(DetailDe_System.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_start_out_exam);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btn_outExam   = (Button) dialog.findViewById(R.id.btn_outExam);
        btn_cancelOutExam   = (Button) dialog.findViewById(R.id.btn_cancelOutExam);
        txt_titleStartOut   = (TextView) dialog.findViewById(R.id.txt_titleStartOut);
        txt_titleStartOut.setText("Sẵn Sàng Làm Bài ?");

        btn_outExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startCountDown();
            }
        });

        btn_cancelOutExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        dialog.show();
    }


    private void doDeThiChinhThuc() {
        topicNumber  = intent.getStringExtra("examNumber");
        examTitle   = intent.getStringExtra("examTitle");
        txt_title.setText(examTitle);

    }

    private void doPractice() {
        topicNumber  = intent.getStringExtra("examNumber");
        examTitle   = intent.getStringExtra("examTitle");

        setupRecycleView(topicNumber);

        txt_title.setText(examTitle);
    }

    private void setupRecycleView(String topicNumber) {
        detailDeList  = new ArrayList<>();
        mAdapterDetailDe = new AdapterDetailDe(this,detailDeList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcv_showDetailExamTopic.setLayoutManager(linearLayoutManager);
        rcv_showDetailExamTopic.setAdapter(mAdapterDetailDe);

        readDetail_luyenThi(topicNumber);
    }
    private void readDetail_luyenThi(String topicNumber) {
        InputStream inputStream = getResources().openRawResource(R.raw.luyen_thi_detail_topic);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream,Charset.forName("UTF-8"))
        );

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null)
            {

                String[] split =  line.split(";");

                DetailDe detailDe;

                if (split[0].equals(topicNumber))
                {
//                    Toast.makeText(this, "In Raw = " + split[0], Toast.LENGTH_LONG).show();

                    detailDe = new DetailDe(split[0],split[1],split[2], split[3],split[4],split[5], split[6], split[7]);
                    detailDeList.add(detailDe);
                }
            }
            mAdapterDetailDe.notifyDataSetChanged();

        } catch (Exception e)
        {
            Toast.makeText(this, "Có Lỗi Xảy Ra : Load Detail Practice !", Toast.LENGTH_SHORT).show();
        }

    }







    private void dialogOutExam() {
        Dialog dialog = new Dialog(DetailDe_System.this);
        dialog.setContentView(R.layout.dialog_start_out_exam);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btn_cancelOutExam   = (Button) dialog.findViewById(R.id.btn_cancelOutExam);
        btn_outExam         = (Button) dialog.findViewById(R.id.btn_outExam);

        btn_cancelOutExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_outExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialog.show();
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMilliSecond, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliSecond = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timeLeftInMilliSecond = 0;
                dialogTimeOut();

            }
        }.start();
    }

    private void updateTimer() {
        int minute = (int) timeLeftInMilliSecond/60000;
        int second = (int) timeLeftInMilliSecond % 60000 / 1000;

        String timeLefText ;
        timeLefText  = "" + minute;
        timeLefText += " : ";
        if (second < 10) timeLefText += "0";
        timeLefText += second;

        txt_timeOut.setText(timeLefText);

    }


    private void dialogSubmit() {
        Dialog dialog = new Dialog(DetailDe_System.this);
        dialog.setContentView(R.layout.dialog_submit_timeout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        txt_content                = (TextView) dialog.findViewById(R.id.txt_content);
        txt_titleSubmitTimeout     = (TextView) dialog.findViewById(R.id.txt_titleSubmitTimeout);
        txt_showNumberOfCorrectAns = (TextView) dialog.findViewById(R.id.txt_showNumberOfCorrectAns);
        btn_showResult = (Button) dialog.findViewById(R.id.btn_showResult);
        btn_out     = (Button) dialog.findViewById(R.id.btn_out);

        txt_showNumberOfCorrectAns.setVisibility(View.GONE);
        txt_content.setVisibility(View.GONE);
        txt_titleSubmitTimeout.setText("Nộp Bài ?");
        btn_showResult.setText("CÓ");
        btn_out.setText("HỦY");

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailDe_System.this, "Show Result", Toast.LENGTH_SHORT).show();
                countDownTimer.cancel();
            }
        });


        dialog.show();
    }


    private void dialogTimeOut() {
        Dialog dialog = new Dialog(DetailDe_System.this);
        dialog.setContentView(R.layout.dialog_submit_timeout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        txt_showNumberOfCorrectAns = (TextView) dialog.findViewById(R.id.txt_showNumberOfCorrectAns);

        btn_showResult = (Button) dialog.findViewById(R.id.btn_showResult);
        btn_out     = (Button) dialog.findViewById(R.id.btn_out);
        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailDe_System.this, "Show Result", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        dialog.show();
    }



    private void mapting() {
        img_submitExam      = (ImageView) findViewById(R.id.img_submitExam);
        rcv_showDetailExamTopic   = (RecyclerView) findViewById(R.id.rcv_showDetailExamTopic);
        imgBack     = (ImageView) findViewById(R.id.imgBack);
        txt_title   = (TextView) findViewById(R.id.txt_title);

        txt_timeOut = (TextView) findViewById(R.id.txt_timeOut);
    }

    @Override
    public void onBackPressed() {
        dialogOutExam();
    }
}