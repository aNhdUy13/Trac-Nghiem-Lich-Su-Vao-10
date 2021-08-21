package com.nda.thi_lich_su_vao_10.fn.DetailDe;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nda.thi_lich_su_vao_10.R;

import java.util.List;

public class AdapterDetailDe extends RecyclerView.Adapter<AdapterDetailDe.detailDeViewHolder> {

    DetailDe_System context;
    List<DetailDe> detailDeList;

    public AdapterDetailDe(DetailDe_System context, List<DetailDe> detailDeList) {
        this.context = context;
        this.detailDeList = detailDeList;
    }

    @Override
    public detailDeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_detail_exam,parent,false);
        return new AdapterDetailDe.detailDeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterDetailDe.detailDeViewHolder holder, int position) {
        DetailDe de = detailDeList.get(position);

        holder.txt_questionNumber.setText(de.getQuestionNumber());
        holder.txt_questionDetail.setText(de.getQuestion());

        holder.radioBtn_ansA.setText(de.getAnsA());
        holder.radioBtn_ansB.setText(de.getAnsB());
        holder.radioBtn_ansC.setText(de.getAnsC());
        holder.radioBtn_ansD.setText(de.getAnsD());


        context.showResult(holder.ll_result, holder.radioBtn_ansA, holder.radioBtn_ansB, holder.radioBtn_ansC,
                holder.radioBtn_ansD,position);

        holder.txt_showCorrectAns.setText(de.getCorrectAns());



        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Toast.makeText(context, "" + checkedId, Toast.LENGTH_SHORT).show();

                String correctAns = de.getCorrectAns();
                switch (checkedId)
                {
                    case R.id.radioBtn_ansA:
                        holder.txt_showUserAns.setText("A");
                        changeBackground(holder.txt_showUserAns, correctAns, holder.ll_result);
                        //Toast.makeText(context, "" + de.getAnsA(), Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.radioBtn_ansB:
                        holder.txt_showUserAns.setText("B");
                        changeBackground(holder.txt_showUserAns, correctAns, holder.ll_result);

                        //Toast.makeText(context, "" + de.getAnsB(), Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.radioBtn_ansC:
                        holder.txt_showUserAns.setText("C");
                        changeBackground(holder.txt_showUserAns, correctAns, holder.ll_result);

                        //Toast.makeText(context, "" + de.getAnsC(), Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.radioBtn_ansD:
                        holder.txt_showUserAns.setText("D");
                        changeBackground(holder.txt_showUserAns, correctAns, holder.ll_result);

                        //Toast.makeText(context, "" + de.getAnsD(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
    private void changeBackground(TextView txt_showUserAns, String correctAns, LinearLayout ll_result)
    {
        String userAns = txt_showUserAns.getText().toString().trim();
        if (userAns.equals(correctAns))
        {
            ll_result.setBackgroundColor(Color.parseColor("#4CAF50"));
        }
    }

    @Override
    public int getItemCount() {
        return detailDeList.size();
    }

    public class detailDeViewHolder extends RecyclerView.ViewHolder {
        TextView txt_questionNumber, txt_questionDetail, txt_showUserAns, txt_showCorrectAns;
        RadioButton radioBtn_ansA, radioBtn_ansB, radioBtn_ansC, radioBtn_ansD;

        RadioGroup radioGroup;
        LinearLayout ll_result;
        public detailDeViewHolder(@NonNull  View itemView) {
            super(itemView);
            ll_result   = (LinearLayout) itemView.findViewById(R.id.ll_result);

            txt_showUserAns         = (TextView) itemView.findViewById(R.id.txt_showUserAns);
            txt_showCorrectAns      = (TextView) itemView.findViewById(R.id.txt_showCorrectAns);

            txt_questionNumber      = (TextView) itemView.findViewById(R.id.txt_questionNumber);
            txt_questionDetail      = (TextView) itemView.findViewById(R.id.txt_questionDetail);

            radioGroup          = (RadioGroup) itemView.findViewById(R.id.radioGroup);

            radioBtn_ansA       = (RadioButton) itemView.findViewById(R.id.radioBtn_ansA);
            radioBtn_ansB       = (RadioButton) itemView.findViewById(R.id.radioBtn_ansB);
            radioBtn_ansC       = (RadioButton) itemView.findViewById(R.id.radioBtn_ansC);
            radioBtn_ansD       = (RadioButton) itemView.findViewById(R.id.radioBtn_ansD);




        }
    }
}
