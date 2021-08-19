package com.nda.thi_lich_su_vao_10.fn.DeOnThi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nda.thi_lich_su_vao_10.R;
import com.nda.thi_lich_su_vao_10.fn.DetailDe_System;

import java.util.List;

public class adapterPracticeDethi extends RecyclerView.Adapter<adapterPracticeDethi.practiceDeThiViewHolder> {

    DeOnThi_System context;
    List<DeThi> deThiList;

    public adapterPracticeDethi(DeOnThi_System context, List<DeThi> deThiList) {
        this.context = context;
        this.deThiList = deThiList;
    }

    @Override
    public practiceDeThiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_topic,parent,false);
        return new practiceDeThiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  adapterPracticeDethi.practiceDeThiViewHolder holder, int position) {
        DeThi deThi = deThiList.get(position);

        holder.txt_showExamNumber.setText(deThi.getTopicNumber());
        holder.txt_showExamTitle.setText(deThi.getTopicTItle());

        holder.ll_showExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailDe_System.class);
                Bundle bundle = new Bundle();

                bundle.putBoolean("practice",true);
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

    public class practiceDeThiViewHolder extends RecyclerView.ViewHolder {

        TextView txt_showExamNumber,txt_showExamTitle;
        LinearLayout ll_showExam;

        public practiceDeThiViewHolder(@NonNull  View itemView) {
            super(itemView);
            txt_showExamNumber      = (TextView) itemView.findViewById(R.id.txt_showExamNumber);
            txt_showExamTitle       = (TextView) itemView.findViewById(R.id.txt_showExamTitle);

            ll_showExam     = (LinearLayout) itemView.findViewById(R.id.ll_showExam);
        }
    }
}
