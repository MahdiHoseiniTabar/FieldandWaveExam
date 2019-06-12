package com.example.fieldandwaveexam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {

    public DatabaseOpenHelper(Context context) {
        super(context, "Question.db", null, 1);
    }
}
