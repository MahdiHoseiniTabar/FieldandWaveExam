package com.example.fieldandwaveexam;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoginFragment.Callback {

    DatabaseAccess databaseAccess;
    private FragmentManager fragmentManager;
    Repository repository = Repository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        databaseAccess = DatabaseAccess.getInstance(this);
        if (repository.getQuestionList() == null) {
            Log.i("exammm", "onCreate: ");
            databaseAccess.open();
            repository.setQuestionList(databaseAccess.getQuestions());
            databaseAccess.close();
        }

        fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.fragment_containar) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_containar, LoginFragment.newInstance())
                    .commit();
        }


    }

    @Override
    public void next() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_containar, ExamFragment.newInstance())
                .addToBackStack("ddsdf")
                .commit();
    }
}
