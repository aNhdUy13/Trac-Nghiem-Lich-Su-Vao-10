package com.nda.thi_lich_su_vao_10.fn.DetailDe_Another;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nda.thi_lich_su_vao_10.R;

import java.util.List;

public class AdapterCheckAns extends BaseAdapter {

    List list;
    LayoutInflater inflater;

    public AdapterCheckAns(List list, Context context) {
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Question question = (Question) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_gridview_show_ans,null);
            viewHolder.txt_numAns = (TextView) convertView.findViewById(R.id.txt_numAns);
            viewHolder.txt_ans = (TextView) convertView.findViewById(R.id.txt_ans);

            convertView.setTag(viewHolder);
        }

        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txt_numAns.setText(question.getQuestionNumber());
        viewHolder.txt_ans.setText(question.getUserAns());

        return convertView;
    }

    private static class ViewHolder{
        TextView txt_numAns, txt_ans;
    }
}
