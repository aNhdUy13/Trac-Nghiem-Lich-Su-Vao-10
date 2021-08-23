package com.nda.thi_lich_su_vao_10.fn.Note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nda.thi_lich_su_vao_10.MainActivity;
import com.nda.thi_lich_su_vao_10.R;

import java.util.ArrayList;
import java.util.List;

public class Note_System extends AppCompatActivity {

    ImageView imgBack, img_addNote;


    Intent intent;

    /*
        Dialog
     */
    Button btn_allow_delete, btn_cancel_delete;
    /*
        (END)  Dialog
    */
    List<Note> noteList;
    AdapterNote mAdapterNote;
    RecyclerView rcv_showNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_system);

        mapting();
        initiate();
        setupRecycleView();
    }

    private void initiate() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Note_System.this,MainActivity.class));
            }
        });
        img_addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), NoteDetail.class);
                Bundle extras = new Bundle();
                extras.putBoolean("AddNote", true);
                intent.putExtras(extras);

                startActivity(intent);
            }
        });
    }
    private void setupRecycleView()
    {
        noteList = new ArrayList<>();

        mAdapterNote = new AdapterNote(this,noteList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        rcv_showNote.setLayoutManager(linearLayoutManager);
        rcv_showNote.setAdapter(mAdapterNote);

        displayNote();
    }
    private void displayNote()
    {
        Cursor cursor = MainActivity.database.GetData(
                "SELECT * FROM note_table ");
        noteList.clear();
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String noteTitle = cursor.getString(1);
            String noteContent = cursor.getString(2);


            noteList.add(new Note(id,noteTitle, noteContent));
        }

        mAdapterNote.notifyDataSetChanged();
    }
    private void mapting() {
        imgBack         = (ImageView) findViewById(R.id.imgBack);
        img_addNote     = (ImageView) findViewById(R.id.img_addNote);
        rcv_showNote    = (RecyclerView) findViewById(R.id.rcv_showNote);
    }

    public void deleteNote(int id) {
        Dialog dialog = new Dialog(Note_System.this);
        dialog.setContentView(R.layout.dialog_delete);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btn_allow_delete = (Button) dialog.findViewById(R.id.btn_allow_delete);
        btn_cancel_delete = (Button) dialog.findViewById(R.id.btn_cancel_delete);

        btn_allow_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.database.QueryData("DELETE FROM note_table WHERE note_Id = '" + id + "'");

                Toast.makeText(getApplicationContext(),"Delete Successful ! ",Toast.LENGTH_LONG).show();
                displayNote();
                dialog.dismiss();

            }
        });
        btn_cancel_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void updateNote(int id, String noteTitle, String noteContent) {
        intent = new Intent(getApplicationContext(), NoteDetail.class);
        Bundle extras = new Bundle();
        extras.putBoolean("UpdateNOte", true);
        extras.putInt("updateId",id);
        extras.putString("updateTitle", noteTitle);
        extras.putString("updateContent", noteContent);

        intent.putExtras(extras);

        startActivity(intent);
    }
}