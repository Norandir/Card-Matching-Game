package com.example.cardmemorygame; /**
 * This class is built to handle creating a random grid for the game.
 * Once the grid is created, the code will load the values into each tile that needs to be assigned one.
 *
 * @author Sawyer Kole
 * @version 1.0
 * @since August 8, 2020
 */


import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class GenerateRandomGrid {

    /*
    *Default Constructor. Nothing Special here
     */
    public GenerateRandomGrid() {

    }

    /**
    * Generates pairs of integers starting from 1 and ending at numTiles/2
    * After generating pairs, the ArrayList acting as the grid has it's elements randomized
    * Finally, passes ArrayList grid back to caller
     */
    private ArrayList<Integer> generateGrid(int numTiles) {
        //ArrayList to hold integers for the grid
        ArrayList<Integer> grid = new ArrayList<Integer>();

        //Generates pairs of integers
        for (int i = 0; i < numTiles/2; i++) {
            grid.add(i + 1);
            grid.add(i + 1);
        }

        //Create rand and variables needed for next part
        Random rand = new Random();
        int k;
        int j;
        int x;

        //Picks two random array positions and switches their values. This is done between 100 and 600 times.
        for (int i = 0; i < (rand.nextInt(501) + 100); i++) {
            k = rand.nextInt(numTiles);
            j = rand.nextInt(numTiles);

            x = grid.get(k);
            grid.set(k, grid.get(j));;
            grid.set(j, x);
        }


        //Return ArrayList grid
        return grid;
    }

    /**
    * Sets the values for tiles from a given ArrayList. Tiles are set Randomly from a randomly sorted Integer ArrayList
     */
    public ArrayList<Integer> setTilesRandomly(ArrayList<Button> tiles) {
        ArrayList<Integer> grid = generateGrid(tiles.size());

        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).setText(Integer.toString(grid.get(i)));
        }
        return grid;
    }
}
