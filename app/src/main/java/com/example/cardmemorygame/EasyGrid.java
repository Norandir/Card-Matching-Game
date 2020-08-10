package com.example.cardmemorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class EasyGrid extends AppCompatActivity {

    private ArrayList<Button> tiles = new ArrayList<Button>(); //Array that holds references to all the tiles
    private ArrayList<Integer> tileNumbers = new ArrayList<Integer>(); //Array that holds numbers for all tiles
    private int tilesClicked = 0;

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

    /**
     * onClick handler for buttons. Makes the button invisible (revealing the image underneath)
     * if image was visible & vice versa. Also re-hides buttons if more than two are revealed.
     * @param view
     */
    public void CycleVisibility(View view) {

        int isVisible = view.getVisibility();

        tilesClicked++;

        if (tilesClicked <= 2) {

            if (isVisible == View.VISIBLE) {
                view.setVisibility(View.INVISIBLE);
            }
            else if (isVisible == View.INVISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        } else {

            for (int i = 0; i < tiles.size(); i++) {
                tiles.get(i).setVisibility(View.VISIBLE);
            }

            tilesClicked = 0;
        }
    }
}