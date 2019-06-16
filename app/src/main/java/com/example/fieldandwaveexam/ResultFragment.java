package com.example.fieldandwaveexam;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


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
    TextView result;
    TextView captur;
    Repository repository;
    CallBackResult callBackResult;


    interface CallBackResult {
        void goToResult();
        void addToolbar();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (callBackResult == null)
            callBackResult = (CallBackResult) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callBackResult = null;
    }

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
        password = view.findViewById(R.id.text_number);
        true_answer = view.findViewById(R.id.text_solved);
        false_answer = view.findViewById(R.id.text_unsolved);
        percent = view.findViewById(R.id.text_percent);
        all = view.findViewById(R.id.text_all);
        result = view.findViewById(R.id.button_result);
        captur = view.findViewById(R.id.button_capture);

        name.setText(Mypref.getName(getActivity()));
        password.setText(Mypref.getPass(getActivity()));

        true_answer.setText(repository.getTrueAnswer());
        false_answer.setText(repository.getFalseAnswer());
        percent.setText(Float.parseFloat(String.valueOf(repository.getPercent())) + " %");
        all.setText(repository.getAll());
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackResult.goToResult();
            }
        });
        captur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Log.i("captur", "onClick: ");
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.i("captur", "onClick: else");
                    storeBitmap(takeScreenshotForView(getActivity().getWindow().getDecorView().getRootView()), Environment.getExternalStorageDirectory().toString() + "/FieldAndWaveExamResult" + UUID.randomUUID() + ".png");
                    Toast.makeText(getActivity(), "درحال ذخیره سازی تصویر کار نامه در گالری!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                storeBitmap(takeScreenshotForView(getActivity().getWindow().getDecorView().getRootView()), Environment.getExternalStorageDirectory().toString() + "/FieldAndWaveExamResult" + UUID.randomUUID() + ".png");
                Toast.makeText(getActivity(), "درحال ذخیره سازی تصویر کارنامه در گالری!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "متاسفانه اجازه دسترسی داده نشد!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public Bitmap takeScreenshotForView(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY));
        view.layout((int) view.getX(), (int) view.getY(), (int) view.getX() + view.getMeasuredWidth(), (int) view.getY() + view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }

    public void storeBitmap(Bitmap bitmap, String filePath) {
        File imageFile = new File(filePath);
        imageFile.getParentFile().mkdirs();
        try {
            OutputStream fout = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
            fout.flush();
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestPermissionAndSave() {


    }

}
