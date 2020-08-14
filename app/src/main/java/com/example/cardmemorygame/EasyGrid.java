package com.example.cardmemorygame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class EasyGrid extends AppCompatActivity {

    private ArrayList<Button> tiles = new ArrayList<Button>(); //Array that holds references to all the tiles
    private ArrayList<Integer> tileNumbers = new ArrayList<Integer>(); //Array that holds numbers for all tiles

    private int win = 0;
    private int lose = 0;

    private ArrayList<Integer> pairNumbersDone = new ArrayList<Integer>();
    private int tilesClicked = 0;
    private int strikes = 0;
    private int tileNumberToMatch;
    private int firstClickedIndex = -1;
    private int secondIndexToClick = -1;
    private View incorrectButton1;
    private View incorrectButton2;
    private boolean matchFound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_grid);
        setTileNumbers();
        assignImages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Hint();
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
                win++;
                Toast toast = Toast.makeText(this, "You WIN! score = Win " + win + " " + " Lose " + lose,
                        Toast.LENGTH_LONG);
                toast.show();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }


    private void Hint(){
        Thread thread = new Thread() {
            public void run() {
                for (int i = 0; i < tiles.size(); i++) {
                    tiles.get(i).setVisibility(View.INVISIBLE);
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int k = 0; k < tiles.size(); k++) {
                    final int j = k;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tiles.get(j).setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        };
        thread.start();
    }

    /**
     * Moves images onto their corresponding tile numbers.
     */
    private void assignImages() {
        // Variables to track the tile pair being found and the indices of the pair's locations.
        int pairNumber;
        int number1Index = 0;
        int number2Index = 0;
        // Objects to change the constraints of the images.
        ConstraintLayout constraintLayout = findViewById(R.id.parent_layout);
        ConstraintSet constraintSet = new ConstraintSet();
        // Loops through the tile numbers array, first finding the tile number at the current index.
        for (int i = 0; i < tileNumbers.size(); i++) {
            boolean pairDone = false;
            pairNumber = tileNumbers.get(i);
            Log.d("tileNumbers for loop", "pair number " + pairNumber + " found at index " + i);

            for (int k = 0; k < pairNumbersDone.size(); k++) {
                if (pairNumber == pairNumbersDone.get(k)){
                    Log.d("pairNumbersDone for", "Pair has already been done. skipping...");
                    pairDone = true;
                }
            }

            if (!pairDone) {
                Log.d("pairDone if", "Pair has not been completed yet, proceeding...");
                number1Index = i;
                Log.d("assignImages", "tile number " + pairNumber + " found at index " + number1Index + ". Searching for counterpart...");
                // Then loops backwards through the tile numbers array to find the tile with the same tile number (that is not at the same index)
                for (int j = tileNumbers.size() - 1; j >= 0; j--) {
                    if (pairNumber == tileNumbers.get(j) && j != i) {
                        number2Index = j;
                        Log.d("assignImages", "counterpart with tile number " + pairNumber + " found at index " + number2Index + ". Indices for this pair are " + number1Index + ", " + number2Index);
                        break;
                    }
                }
                // A metric butt ton of switch code to re-assign images (via their constraints) to their corresponding pair #'s, wherever the pair gets randomly assigned
                switch (pairNumber) {
                    case 1:
                        Log.d("pairNumber Switch", "pairNumber 1");
                        switch (number1Index) {
                            case 0:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 1 at first index " + number1Index);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 2 at first index " + number1Index);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 3 at first index " + number1Index);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 4 at first index " + number1Index);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 5 at first index " + number1Index);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 6 at first index " + number1Index);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 7 at first index " + number1Index);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 8 at first index " + number1Index);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 9 at first index " + number1Index);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 10 at first index " + number1Index);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 11 at first index " + number1Index);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 12 at first index " + number1Index);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 13 at first index " + number1Index);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 14 at first index " + number1Index);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 15 at first index " + number1Index);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_1, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                Log.d("number1Index Switch", "Assigning image1_1 to card 16 at first index " + number1Index);
                                break;
                        }

                        switch (number2Index) {
                            case 0:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 1 at second index " + number2Index);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 2 at second index " + number2Index);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 3 at second index " + number2Index);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 4 at second index " + number2Index);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 5 at second index " + number2Index);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 6 at second index " + number2Index);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 7 at second index " + number2Index);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 8 at second index " + number2Index);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 9 at second index " + number2Index);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 10 at second index " + number2Index);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 11 at second index " + number2Index);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 12 at second index " + number2Index);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 13 at second index " + number2Index);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 14 at second index " + number2Index);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 15 at second index " + number2Index);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg1_2, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                Log.d("number2Index Switch", "Assigning image1_2 to card 16 at second index " + number2Index);
                                break;
                        }
                        break;
                    case 2:
                        Log.d("pairNumber Switch", "pairNumber 2");
                        switch (number1Index) {
                            case 0:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_1, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                break;
                        }

                        switch (number2Index) {
                            case 0:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg2_2, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                break;
                        }
                        break;
                    case 3:
                        Log.d("pairNumber Switch", "pairNumber 3");
                        switch (number1Index) {
                            case 0:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_1, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                break;
                        }

                        switch (number2Index) {
                            case 0:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg3_2, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                break;
                        }
                        break;
                    case 4:
                        Log.d("pairNumber Switch", "pairNumber 4");
                        switch (number1Index) {
                            case 0:
                                constraintSet.clear(R.id.cardimg4_1);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_1, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                break;
                        }

                        switch (number2Index) {
                            case 0:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg4_2, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                break;
                        }
                        break;
                    case 5:
                        Log.d("pairNumber Switch", "pairNumber 5");
                        switch (number1Index) {
                            case 0:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_1, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                break;
                        }

                        switch (number2Index) {
                            case 0:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg5_2, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                break;
                        }
                        break;
                    case 6:
                        Log.d("pairNumber Switch", "pairNumber 6");
                        switch (number1Index) {
                            case 0:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_1, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                break;
                        }

                        switch (number2Index) {
                            case 0:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg6_2, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                break;
                        }
                        break;
                    case 7:
                        Log.d("pairNumber Switch", "pairNumber 7");
                        switch (number1Index) {
                            case 0:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_1, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                break;
                        }

                        switch (number2Index) {
                            case 0:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg7_2, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                break;
                        }
                        break;
                    case 8:
                        Log.d("pairNumber Switch", "pairNumber 8");
                        switch (number1Index){
                            case 0:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_1, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                break;
                        }

                        switch (number2Index){
                            case 0:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_1, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_1, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_1, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_1, ConstraintSet.LEFT, 8);
                                break;
                            case 1:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_2, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_2, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_2, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_2, ConstraintSet.LEFT, 8);
                                break;
                            case 2:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_3, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_3, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_3, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_3, ConstraintSet.LEFT, 8);
                                break;
                            case 3:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_4, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_4, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_4, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_4, ConstraintSet.LEFT, 8);
                                break;
                            case 4:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_5, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_5, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_5, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_5, ConstraintSet.LEFT, 8);
                                break;
                            case 5:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_6, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_6, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_6, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_6, ConstraintSet.LEFT, 8);
                                break;
                            case 6:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_7, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_7, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_7, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_7, ConstraintSet.LEFT, 8);
                                break;
                            case 7:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_8, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_8, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_8, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_8, ConstraintSet.LEFT, 8);
                                break;
                            case 8:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_9, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_9, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_9, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_9, ConstraintSet.LEFT, 8);
                                break;
                            case 9:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_10, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_10, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_10, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_10, ConstraintSet.LEFT, 8);
                                break;
                            case 10:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_11, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_11, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_11, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_11, ConstraintSet.LEFT, 8);
                                break;
                            case 11:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_12, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_12, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_12, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_12, ConstraintSet.LEFT, 8);
                                break;
                            case 12:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_13, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_13, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_13, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_13, ConstraintSet.LEFT, 8);
                                break;
                            case 13:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_14, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_14, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_14, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_14, ConstraintSet.LEFT, 8);
                                break;
                            case 14:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_15, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_15, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_15, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_15, ConstraintSet.LEFT, 8);
                                break;
                            case 15:
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.RIGHT, R.id.card_16, ConstraintSet.RIGHT, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.TOP, R.id.card_16, ConstraintSet.TOP, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.BOTTOM, R.id.card_16, ConstraintSet.BOTTOM, 8);
                                constraintSet.connect(R.id.cardimg8_2, ConstraintSet.LEFT, R.id.card_16, ConstraintSet.LEFT, 8);
                                break;
                        }
                }

                constraintSet.applyTo(constraintLayout);
                pairNumbersDone.add(pairNumber);
                Log.d("pairDone if", "Added " + pairNumber + " to list of pair numbers to avoid");
            }
            pairDone = false;
        }
    }

    /**
     * onClick handler for buttons. Makes the button invisible (revealing the image underneath)
     * if image was visible & vice versa. Also re-hides buttons if more than two are revealed.
     * @param view
     */
    public void CycleVisibility(View view) {

        int isVisible = view.getVisibility();

        if (isVisible == view.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }



        Win();

        if (firstClickedIndex == -1) {
            Log.d("Cyclevisibility for", "if entered");

            if (incorrectButton1 != null) {
                incorrectButton1.setVisibility(View.VISIBLE);
                incorrectButton2.setVisibility(View.VISIBLE);
            }

            for (int i = 0; i < tiles.size(); i++) {
                if (tiles.get(i) == view) {
                    firstClickedIndex = i;
                    tileNumberToMatch = tileNumbers.get(i);
                    for (int k = tileNumbers.size() - 1; k >= 0; k--) {
                        if (tileNumberToMatch == tileNumbers.get(k) && k != i) {
                            secondIndexToClick = k;
                        }
                    }
                    Log.d("CycleVisibility for", "Match found in IDs at index " + i + ". firstClickedIndex is now " + firstClickedIndex + ", and the tile number to match is " + tileNumberToMatch + ". Second index needed to be a correct match is " + secondIndexToClick);
                }
            }
        } else {
            Log.d("CycleVisibility for", "else entered");
            for (int i = 0; i < tiles.size(); i++) {
                if (tiles.get(i) == view && i == secondIndexToClick) {
                    Log.d("CycleVisibility for", "Correct second tile clicked: " + secondIndexToClick);
                    matchFound = true;
                    break;
                }
            }

            if (!matchFound) {
                Toast toast = Toast.makeText(this, "Those two cards did not match. +1 strike.", Toast.LENGTH_LONG);
                toast.show();
                //view.setVisibility(View.VISIBLE);
                incorrectButton1 = view;
                incorrectButton2 = tiles.get(firstClickedIndex);
                countStrikes();
            }

            firstClickedIndex = -1;
            secondIndexToClick = -1;
            matchFound = false;
        }
    }

    private void countStrikes() {
        strikes++;

        TextView strikeCounter = findViewById(R.id.strikeCounter);
        strikeCounter.setText("Incorrect Matches: " + strikes + "/3");

        if (strikes == 3) {
            launchLoseActivity();
            lose++;
        }

    }

    public void launchLoseActivity() {
        Intent intent = new Intent(this, LoseActivity.class);
        startActivity(intent);
    }
}

