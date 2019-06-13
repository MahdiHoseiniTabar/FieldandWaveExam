package com.example.fieldandwaveexam;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Repository {
    private static Repository repository;
    private List<Question> questionList;
    private List<Question> searchItems = new ArrayList<>();
    private int true_question;
    private int false_question;
    private QuizeDao quizeDao;


    public static Repository getInstance() {
        if (repository == null)
            repository = new Repository();
        return repository;
    }

    private Repository() {
        quizeDao = MyApp.getApp().getDaoSession().getQuizeDao();
    }

    public List<Question> search(String query) {
        if (searchItems.size() != 0)
            searchItems.clear();
        for (int i = 0; i < questionList.size(); i++) {
            if (questionList.get(i).getTitle().contains(query))
                searchItems.add(questionList.get(i));
        }
        return searchItems;
    }


    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public String getTrueAnswer() {
        true_question = 0;
        for (int i = 0; i < questionList.size(); i++) {
            if (!questionList.get(i).getChoose().equals("0")) {
                if (questionList.get(i).getAnswer().equals(questionList.get(i).getChoose()))
                    true_question++;
            }
        }
        return String.valueOf(true_question);
    }

    public String getFalseAnswer() {
        false_question = 0;
        for (int i = 0; i < questionList.size(); i++) {
            if (!questionList.get(i).getChoose().equals("0")) {
                if (!questionList.get(i).getAnswer().equals(questionList.get(i).getChoose()))
                    false_question++;
            }
        }
        return String.valueOf(false_question);

    }

    public float getPercent() {
        return ((float) true_question / (float) getQuestionList().size()) * 100;
    }

    public String getAll() {
        return String.valueOf(getQuestionList().size());
    }


    public void insertQuestion(){
        for (int i = 0; i <questionList.size() ; i++) {
            Quize quize = new Quize(new Random().nextLong(),
                    String.valueOf(questionList.get(i).getId()),
                    questionList.get(i).getTitle(),"");
            quizeDao.insert(quize);
        }
    }

    public Quize getQuize(int id) {
        Log.i("quize", "getQuize: " + id);
        return quizeDao.queryBuilder().where(QuizeDao.Properties.Id.eq(String.valueOf(id))).unique();
    }

    public void writeAnswer(Quize quize,String toString) {

        quize.setAnswer(toString);

        quizeDao.update(quize);
    }
}
