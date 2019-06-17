package com.example.fieldandwaveexam;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
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
    TextView ok;
    TextView cancel;
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

        void setUpMask();

        void clearMask();
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

            ok.setEnabled(true);
            cancel.setEnabled(true);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                new ResultAsync().execute();

                callBack.setUpMask();
                ok.setEnabled(false);
                cancel.setEnabled(false);

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

    class ResultAsync extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            Mypref.setIsended(getActivity(),true);
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
            callBack.goToResultPage();
            callBack.clearMask();
            callbackToExamFragment.check();
            getDialog().dismiss();
        }
    }


}
