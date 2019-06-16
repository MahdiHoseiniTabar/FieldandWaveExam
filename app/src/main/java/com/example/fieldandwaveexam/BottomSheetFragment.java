package com.example.fieldandwaveexam;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    public static final String TITLE = "title";
    private static CallbackToExamFragment callbackToExamFragment;
    Button ok;
    Button cancel;
    View view;
    CallBack callBack;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (callBack == null)
            callBack = (CallBack) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callBack = null;
    }

    interface CallBack{
        void goToResultPage();
    }
    interface CallbackToExamFragment{
        void check();
    }
    public static BottomSheetFragment newInstance(CallbackToExamFragment callbackToExamFragment) {
        BottomSheetFragment.callbackToExamFragment = callbackToExamFragment;

        Bundle args = new Bundle();

        BottomSheetFragment fragment = new BottomSheetFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet, null);
        ok = view.findViewById(R.id.bottom_ok);
        cancel = view.findViewById(R.id.bottom_cancel);



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.goToResultPage();
                Mypref.setIsended(getActivity(),true);
                new CountDownTimer(500,500){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        callbackToExamFragment.check();
                    }
                };



                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);

    }



}
