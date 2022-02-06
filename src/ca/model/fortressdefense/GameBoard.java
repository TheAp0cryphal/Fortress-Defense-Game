package ca.model.fortressdefense;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent our GameBoard
 * This class is responsible for the initialization of the grid.
 */

public class GameBoard {
    private Cell[][] grid;
    private List<String> gridCellIdentifier = new ArrayList<>();

    public void initializeGameBoard(int numOfTanks) {
        BoardGenerator.generateBoard(numOfTanks, grid, gridCellIdentifier);
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void initGrid(int rowSize, int colSize) {
        grid = new Cell[rowSize][colSize];

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                grid[i][j] = new Cell(i, j, '.');
                String identifier = "" + i + j;
                gridCellIdentifier.add(identifier);
                grid[i][j].setActive(true);
            }
        }
    }

}