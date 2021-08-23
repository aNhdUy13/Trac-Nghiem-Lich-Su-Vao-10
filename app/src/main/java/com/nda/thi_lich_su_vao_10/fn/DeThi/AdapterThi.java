package com.nda.thi_lich_su_vao_10.fn.DeThi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nda.thi_lich_su_vao_10.R;
import com.nda.thi_lich_su_vao_10.fn.DeOnThi.DeOnThi;
import com.nda.thi_lich_su_vao_10.fn.DeOnThi.DeOnThi_System;
import com.nda.thi_lich_su_vao_10.fn.DeOnThi.adapterPracticeDethi;
import com.nda.thi_lich_su_vao_10.fn.DetailDe_Another.ScreenSlideActivity;

import java.util.List;

public class AdapterThi extends RecyclerView.Adapter<AdapterThi.thiViewHolder> {
    DeThi_System context;
    List<DeThi> deThiList;

    public AdapterThi(DeThi_System context, List<DeThi> deThiList) {
        this.context = context;
        this.deThiList = deThiList;
    }

    @Override
    public AdapterThi.thiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_topic,parent,false);
        return new AdapterThi.thiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterThi.thiViewHolder holder, int position) {
        DeThi deThi = deThiList.get(position);

        holder.txt_showExamNumber.setText(deThi.getTopicNumber());
        holder.txt_showExamTitle.setText(deThi.getTopicTItle());
        holder.txt_showNumberQues.setText(deThi.getNumQues());
//        holder.ll_showExam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailDe_System.class);
//                Bundle bundle = new Bundle();
//
//                bundle.putBoolean("practice",true);
//                bundle.putString("examNumber",deThi.getTopicNumber());
//                bundle.putString("examTitle",deThi.getTopicTItle());
//                intent.putExtras(bundle);
//
//                context.startActivity(intent);
//            }
//        });

        // Work with another
        holder.ll_showExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ScreenSlideActivity.class);
                Bundle bundle = new Bundle();

                bundle.putBoolean("thi",true);
                bundle.putString("examNumber",deThi.getTopicNumber());
                bundle.putString("examTitle",deThi.getTopicTItle());
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deThiList.size();
    }

    public class thiViewHolder extends RecyclerView.ViewHolder {
        TextView txt_showExamNumber,txt_showExamTitle, txt_showNumberQues;
        LinearLayout ll_showExam;

        public thiViewHolder(@NonNull  View itemView) {
            super(itemView);
            txt_showExamNumber      = (TextView) itemView.findViewById(R.id.txt_showExamNumber);
            txt_showExamTitle       = (TextView) itemView.findViewById(R.id.txt_showExamTitle);
            txt_showNumberQues      = (TextView) itemView.findViewById(R.id.txt_showNumberQues);
            ll_showExam     = (LinearLayout) itemView.findViewById(R.id.ll_showExam);
        }
    }
}
