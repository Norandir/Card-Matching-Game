import java.util.ArrayList;
import java.util.Random;
//FIXME: Add java doc for class
public class GenerateRandomGrid {

    /*
    *Default Constructor. Nothing Special here
     */
    public GenerateRandomGrid() {

    }

    /*
    * Generates pairs of integers starting from 1 and ending at numTiles/2
    * After generating pairs, the ArrayList acting as the grid has it's elements randomized
    * Finally, passes ArrayList grid back to caller
     */
    private ArrayList<Integer> generateGrid(int numTiles) {
        //ArrayList to hold integers for the grid
        ArrayList<Integer> grid = new ArrayList<Integer>;

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

        //Take a random number between 100 and 600 and randomly switch two array positions that many times
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

    /*
    * Sets the values for tiles from a given ArrayList. Tiles are set Randomly from a randomly sorted Integer ArrayList
     */
    public void setTilesRandomly(ArrayList<Integer> tiles) { //FIXME: Replace ArrayList<Integer> with ArrayList<"Object"> once determined
        ArrayList<Integer> grid = generateGrid(tiles.size());

        for (int i = 0; i < tiles.size(); i++) {
            //tiles.get(i).setText(Integer.toString(grid.get(i))); //FIXME: Fix code to work once the FIXME above is fixed.
        }
    }
}
