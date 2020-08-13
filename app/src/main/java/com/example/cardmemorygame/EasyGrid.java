package com.example.cardmemorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class EasyGrid extends AppCompatActivity {

    private ArrayList<Button> tiles = new ArrayList<Button>(); //Array that holds references to all the tiles
    private ArrayList<Integer> tileNumbers = new ArrayList<Integer>(); //Array that holds numbers for all tiles
    private int win = 0;
    private int lose = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_grid);
        setTileNumbers();
    }

    /**
    * Handles adding all tiles to an array to be passed to GenerateRandomGrid.
     * GenerateRandomGrid then handles setting all the tiles numbers randomly
     */
    private void setTileNumbers() {
        addTilesToArray();
        GenerateRandomGrid gen = new GenerateRandomGrid();
        tileNumbers = gen.setTilesRandomly(tiles);
    }

    /**
     * Adds all the tiles for the given grid into an ArrayList
     */
    private void addTilesToArray() {
        Button button;

        tiles.add((button = findViewById(R.id.card_1)));
        tiles.add((button = findViewById(R.id.card_2)));
        tiles.add((button = findViewById(R.id.card_3)));
        tiles.add((button = findViewById(R.id.card_4)));
        tiles.add((button = findViewById(R.id.card_5)));
        tiles.add((button = findViewById(R.id.card_6)));
        tiles.add((button = findViewById(R.id.card_7)));
        tiles.add((button = findViewById(R.id.card_8)));
        tiles.add((button = findViewById(R.id.card_9)));
        tiles.add((button = findViewById(R.id.card_10)));
        tiles.add((button = findViewById(R.id.card_11)));
        tiles.add((button = findViewById(R.id.card_12)));
        tiles.add((button = findViewById(R.id.card_13)));
        tiles.add((button = findViewById(R.id.card_14)));
        tiles.add((button = findViewById(R.id.card_15)));
        tiles.add((button = findViewById(R.id.card_16)));
    }

    private void Win (){//call this function on card click
        int count = 0;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getVisibility() == View.INVISIBLE){
                count++;
            }
            if (count == 16){
                Toast toast = Toast.makeText(this, "You WIN! score = Win " + win + " " + " Lose " + lose,
                        Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }


}
