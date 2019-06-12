package com.example.fieldandwaveexam;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import java.util.UUID;

public class QuestionCurserWraper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public QuestionCurserWraper(Cursor cursor) {
        super(cursor);
    }

    public Question getQuestion(){

        Question question = new Question();

        question.setId(getInt(getColumnIndex("id")));

        question.setAnswer(getString(getColumnIndex("answer")));

        question.setGozine1(getString(getColumnIndex("ans1")));
        question.setGozine2(getString(getColumnIndex("ans2")));
        question.setGozine3(getString(getColumnIndex("ans3")));
        question.setGozine4(getString(getColumnIndex("ans4")));
        question.setTitle(getString(getColumnIndex("title")));

        return question;
    }
}
