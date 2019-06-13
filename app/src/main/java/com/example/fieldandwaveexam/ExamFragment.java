package com.example.fieldandwaveexam;


import android.animation.Animator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.support.v7.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFragment extends Fragment {

    RecyclerView recyclerView;
    MyAdapter adapter;
    List<Question> questionList;
    FloatingActionButton fab;
    SearchView searchView;
    Repository repository;
    TextView submit;

    public static final String TAG = "examfragment";

    public static ExamFragment newInstance() {

        Bundle args = new Bundle();

        ExamFragment fragment = new ExamFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ExamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository =  Repository.getInstance();
        questionList =repository.getQuestionList();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exam, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        fab = view.findViewById(R.id.float_button);
        submit = view.findViewById(R.id.text_submit);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        if (adapter == null)
            adapter = new MyAdapter(questionList);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(layoutManager.getItemCount() - 1);
                fab.hide();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (((LinearLayoutManager) layoutManager).findLastVisibleItemPosition() != layoutManager.getItemCount() - 1)
                    fab.show();
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment.newInstance("dsfsd", new BottomSheetFragment.CallbackToExamFragment() {
                    @Override
                    public void check() {
                        updateUI(questionList);

                        Log.i(TAG, "check: ");
                    }
                }).show(getFragmentManager(),"dsfsd");
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.search_menu);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fab.hide();
                Log.i(TAG, "onQueryTextSubmit: " + query);
                updateUI(repository.search(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                Log.i(TAG, "onViewDetachedFromWindow: ");
                fab.show();
                updateUI(questionList);
            }
        });

    
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void updateUI(List<Question> searchList) {
        adapter.setQuestionList(searchList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.search_menu) {
            //search fragment
        } else {
            View view = View.inflate(getActivity(), R.layout.dialog_search, null);
            final EditText number = view.findViewById(R.id.editText_number);


            new AlertDialog.Builder(getActivity())
                    .setView(view)
                    .setTitle("شماره سوالی که میخواهید را انتخاب کنید")
                    .setPositiveButton("برو به این سوال", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Integer.valueOf(number.getText().toString()) <= questionList.size())
                                recyclerView.scrollToPosition(Integer.valueOf(number.getText().toString()) - 1);
                            else
                                Toast.makeText(getActivity(), "آزمون شامل 200 سوال است! لطفا در این بازه انتخاب کنید", Toast.LENGTH_LONG).show();

                        }
                    })
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
        private List<Question> questionList;

        public MyAdapter(List<Question> questionList) {
            this.questionList = questionList;
        }
        public void setQuestionList(List<Question> list){
            questionList = list;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyHolder(LayoutInflater.from(getActivity()).inflate(R.layout.question_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
            myHolder.bind(questionList.get(i));
            if (Mypref.IsEnded(getActivity()))
                myHolder.checkAnswers(questionList.get(i));
        }

        @Override
        public int getItemCount() {
            return questionList.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView title;
            RadioGroup radioGroup;
            RadioButton gozine1;
            RadioButton gozine2;
            RadioButton gozine3;
            RadioButton gozine4;
            Button button;
            TextView textView;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                radioGroup = itemView.findViewById(R.id.radio_group);
                gozine1 = itemView.findViewById(R.id.gozine1);
                gozine2 = itemView.findViewById(R.id.gozine2);
                gozine3 = itemView.findViewById(R.id.gozine3);
                gozine4 = itemView.findViewById(R.id.gozine4);
                button = itemView.findViewById(R.id.materialButton);
                textView = itemView.findViewById(R.id.text_tashih);
            }

            public void bind(final Question question) {
                Log.i(TAG, "bind: ");
                title.setText(question.getTitle());
                gozine1.setText(question.getGozine1());
                gozine2.setText(question.getGozine2());
                gozine3.setText(question.getGozine3());
                gozine4.setText(question.getGozine4());


                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                      //  button.setVisibility(View.VISIBLE);
                        if (gozine1.isChecked()) {
                            question.setChoose("1");
                        } else if (gozine2.isChecked())
                            question.setChoose("2");
                        else if (gozine3.isChecked())
                            question.setChoose("3");
                        else if (gozine4.isChecked())
                            question.setChoose("4");
                        else
                            question.setChoose("0");
                    }
                });


            }
            public void checkAnswers(Question question){
                switch (question.getChoose()){
                    case "1":
                        gozine1.setChecked(true);
                        break;
                    case "2":
                        gozine2.setChecked(true);
                        break;
                    case "3":
                        gozine3.setChecked(true);
                        break;
                    case "4":
                        gozine4.setChecked(true);
                        break;
                    default:
                        break;
                }
                gozine1.setEnabled(false);
                gozine2.setEnabled(false);
                gozine3.setEnabled(false);
                gozine4.setEnabled(false);
                button.setVisibility(View.GONE);
                radioGroup.setEnabled(false);
                textView.setVisibility(View.VISIBLE);

                if (question.getChoose().equals(question.getAnswer())) {
                    //true answer
                    textView.setText("تبریک! گزینه ی درست را انتخاب کرده اید");
                    textView.setTextColor(getResources().getColor(R.color.colorTrue));
                } else {
                    textView.setText(String.format("متاسفانه گزینه ی %s صحیح است ", question.getAnswer()));
                    textView.setTextColor(getResources().getColor(R.color.colorFalse));
                }
            }
        }
    }
}
