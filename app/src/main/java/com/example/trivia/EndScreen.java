package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndScreen extends AppCompatActivity implements View.OnClickListener {

    private TextView countCorrectAnswers;
    private Button btnEndQuiz;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        countCorrectAnswers = findViewById(R.id.count_correct_answers);
        btnEndQuiz = findViewById(R.id.btn_end);

        data = getIntent().getStringExtra("correctAnswers");
        countCorrectAnswers.setText(data);

        btnEndQuiz.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_end:
                finish();
                break;
        }
    }

}