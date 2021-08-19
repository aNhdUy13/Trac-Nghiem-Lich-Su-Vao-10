package com.nda.thi_lich_su_vao_10.fn.DetailDe;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nda.thi_lich_su_vao_10.R;
import com.nda.thi_lich_su_vao_10.fn.DeOnThi.adapterPracticeDethi;
import com.nda.thi_lich_su_vao_10.fn.DetailDe_System;

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

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.radioBtn_ansA:
                        Toast.makeText(context, "" + de.getAnsA(), Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.radioBtn_ansB:
                        Toast.makeText(context, "" + de.getAnsB(), Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.radioBtn_ansC:
                        Toast.makeText(context, "" + de.getAnsC(), Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.radioBtn_ansD:
                        Toast.makeText(context, "" + de.getAnsD(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailDeList.size();
    }

    public class detailDeViewHolder extends RecyclerView.ViewHolder {
        TextView txt_questionNumber, txt_questionDetail;
        RadioButton radioBtn_ansA, radioBtn_ansB, radioBtn_ansC, radioBtn_ansD;

        RadioGroup radioGroup;
        public detailDeViewHolder(@NonNull  View itemView) {
            super(itemView);

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
