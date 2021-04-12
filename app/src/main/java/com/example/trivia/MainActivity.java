package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionCounterTextview, corectAnswerTextview, questionTextview;
    private Button answerTrueButton, answerFalseButton;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answerTrueButton = findViewById(R.id.btn_true);
        answerFalseButton = findViewById(R.id.btn_false);
        questionCounterTextview = findViewById(R.id.questions_counter_text);
        corectAnswerTextview = findViewById(R.id.corect_answer_counter_text);
        questionTextview = findViewById(R.id.question_textview);

        answerTrueButton.setOnClickListener(this);
        answerFalseButton.setOnClickListener(this);

        List<Question> questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                questionTextview.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                Log.d("iside", "processFinished: " + questionArrayList);
            }
        });
//        Log.d("main", "onCreate: " + questionList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_true:
                break;
            case R.id.btn_false:
                break;
        }
    }
}