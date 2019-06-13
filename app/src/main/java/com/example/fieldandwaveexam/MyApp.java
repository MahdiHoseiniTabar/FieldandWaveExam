package com.example.fieldandwaveexam;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

public class MyApp extends Application {
    private static MyApp app;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        MyDevOpenHelper openHelper = new MyDevOpenHelper(this,"addQuestion.db",null);
        Database database = openHelper.getWritableDb();

        daoSession = new DaoMaster(database).newSession();
        app = this;
    }

    public static MyApp getApp() {
        return app;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
