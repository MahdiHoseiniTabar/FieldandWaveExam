package com.example.fieldandwaveexam;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class Quize {

    @Id
    private Long uuid;

    private String id;
    private String title;
    private String answer;
    @Generated(hash = 1184428987)
    public Quize(Long uuid, String id, String title, String answer) {
        this.uuid = uuid;
        this.id = id;
        this.title = title;
        this.answer = answer;
    }
    @Generated(hash = 1832116361)
    public Quize() {
    }
    public Long getUuid() {
        return this.uuid;
    }
    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAnswer() {
        return this.answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }


}
