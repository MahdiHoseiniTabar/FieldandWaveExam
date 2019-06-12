package com.example.fieldandwaveexam;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository repository;
    private List<Question> questionList ;
    private List<Question> searchItems = new ArrayList<>();


    public static Repository getInstance(){
        if (repository == null)
            repository = new Repository();
        return repository;
    }

    private Repository() {

    }
    public List<Question> search(String query){
       if (searchItems.size()!=0)
           searchItems.clear();
        for (int i = 0; i <questionList.size() ; i++) {
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
}
