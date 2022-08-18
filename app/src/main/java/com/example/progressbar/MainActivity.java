package com.example.progressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int max = 100;
    int count = 0 ;


    private SemiCircleDrawable progressBar;
    private TextView progress;
    private Button btnUp;
    private TextView maxScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progress = findViewById(R.id.progress);
        btnUp = findViewById(R.id.btnUp);
        maxScore = findViewById(R.id.maxScore);
        maxScore.setText("Max Point: "+max);
    }


    public void UpCount(View view) {
        if (count >= max) count = 0;
        else count = count + 1 ;

        progress.setText(count+"pt");
        progressBar.initProgress(count);
    }

    int convertPercent(int percent){
        return percent * 120/max;
    }

}
