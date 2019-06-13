package com.example.fieldandwaveexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ShowDescriptionDialog extends DialogFragment {
    private Repository repository;
    private EditText addText;
    private Quize quize;

    public static ShowDescriptionDialog newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        ShowDescriptionDialog fragment = new ShowDescriptionDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_show, null);
        addText = view.findViewById(R.id.editText_number);
        repository = Repository.getInstance();
        quize = repository.getQuize(getArguments().getInt("id"));
        if (quize.getAnswer().isEmpty()) {
            addText.setMaxLines(10);
            addText.setLines(3);
        } else {
            addText.setText(quize.getAnswer());
        }

        final AlertDialog dialogg = new AlertDialog.Builder(getActivity())
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repository.writeAnswer(quize, addText.getText().toString());

                    }
                })
                .setView(view)
                .create();

    dialogg.setOnShowListener(new DialogInterface.OnShowListener() {
        @Override
        public void onShow(DialogInterface dialog) {
            dialogg.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
        }
    });
        return dialogg;

    }
}
