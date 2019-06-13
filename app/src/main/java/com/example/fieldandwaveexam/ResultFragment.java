package com.example.fieldandwaveexam;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {
    TextView name;
    TextView password;
    TextView true_answer;
    TextView false_answer;
    TextView percent;
    TextView all;
    Repository repository;

    public static ResultFragment newInstance() {

        Bundle args = new Bundle();

        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = Repository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        name = view.findViewById(R.id.text_name);
        password  = view.findViewById(R.id.text_number);
        true_answer = view.findViewById(R.id.text_solved);
        false_answer = view.findViewById(R.id.text_unsolved);
        percent = view.findViewById(R.id.text_percent);
        all = view.findViewById(R.id.text_all);

        name.setText(Mypref.getName(getActivity()));
        password.setText(Mypref.getPass(getActivity()));

        true_answer.setText(repository.getTrueAnswer());
        false_answer.setText(repository.getFalseAnswer());
        percent.setText(Float.parseFloat(String.valueOf(repository.getPercent()) )+ " %");
        all.setText(repository.getAll());

        return view;
    }

}
