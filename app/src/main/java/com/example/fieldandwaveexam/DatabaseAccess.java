package com.example.fieldandwaveexam;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    public static final String TAG = "dataAccess";
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    private List<Question> questionList = new ArrayList<>();
    private QuestionCurserWraper curserWraper;


    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public List<Question> getQuestions() {
        Cursor cursor = database.rawQuery("SELECT * FROM question", null);
        Log.i(TAG, "getQuestions: " + cursor.getColumnCount());
        curserWraper = new QuestionCurserWraper(cursor);
        try {
            if (curserWraper.getCount() == 0)
                return questionList;
            curserWraper.moveToFirst();
            while (!curserWraper.isAfterLast()) {
                questionList.add(curserWraper.getQuestion());
                curserWraper.moveToNext();
            }
        } finally {
            curserWraper.close();
            cursor.close();
        }
        return questionList;
    }


}
