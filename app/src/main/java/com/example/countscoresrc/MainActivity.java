package com.example.countscoresrc;

import android.app.Dialog;
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

    private TextView txtScore;
    private TextView txtLastAction;
    private TextView txtTimer;
    private EditText timeMin;
    private EditText timeSec;

    private Button btnGoal;
    private Button btnBotRestart;
    private Button btnFoul;
    private Button btnReset;
    private Button undoBtn;
    private Button btnStartTimer;
    private Button btnPauseTimer;
    private Button btnResetTimer;



    private String restartTxt = "Last Action: RESTART";
    private String goalTxt = "Last Action: GOAL";
    private String foulTxt = "Last Action: FOUL";

    private boolean isTimerRunning = false;
    private long timeLeft;
    private CountDownTimer countDownTimer;

    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txtScore = findViewById(R.id.txtScore);
        txtLastAction = findViewById(R.id.txtLastAction);
        txtTimer = findViewById(R.id.txtTimer);

        timeMin = findViewById(R.id.timeMin);
        timeSec = findViewById(R.id.timeSec);

        btnGoal = findViewById(R.id.btnGoal);
        btnBotRestart = findViewById(R.id.btnRestart);
        btnFoul = findViewById(R.id.btnFoul);
        btnReset = findViewById(R.id.btnReset);
        undoBtn = findViewById(R.id.btnUndo);
        btnStartTimer = findViewById(R.id.btnStartTimer);
        btnPauseTimer = findViewById(R.id.btnPauseTimer);
        btnResetTimer = findViewById(R.id.btnResetTimer);


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
                Dialog  dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog);
                dialog.show();
                Button btnYes = dialog.findViewById(R.id.btnYes);
                Button btnNo = dialog.findViewById(R.id.btnNo);
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        score = 0;
                        txtScore.setText("");
                        txtLastAction.setText("");
                        dialog.dismiss();
                        resetTimer();
                    }
                });

            }
        });

        btnStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        btnPauseTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        btnResetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

    }

    private void startTimer() {
        if (!isTimerRunning) {
            String minutesStr = timeMin.getText().toString();
            String secondsStr = timeSec.getText().toString();

            int minutes = minutesStr.isEmpty() ? 0 : Integer.parseInt(minutesStr);
            int seconds = secondsStr.isEmpty() ? 0 : Integer.parseInt(secondsStr);

            if (minutes < 0 || seconds < 0 || seconds >= 60) {
                Toast.makeText(this, "Enter valid time", Toast.LENGTH_SHORT).show();
                return;
            }

            int totalSeconds = (minutes * 60) + seconds;
            if (totalSeconds <= 0) {
                Toast.makeText(this, "Enter a positive time", Toast.LENGTH_SHORT).show();
                return;
            }

            timeLeft = totalSeconds * 1000L;
            timeMin.setEnabled(false);
            timeSec.setEnabled(false);
            isTimerRunning = true;
            btnStartTimer.setEnabled(false);
            btnPauseTimer.setEnabled(true);
            btnResetTimer.setEnabled(true);

            countDownTimer = new CountDownTimer(timeLeft, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeft = millisUntilFinished;
                    updateTimerText();
                }

                @Override
                public void onFinish() {
                    txtTimer.setText("00:00");
                    isTimerRunning = false;
                    btnStartTimer.setEnabled(true);
                    btnPauseTimer.setEnabled(false);
                    btnResetTimer.setEnabled(true);
                    timeMin.setEnabled(true);
                    timeSec.setEnabled(true);
                }
            }.start();
        }
    }

    private void pauseTimer() {
        if (isTimerRunning) {
            countDownTimer.cancel();
            isTimerRunning = false;
            btnStartTimer.setEnabled(true);
            btnPauseTimer.setEnabled(false);
        }
    }

    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isTimerRunning = false;
        timeLeft = 0;
        txtTimer.setText("00:00");
        timeMin.setEnabled(true);
        timeSec.setEnabled(true);
        btnStartTimer.setEnabled(true);
        btnPauseTimer.setEnabled(false);
        btnResetTimer.setEnabled(false);
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;
        txtTimer.setText(String.format("%02d:%02d", minutes, seconds));
    }
}