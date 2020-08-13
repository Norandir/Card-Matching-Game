package com.example.cardmemorygame;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    public void launchEasyGrid(View view) {
        Intent intent = new Intent(this, EasyGrid.class);
        startActivity(intent);
    }


    class game extends TimerTask{
        public void run() {
            for (int i = 0; i < tiles.size(); i++) {
                tiles.get(i).setVisibility(View.VISIBLE);
            }
            timer.cancel(); //Terminate the timer thread
        }
    }



   


}