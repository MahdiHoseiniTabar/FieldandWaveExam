package com.example.fieldandwaveexam;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoginFragment.Callback, ExamFragment.Call_back, BottomSheetFragment.CallBack, ResultFragment.CallBackResult {

    private Toolbar toolbar;
    FrameLayout frameLayout;
    DatabaseAccess databaseAccess;
    private FragmentManager fragmentManager;
    Repository repository = Repository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.tool_bar);
        frameLayout = findViewById(R.id.frame);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);
        databaseAccess = DatabaseAccess.getInstance(this);


        fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.fragment_containar) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_containar, LoginFragment.newInstance())
                    .commit();
        }

        new DataBaseAsync().execute();

    }

    class DataBaseAsync extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            if (repository.getQuestionList() == null) {
                Log.i("exammm", "onCreate: ");
                databaseAccess.open();
                repository.setQuestionList(databaseAccess.getQuestions());
                if (Mypref.IsFlage(MainActivity.this)) {
                    repository.insertQuestion();
                    Mypref.setFlag(MainActivity.this, false);
                }
                databaseAccess.close();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            frameLayout.setVisibility(View.GONE);


        }
    }

    @Override
    public void next() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_containar, ExamFragment.newInstance())
                .addToBackStack("ddsdf")
                .commit();
    }

    @Override
    public void removeToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public void goToResultPage() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_containar, ResultFragment.newInstance())
                .addToBackStack("ddsdf")
                .commit();
    }

    @Override
    public void goToResult() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_containar, ExamFragment.newInstance())
                .addToBackStack("ddsdf")
                .commit();
    }


    @Override
    public void addToolbar() {
        toolbar.setVisibility(View.VISIBLE);
    }
}
