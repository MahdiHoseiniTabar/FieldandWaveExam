package com.example.fieldandwaveexam;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository repository;
    private List<Question> questionList ;

    public static Repository getInstance(){
        if (repository == null)
            repository = new Repository();
        return repository;
    }

    private Repository() {

    }


    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
