package com.example.appblocker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class LockScreen extends AppCompatActivity {
    private TextView[] questionTextViews;
    private EditText[] answerEditTexts;
    private String[] questions;
    private String[] answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        // Generate 10 random questions and answers
        questions = new String[]{"Question 1", "Question 2", "Question 3", "Question 4", "Question 5", "Question 6", "Question 7", "Question 8", "Question 9", "Question 10"};
        answers = new String[]{"Answer 1", "Answer 2", "Answer 3", "Answer 4", "Answer 5", "Answer 6", "Answer 7", "Answer 8", "Answer 9", "Answer 10"};
//        shuffleQuestionsAndAnswers();

        // Set up the UI elements
        @SuppressLint("WrongViewCast") LinearLayout questionLayout = findViewById(R.id.question_text_view);
        questionTextViews = new TextView[10];
        answerEditTexts = new EditText[10];
        for (int i = 0; i < 10; i++) {
            questionTextViews[i] = new TextView(this);
            questionTextViews[i].setText(questions[i]);
            questionLayout.addView(questionTextViews[i]);

            answerEditTexts[i] = new EditText(this);
            questionLayout.addView(answerEditTexts[i]);
        }

        Button submitButton = findViewById(R.id.check_answer_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allAnswersCorrect = true;
                for (int i = 0; i < 10; i++) {
                    if (!answerEditTexts[i].getText().toString().equals(answers[i])) {
                        allAnswersCorrect = false;
                        break;
                    }
                }
                if (allAnswersCorrect) {
                    // Launch the locked app using an Intent
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect answers, please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}