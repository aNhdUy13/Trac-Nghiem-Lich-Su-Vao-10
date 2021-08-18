package com.nda.thi_lich_su_vao_10.fn.Note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nda.thi_lich_su_vao_10.MainActivity;
import com.nda.thi_lich_su_vao_10.R;

public class NoteDetail extends AppCompatActivity {
    Intent intent;
    Bundle bundle;
    TextView txt_titleNoteDetail;
    ImageView imgBack, img_cancel, img_add_update_note;
    EditText edt_noteTitle, edt_noteContent;

    String title, content;

    int noteId;
    String noteTitle, noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        mapting();
        initiate();


    }

    private void initiate()
    {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        intent = getIntent();
        bundle = intent.getExtras();

        if (bundle.containsKey("AddNote"))
        {
            addNote();

        }

        if (bundle.containsKey("UpdateNOte"))
        {
            updateNote();

        }
    }

    private void updateNote() {
        txt_titleNoteDetail.setText("Cập Nhập Ghi Chú");

        noteId = intent.getIntExtra("updateId",0);
        noteTitle = intent.getStringExtra("updateTitle");
        noteContent = intent.getStringExtra("updateContent");

        edt_noteTitle.setText(noteTitle);
        edt_noteContent.setText(noteContent);

        img_add_update_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = edt_noteTitle.getText().toString().trim();
                content = edt_noteContent.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty())
                {
                    edt_noteTitle.setError("Không Được Trống !");
                    edt_noteContent.setError("Không Được Trống !");
                }

                else
                {
                    //Toast.makeText(NoteDetail.this, "id : " + noteId, Toast.LENGTH_SHORT).show();

                    MainActivity.database.QueryData("UPDATE note_table SET note_title = '" + title
                            + "' , note_Content = '" + content + "'  WHERE note_Id = '" + noteId + "' ");

                   startActivity(new Intent(NoteDetail.this,Note_System.class));
                }


            }
        });
    }

    private void addNote() {
        txt_titleNoteDetail.setText("Thêm Ghi Chú");

        img_add_update_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = edt_noteTitle.getText().toString().trim();
                content = edt_noteContent.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty())
                {
                    edt_noteTitle.setError("Không Được Trống !");
                    edt_noteContent.setError("Không Được Trống !");
                }

                else
                {
                    MainActivity.database.INSERT_NOTE(title, content);
                    startActivity(new Intent(NoteDetail.this,Note_System.class));
                }


            }
        });

    }

    private void mapting() {
        txt_titleNoteDetail = (TextView) findViewById(R.id.txt_titleNoteDetail);

        imgBack                  = (ImageView) findViewById(R.id.imgBack);
        img_cancel               = (ImageView) findViewById(R.id.img_cancel);
        img_add_update_note      = (ImageView) findViewById(R.id.img_add_update_note);


        edt_noteTitle     = (EditText) findViewById(R.id.edt_noteTitle);
        edt_noteContent   = (EditText) findViewById(R.id.edt_noteContent);

    }
}