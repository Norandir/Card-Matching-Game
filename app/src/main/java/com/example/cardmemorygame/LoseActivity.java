package com.example.cardmemorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LoseActivity extends AppCompatActivity {

    private int win = 0;
    private int lose = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);
        Intent intent = getIntent();
        win = intent.getIntExtra("Wins", 0);
        lose = intent.getIntExtra("Losses", 0);
    }

    public void launchMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Wins", win);
        intent.putExtra("Losses", lose);
        startActivity(intent);
    }

}