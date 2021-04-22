package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionCounterTextview, correctAnswerTextview, questionTextview;
    private Button answerTrueButton, answerFalseButton;
    private int dataTransition = 0;
    private int currentQuestionIndex = 0;
    private int correctAnswersCounter = 0;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answerTrueButton = findViewById(R.id.btn_true);
        answerFalseButton = findViewById(R.id.btn_false);
        questionCounterTextview = findViewById(R.id.questions_counter_text);
        correctAnswerTextview = findViewById(R.id.corect_answer_counter_text);
        questionTextview = findViewById(R.id.question_textview);

        answerTrueButton.setOnClickListener(this);
        answerFalseButton.setOnClickListener(this);

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                questionTextview.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                questionCounterTextview.setText(currentQuestionIndex + " / " + (questionArrayList.size() - 1));
                correctAnswerTextview.setText("Правильных ответов: " + correctAnswersCounter);
                Log.d("iside", "processFinished: " + questionArrayList);
            }
        });
//        Log.d("main", "onCreate: " + questionList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_true:
                checkAnswer(true);
                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                delayUpdateQuestion();
                break;
            case R.id.btn_false:
                checkAnswer(false);
                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                delayUpdateQuestion();
                break;
        }
    }

    private void checkAnswer(boolean userChooseCorrect) {
        boolean answerIsTrue = questionList.get(currentQuestionIndex).isAnswerTrue();
        if (userChooseCorrect == answerIsTrue) {
            correctAnswersCounter++;
            fadeView();
            correctAnswerTextview.setText(("Правильных ответов: " + correctAnswersCounter));
        } else {
            shakeAnimation();
            correctAnswerTextview.setText(("Правильных ответов: " + correctAnswersCounter));
        }
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        fadeInTxtQuestion();
        questionTextview.setText(question);
        questionCounterTextview.setText(currentQuestionIndex + " / " + (questionList.size() - 1));
        Log.d("upd", "updateQuestion: " + currentQuestionIndex);

        dataTransition = correctAnswersCounter;
        if (currentQuestionIndex == 0) endOfQuiz();
    }

//==================================== Animations ==================================================

    private void fadeView() {
        Animation alpha = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_animation);
        CardView cardView = findViewById(R.id.question_cardview);
        cardView.setAnimation(alpha);

        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(getColor(R.color.background_color_card_view));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        CardView cardView = findViewById(R.id.question_cardview);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(getColor(R.color.background_color_card_view));

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void fadeInTxtQuestion() {
        Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in_txt);
        TextView txt = findViewById(R.id.question_textview);
        txt.setAnimation(fadeIn);

    }

//============================ Задержка перед обновлением вопроса для анимации =====================


    private void delayUpdateQuestion() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateQuestion();
            }
        }, 500);
    }

    private void endOfQuiz() {
        String dataOut = String.valueOf(correctAnswersCounter);
        Intent intent = new Intent(MainActivity.this, EndScreen.class);
        intent.putExtra("correctAnswers", dataOut);
        startActivity(intent);
    }


}

