package com.example.countscoresrc;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private TextView txtScore;
    private TextView txtLastAction;
    private TextView txtTimer;
    private Button btnGoal;
    private Button btnBotRestart;
    private Button btnFoul;
    private Button btnReset;
    private Button undoBtn;


    private String restartTxt = "Last Action: RESTART";
    private String goalTxt = "Last Action: GOAL";
    private String foulTxt = "Last Action: FOUL";

    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txtScore = findViewById(R.id.txtScore);
        txtLastAction = findViewById(R.id.txtLastAction);
        txtTimer = findViewById(R.id.txtTimer);

        btnGoal = findViewById(R.id.btnGoal);
        btnBotRestart = findViewById(R.id.btnRestart);
        btnFoul = findViewById(R.id.btnFoul);
        btnReset = findViewById(R.id.btnReset);
        undoBtn = findViewById(R.id.btnUndo);

        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastAction = txtLastAction.getText().toString();

                int goal = lastAction.compareTo(goalTxt);
                int restart = lastAction.compareTo(restartTxt);
                int foul = lastAction.compareTo(foulTxt);

                if (goal == 0){
                    score -= 300;
                    txtScore.setText(score + " ");
                }
                if (restart == 0){
                    score += 100;
                    txtScore.setText(score + " ");
                }
                if (foul == 0){
                    score += 150;
                    txtScore.setText(score + " ");
                }
            }
        });

        btnGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score += 300;
                txtScore.setText(score + " ");
                txtLastAction.setText(goalTxt);

            }
        });

        btnBotRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score -= 100;
                txtScore.setText(score + " ");
                txtLastAction.setText(restartTxt);

            }
        });

        btnFoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score -= 150;
                txtScore.setText(score + " ");
                txtLastAction.setText(foulTxt);

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                txtScore.setText("");
                txtLastAction.setText("");
                txtTimer.setText("");

            }
        });

    }
    private void startTimer(TextView timerText) {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) millisUntilFinished / 1000;
                timerText.setText("Time: " + secondsRemaining + "s");
            }

            @Override
            public void onFinish() {
                timerText.setText("Done!");
                isTimerRunning = false;
            }
        }.start();
        isTimerRunning = true;
    }
}