package com.example.fieldandwaveexam;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    TextView textView_exam;
    TextView textView_uni;
    TextView textView_teacher;
    EditText name;
    EditText password;
    Button login;
    Typeface font;
    Callback callback;

    interface Callback{
        void next();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (callback == null){
            callback = (Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public static LoginFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        textView_exam = view.findViewById(R.id.course_name);
        textView_teacher = view.findViewById(R.id.teacher);
        textView_uni = view.findViewById(R.id.course_title);
        name = view.findViewById(R.id.editText_name);
        password = view.findViewById(R.id.editText_password);
        login = view.findViewById(R.id.button_signin);
        font = Typeface.createFromAsset(getActivity().getAssets(),"font/font.ttf");

        textView_uni.setTypeface(font);
        textView_teacher.setTypeface(font);
        textView_exam.setTypeface(font);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity call next fragment
                 callback.next();

                Mypref.setName(getActivity(),name.getText().toString());
                Mypref.setPass(getActivity(),password.getText().toString());
                Mypref.setIsended(getActivity(),false);

                Toast.makeText(getActivity(), name.getText().toString() + " خوش آمدید!", Toast.LENGTH_SHORT).show();

            }
        });



        return view;
    }

}
