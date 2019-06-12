package com.example.fieldandwaveexam;

import java.util.UUID;

public class Question {
    private int id;

    private String title;
    private String gozine1;
    private String gozine2;
    private String gozine3;
    private String gozine4;
    private String answer;
    private String choose = "0";
    private boolean isChecked = false;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGozine1() {
        return gozine1;
    }

    public void setGozine1(String gozine1) {
        this.gozine1 = gozine1;
    }

    public String getGozine2() {
        return gozine2;
    }

    public void setGozine2(String gozine2) {
        this.gozine2 = gozine2;
    }

    public String getGozine3() {
        return gozine3;
    }

    public void setGozine3(String gozine3) {
        this.gozine3 = gozine3;
    }

    public String getGozine4() {
        return gozine4;
    }

    public void setGozine4(String gozine4) {
        this.gozine4 = gozine4;
    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getChoose() {
        return choose;
    }

    public void setChoose(String choose) {
        this.choose = choose;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
