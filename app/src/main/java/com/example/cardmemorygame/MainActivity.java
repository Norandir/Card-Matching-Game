package com.example.cardmemorygame;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    Timer timer;

    public void hint (){

        timer = new Timer();
        timer.schedule(new game(), 5000); //after 5 seconds timer will call method to start game
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