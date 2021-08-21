package com.nda.thi_lich_su_vao_10.fn.DetailDe_Another;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nda.thi_lich_su_vao_10.R;

import java.util.ArrayList;
import java.util.List;


public class ScreenSlidePageFragment extends Fragment {
    public static final String ARG_PAGE = "page";
    private static final String ARG_CHECKANS = "checkAnswer";

    /**
     *  Biến Kiểm Tra
     */
    public int checkAns;


    /**
     *  Item
     */
    TextView txt_questionNumber,txt_questionDetail;
    RadioGroup radioGroup;
    RadioButton radioBtn_ansA,radioBtn_ansB, radioBtn_ansC, radioBtn_ansD;

    List<Question> questionList = new ArrayList<>();




    /**
     *  get Current Page
      */
    private int mPageNumber;


    public ScreenSlidePageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page,container,false
        );

        txt_questionNumber  = (TextView) rootView.findViewById(R.id.txt_questionNumber);
        txt_questionDetail  = (TextView) rootView.findViewById(R.id.txt_questionDetail);
        radioGroup      = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        radioBtn_ansA   = (RadioButton) rootView.findViewById(R.id.radioBtn_ansA);
        radioBtn_ansB   = (RadioButton) rootView.findViewById(R.id.radioBtn_ansB);
        radioBtn_ansC   = (RadioButton) rootView.findViewById(R.id.radioBtn_ansC);
        radioBtn_ansD   = (RadioButton) rootView.findViewById(R.id.radioBtn_ansD);


        return rootView;
    }

    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScreenSlideActivity screenSlideActivity = (ScreenSlideActivity) getActivity();

        questionList = screenSlideActivity.getData();

        mPageNumber = getArguments().getInt(ARG_PAGE);

        checkAns = getArguments().getInt(ARG_CHECKANS);

    }
    public static ScreenSlidePageFragment create(int pageNumber, int checkAnswer)
    {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();

        Bundle bundle = new Bundle();

        bundle.putInt(ARG_PAGE, pageNumber);
        bundle.putInt(ARG_CHECKANS, checkAnswer);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable  Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            txt_questionNumber.setText(getItem(mPageNumber).getQuestionNumber());
            txt_questionDetail.setText(getItem(mPageNumber).getQuestion());

            radioBtn_ansA.setText(getItem(mPageNumber).getAnsA());
            radioBtn_ansB.setText(getItem(mPageNumber).getAnsB());
            radioBtn_ansC.setText(getItem(mPageNumber).getAnsC());
            radioBtn_ansD.setText(getItem(mPageNumber).getAnsD());

            if (checkAns != 0 )
            {
                radioBtn_ansA.setClickable(false);
                radioBtn_ansB.setClickable(false);
                radioBtn_ansC.setClickable(false);
                radioBtn_ansD.setClickable(false);

                getCheckAns(getItem(mPageNumber).getCorrectAns());

            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    /**
                     *  choiceId là biến tạo trong Question .. dùng để gán giá trị checkedId
                     */
                    getItem(mPageNumber).choiceId = checkedId;

                    getItem(mPageNumber).setUserAns(getChoiceFromID(checkedId));
//                    Toast.makeText(getContext(), "" + checkedId, Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error : SSPF - Not enough element", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     *
     * Lấy Vị trí của page hiện tại
     */
    private Question getItem(int mPageNumber)
    {
        return questionList.get(mPageNumber);
    }


    /**
     *  Lấy giá trị của radio group chuyển
     *  thành đáp án A , B , C ,D
     */
    private String getChoiceFromID(int ID)
    {
        if (ID == R.id.radioBtn_ansA)
        {
            return "A";
        }
        else if (ID == R.id.radioBtn_ansB)
        {
            return "B";
        }
        else if (ID == R.id.radioBtn_ansC)
        {
            return "C";
        }
        else if (ID == R.id.radioBtn_ansD)
        {
            return "D";
        }
        else
            return "";
    }


    /**
     *  Kiểm tra câu đúng
     */
    private void getCheckAns(String ans)
    {
        if (ans.equals("A"))
        {
            radioBtn_ansA.setBackgroundColor(Color.parseColor("#8BC34A"));
        }
        else if (ans.equals("B"))
        {
            radioBtn_ansB.setBackgroundColor(Color.parseColor("#8BC34A"));

        }
        else if (ans.equals("C"))
        {
            radioBtn_ansC.setBackgroundColor(Color.parseColor("#8BC34A"));

        }
        else if (ans.equals("D"))
        {
            radioBtn_ansD.setBackgroundColor(Color.parseColor("#8BC34A"));

        }
        else;
    }


}