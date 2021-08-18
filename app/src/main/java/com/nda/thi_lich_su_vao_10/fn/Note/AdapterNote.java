package com.nda.thi_lich_su_vao_10.fn.Note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nda.thi_lich_su_vao_10.R;

import java.util.List;

public class AdapterNote extends RecyclerView.Adapter<AdapterNote.noteViewHolder>{

    Note_System context;
    List<Note> noteList;

    public AdapterNote(Note_System context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @Override
    public noteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);
        return new noteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterNote.noteViewHolder holder, int position) {
        Note note = noteList.get(position);
        if (note == null)
        {return;}

        holder.txt_showNoteTitle.setText(note.getNoteTitle());

        holder.img_deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.deleteNote(note.getId());
            }
        });

        holder.cv_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.updateNote(note.getId(), note.getNoteTitle(), note.getNoteContent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class noteViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_showNoteTitle;
        ImageView img_deleteNote;
        CardView cv_note;

        public noteViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_showNoteTitle      = (TextView) itemView.findViewById(R.id.txt_showNoteTitle);

            img_deleteNote    = (ImageView) itemView.findViewById(R.id.img_deleteNote);
            cv_note        = (CardView) itemView.findViewById(R.id.cv_note);

        }
    }
}
