package com.nda.thi_lich_su_vao_10.fn.DetailDe_Another;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nda.thi_lich_su_vao_10.R;
import com.nda.thi_lich_su_vao_10.fn.DetailDe.DetailDe;
import com.nda.thi_lich_su_vao_10.fn.DetailDe.DetailDe_System;
import com.nda.thi_lich_su_vao_10.fn.DoneExam.ExamFinished;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ScreenSlideActivity extends FragmentActivity {

    public int checkAns = 0;

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 40;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;

    /**
     *     Countdown Time
      */
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliSecond = 5400000; // 90 min = 5400000

    /**
     *     Get Data from Topic - Load it
      */
    Intent intent;
    Bundle bundle;
    String topicNumber, examTitle;
    ImageView imgBack, img_submitExam ,img_checkGrade;

    Button btn_outExam, btn_cancelOutExam;
    TextView txt_title, txt_titleStartOut, txt_timeOut;

    List<Question>  questionList = new ArrayList<>();

    /**
     *  Dialog Submit - Timeout
     */
    TextView txt_showNumberOfCorrectAns ,txt_content, txt_titleSubmitTimeout;
    Button btn_showResult  , btn_out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        mapting();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());

        initiate();
    }

    private void initiate()
    {

        img_checkGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                Intent intent = new Intent(ScreenSlideActivity.this, ExamFinished.class);
                intent.putExtra("listQuestion", (Serializable) questionList);
                startActivity(intent);
            }
        });
        img_submitExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSubmit();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOutExam();
            }
        });
        intent = getIntent();
        bundle = intent.getExtras();
        if (bundle.containsKey("practice"))
        {
            dialogReady();

            doPractice();
        }
        if (bundle.containsKey("thi"))
        {
            dialogReady();

            doDeThiChinhThuc();
        }
    }


    private void dialogReady() {
        Dialog dialog = new Dialog(ScreenSlideActivity.this);
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

    private void dialogOutExam() {
        Dialog dialog = new Dialog(ScreenSlideActivity.this);
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
        Dialog dialog = new Dialog(ScreenSlideActivity.this);
        dialog.setContentView(R.layout.dialog_submit_timeout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        txt_content                = (TextView) dialog.findViewById(R.id.txt_content);
        txt_titleSubmitTimeout     = (TextView) dialog.findViewById(R.id.txt_titleSubmitTimeout);
//        txt_showNumberOfCorrectAns = (TextView) dialog.findViewById(R.id.txt_showNumberOfCorrectAns);
        btn_showResult = (Button) dialog.findViewById(R.id.btn_showResult);
        btn_out     = (Button) dialog.findViewById(R.id.btn_out);

//        txt_showNumberOfCorrectAns.setVisibility(View.GONE);
        txt_titleSubmitTimeout.setText("Nộp Bài");
        btn_showResult.setText("CÓ");
//        txt_content.setText("Bạn Muốn Nộp Bài ?");
        btn_out.setText("HỦY");

      showGridUserAns(dialog);

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showResultWhenSubmit_Timeout();

                dialog.dismiss();


            }
        });


        dialog.show();
    }

    private void showResultWhenSubmit_Timeout() {
        countDownTimer.cancel();

        img_submitExam.setVisibility(View.GONE);
        img_checkGrade.setVisibility(View.VISIBLE);
        /**
         *  checkAns = 1 => chương trình end và bắt đầu hiện background
         */
        checkAns = 1;
        if (mPager.getCurrentItem() >= 4)   mPager.setCurrentItem(mPager.getCurrentItem() - 4);

        else if (mPager.getCurrentItem() <= 4)  mPager.setCurrentItem(mPager.getCurrentItem() + 4);
    }

    private void showGridUserAns(Dialog dialog)
    {
        AdapterCheckAns adapterCheckAns = new AdapterCheckAns(questionList,this);
        GridView gvListAns = (GridView) dialog.findViewById(R.id.gv_listQuestion_When_SubmitTimeout);

        gvListAns.setAdapter(adapterCheckAns);

        gvListAns.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Go to specific question
                mPager.setCurrentItem(position);

                dialog.dismiss();


            }
        });
    }

    private void dialogTimeOut() {
        Dialog dialog = new Dialog(ScreenSlideActivity.this);
        dialog.setContentView(R.layout.dialog_submit_timeout);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


//        txt_showNumberOfCorrectAns = (TextView) dialog.findViewById(R.id.txt_showNumberOfCorrectAns);


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
                showResultWhenSubmit_Timeout();
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    /**
     *  When user want to practice
     */
    private void doPractice()
    {
        topicNumber  = intent.getStringExtra("examNumber");
        examTitle   = intent.getStringExtra("examTitle");

        txt_title.setText(examTitle);
        readDetail_luyenThi(topicNumber);
    }
    private void readDetail_luyenThi(String topicNumber) {
        InputStream inputStream = getResources().openRawResource(R.raw.luyen_thi_detail_topic);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null)
            {

                String[] split =  line.split(";");

                Question question;

                if (split[0].equals(topicNumber))
                {
//                    Toast.makeText(this, "In Raw = " + split[0], Toast.LENGTH_LONG).show();

                    question = new Question(split[0],split[1],split[2], split[3],split[4],split[5], split[6], split[7], "");
                    questionList.add(question);
                }
            }

        } catch (Exception e)
        {
            Toast.makeText(this, "Có Lỗi Xảy Ra : Load Detail Practice !", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     *  When user want to thi
     */
    private void doDeThiChinhThuc()
    {
        topicNumber  = intent.getStringExtra("examNumber");
        examTitle   = intent.getStringExtra("examTitle");

        txt_title.setText(examTitle);
        readDetail_thi(topicNumber);
    }
    private void readDetail_thi(String topicNumber)
    {
        InputStream inputStream = getResources().openRawResource(R.raw.thi_detail);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null)
            {

                String[] split =  line.split(";");

                Question question;

                if (split[0].equals(topicNumber))
                {
//                    Toast.makeText(this, "In Raw = " + split[0], Toast.LENGTH_LONG).show();

                    question = new Question(split[0],split[1],split[2], split[3],split[4],split[5], split[6], split[7], "");
                    questionList.add(question);
                }
            }

        } catch (Exception e)
        {
            Toast.makeText(this, "Có Lỗi Xảy Ra : Load Detail Practice !", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     * @return The list of question (contain question, ans, etc)
     */
    public List<Question> getData()
    {
        return questionList;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            dialogOutExam();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            /**
             *  Khi checkAns = 0 => Chương trình chưa kết thúc.
             *
             *  Khi chuyền checkAns = 1 sang fragment ( nó biết là  != 0 ) => Kết thúc và hiện background của đáp án đúng.
             */
            return ScreenSlidePageFragment.create(position, checkAns);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


    /**
     * Di Chuyển GIữa các fragment ( câu hỏi )
     */
    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }


    private void mapting() {
        img_checkGrade      = (ImageView) findViewById(R.id.img_checkGrade);
        img_submitExam      = (ImageView) findViewById(R.id.img_submitExam);
        imgBack     = (ImageView) findViewById(R.id.imgBack);
        txt_title   = (TextView) findViewById(R.id.txt_title);

        txt_timeOut = (TextView) findViewById(R.id.txt_timeOut);
    }
}