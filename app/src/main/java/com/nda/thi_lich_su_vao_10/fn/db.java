package com.nda.thi_lich_su_vao_10.fn;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class db extends SQLiteOpenHelper {
    public static String  dbName = "TracNghiemLichSuVao10.sqlite";

    /*
        Note Function
     */
    public static String note_table = "note_table";
    public static String column1_noteId         = "note_Id";
    public static String column2_noteTitle      = "note_title";
    public static String column3_noteContent    = "note_Content";
    /*
        (END) Note Function
     */

    /*
        User exam custom
     */

    // User create their Topic Exam.
    public static String userDe_table = "userDe_table";
    public static String column1_userDe_id          = "DeId";
    public static String column2_userDe_numberDe    = "DeNumber";
    public static String column3_userDe_titleDe     = "DeTitle";
    public static String column4_userDe_nguoiTao    = "NguoiTaoDe";

    // User create Question of specific topic exam.
    public static String detailDe_table = "detailDe_table";
    public static String column1_detailDe_id                = "detailDe_id";
    public static String column2_foreignKey_userDe_id       = "DeId"; // id from userDe_table => Foreign Key;
    public static String column3_detailDe_questionNumber    = "detailDe_questionNumber";
    public static String column4_detailDe_question          = "detailDe_question";
    public static String column5_detailDe_ansA              = "detailDe_ansA";
    public static String column6_detailDe_ansB              = "detailDe_ansB";
    public static String column7_detailDe_ansC              = "detailDe_ansC";
    public static String column8_detailDe_ansD              = "detailDe_ansD";
    public static String column9_detailDe_correctAns        = "detailDe_correctAns";


    /*
        (END) User exam custom
     */

//    /*
//        Practice
//     */
//    public static String practiceTopic_table = "practiceTopic_table";
//    public static String column1_practiceTopic_number = "practiceTopic_number";
//    public static String column2_practiceTopic_title = "practiceTopic_title";
//
//    public static String practiceTopicDetail_table = "practiceTopicDetail_table";
//    public static String column1_foreignKey_practiceTopicDetail_number  = "practiceTopic_number";
//    public static String column2_practiceTopicDetail_questionNumber     = "practiceDetail_questionNumber";
//    public static String column3_practiceTopicDetail_question           = "practiceDetail_question";
//    public static String column4_practiceTopicDetail_ansA               = "practiceDetail_ansA";
//    public static String column5_practiceTopicDetail_ansB               = "practiceDetail_ansB";
//    public static String column6_practiceTopicDetail_ansC               = "practiceDetail_ansC";
//    public static String column7_practiceTopicDetail_ansD               = "practiceDetail_ansD";
//    public static String column8_practiceTopicDetail_correctAns         = "practiceDetail_correctAns";
//
//
//    /*
//        (END) Practice
//     */

    public db(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void QueryData(String sql)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
        return;
    }
    public Cursor GetData(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    public void INSERT_NOTE(String noteTitle , String noteContent)
    {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO note_table VALUES(null,?,?)";// (null = vị trí đầu = 0 ; 1 ; 2;)
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, noteTitle);
        statement.bindString(2, noteContent);

        statement.executeInsert();
    }
}
