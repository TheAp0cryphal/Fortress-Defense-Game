package ca.model.fortressdefense;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates tanks and gameboard
 * Randomly places the tanks on the gameboard in Pentomino shape
 * Supports error checking by checking if more tanks can be placed on cell
 */

public class BoardGenerator {
    private static Random random = new Random();
    private static final int EXIT = -1;
    private static char intialTankIdentifier = 'A';
    private static int currentRowCoordinate = 0;
    private static int currentColumnCoordinate = 0;
    private static List<String> availableAdjacentDirections = new ArrayList<>();
    private static boolean checkIfTankPlaced;
    private static List<String> totalTankAdjacentCells = new ArrayList<>();
    private static List<String> indexesToBeRemoved = new ArrayList<>();
    private static List<Cell> occupiedCellsList = new ArrayList<>();

    public static void generateBoard(int numOfTanks, Cell[][] grid, List<String> gridCellIdentifier) {
        createTanks(numOfTanks);
        placeTanksOnGrid(grid, gridCellIdentifier);
    }

    private static void placeTanksOnGrid(Cell[][] grid, List<String> gridCellIdentifier) {
        for (int i = 0; i < TankManager.getSize(); ) {
            Tank tank = TankManager.getTank(i);
            checkIfTankPlaced = placeTank(tank, grid, gridCellIdentifier);
            if (checkIfTankPlaced) {
                i++;
            }
        }
    }

    private static void createTanks(int numOfTanks) {
        for (int i = 0; i < numOfTanks; i++) {
            char tankIdentifier = (char) (intialTankIdentifier + i);
            Tank tank = new Tank(tankIdentifier);
            TankManager.add(tank);
        }
    }

    private static boolean placeTank(Tank tank, Cell[][] grid, List<String> gridCellIdentifier) {
        int adjacentCellCounter = 0;
        //Reset all list
        refreshList();
        Cell cell;

        checkIfMoreTanksCanBePlaced(gridCellIdentifier);

        int randomCellIdentifier = generateRandomIdentifier(gridCellIdentifier.size());
        String randomCellIdentifierValue = gridCellIdentifier.get(randomCellIdentifier);
        extractDirectionCoordinates(randomCellIdentifierValue);
        cell = grid[currentRowCoordinate][currentColumnCoordinate];

        // Check cell is occupied or not
        if (cell.isOccupied()) {
            while (cell.isOccupied()) {
                randomCellIdentifier = generateRandomIdentifier(gridCellIdentifier.size());
                randomCellIdentifierValue = gridCellIdentifier.get(randomCellIdentifier);
                extractDirectionCoordinates(randomCellIdentifierValue);
                cell = grid[currentRowCoordinate][currentColumnCoordinate];
            }
        }

        // Placing the first cell on the grid
        updateCellValue(tank, cell);
        occupiedCellsList.add(cell);
        adjacentCellCounter++;
        indexesToBeRemoved.add(randomCellIdentifierValue);
        calculateAdjacent();

        //placing the adjacent cells on grid
        while (adjacentCellCounter < 5) {
            randomCellIdentifier = generateRandomCoordinates(availableAdjacentDirections.size());
            randomCellIdentifierValue = availableAdjacentDirections.get(randomCellIdentifier);
            extractDirectionCoordinates(randomCellIdentifierValue);
            cell = grid[currentRowCoordinate][currentColumnCoordinate];

            checkIfMoreTanksCanBePlaced(gridCellIdentifier);

            if (cell.isOccupied()) {
                while (cell.isOccupied()) {
                    totalTankAdjacentCells.remove(randomCellIdentifierValue);
                    availableAdjacentDirections.remove(randomCellIdentifierValue);
                    randomCellIdentifier = generateRandomCoordinates(availableAdjacentDirections.size());
                    if (randomCellIdentifier == -1) {
                        if (totalTankAdjacentCells.isEmpty()) {
                            resetBoardForUnoccupiedCells();
                            removeIndexesFromGrid(gridCellIdentifier);
                            return false;
                        }
                        randomCellIdentifier = generateRandomCoordinates(totalTankAdjacentCells.size());
                        randomCellIdentifierValue = totalTankAdjacentCells.get(randomCellIdentifier);
                    } else {
                        randomCellIdentifierValue = availableAdjacentDirections.get(randomCellIdentifier);
                    }
                    extractDirectionCoordinates(randomCellIdentifierValue);
                    cell = grid[currentRowCoordinate][currentColumnCoordinate];
                }
            }
            updateCellValue(tank, cell);
            occupiedCellsList.add(cell);
            indexesToBeRemoved.add(randomCellIdentifierValue);
            calculateAdjacent();
            adjacentCellCounter++;
        }

        return isTankPlaced(gridCellIdentifier, adjacentCellCounter);
    }

    private static boolean isTankPlaced(List<String> gridCellIdentifier, int adjacentCellCounter) {
        if (adjacentCellCounter == 5) {
            removeIndexesFromGrid(gridCellIdentifier);
            return true;
        } else {
            resetBoardForUnoccupiedCells();
            return false;
        }
    }

    private static void checkIfMoreTanksCanBePlaced(List<String> gridCellIdentifier) {
        if (gridCellIdentifier.size() < 5) {
            System.out.println("Error....No more tanks can be placed");
            System.exit(EXIT);
        }
    }

    private static void removeIndexesFromGrid(List<String> gridCellIdentifier) {
        for (String toRemove : indexesToBeRemoved) {
            gridCellIdentifier.remove(toRemove);
        }
    }

    private static void resetBoardForUnoccupiedCells() {
        for (Cell newCell : occupiedCellsList) {
            newCell.setOccupied(false);
            newCell.setHoldingValue('.');
        }
    }

    private static void updateCellValue(Tank tank, Cell cell) {
        cell.setOccupied(true);
        cell.setHoldingValue(tank.getTankIdentifier());
    }

    private static void refreshList() {
        totalTankAdjacentCells.clear();
        indexesToBeRemoved.clear();
        occupiedCellsList.clear();
    }

    private static void calculateAdjacent() {
        availableAdjacentDirections.clear();
        int rowCoordinate = currentRowCoordinate;
        int colCoordinate = currentColumnCoordinate;
        //Calculate adjacentCellsDirections
        manageRowDirections(rowCoordinate, colCoordinate);
        manageColumnDirections(rowCoordinate, colCoordinate);
    }

    private static void manageRowDirections(int rowCoordinate, int colCoordinate) {
        if (rowCoordinate == 0) {
            String rowMoveUp = "" + (rowCoordinate + 1) + colCoordinate;
            availableAdjacentDirections.add(rowMoveUp);
            totalTankAdjacentCells.add(rowMoveUp);
        } else if (rowCoordinate == 9) {
            String rowMoveDown = "" + (rowCoordinate - 1) + colCoordinate;
            availableAdjacentDirections.add(rowMoveDown);
            totalTankAdjacentCells.add(rowMoveDown);
        } else {
            // Calculate row coordinate
            String rowMoveUp = "" + (rowCoordinate + 1) + colCoordinate;
            String rowMoveDown = "" + (rowCoordinate - 1) + colCoordinate;
            availableAdjacentDirections.add(rowMoveUp);
            availableAdjacentDirections.add(rowMoveDown);
            totalTankAdjacentCells.add(rowMoveUp);
            totalTankAdjacentCells.add(rowMoveDown);
        }
    }

    private static void manageColumnDirections(int rowCoordinate, int colCoordinate) {
        if (colCoordinate == 0) {
            String colMoveUp = "" + rowCoordinate + (colCoordinate + 1);
            availableAdjacentDirections.add(colMoveUp);
            totalTankAdjacentCells.add(colMoveUp);
        } else if (colCoordinate == 9) {
            String colMoveDown = "" + rowCoordinate + (colCoordinate - 1);
            availableAdjacentDirections.add(colMoveDown);
            totalTankAdjacentCells.add(colMoveDown);
        } else {
            String colMoveUp = "" + rowCoordinate + (colCoordinate + 1);
            String colMoveDown = "" + rowCoordinate + (colCoordinate - 1);
            availableAdjacentDirections.add(colMoveUp);
            availableAdjacentDirections.add(colMoveDown);
            totalTankAdjacentCells.add(colMoveDown);
            totalTankAdjacentCells.add(colMoveUp);
        }
    }

    private static void removeFromList(String randomCellIdentifierValue, List<String> gridCellIdentifier) {
        gridCellIdentifier.remove(randomCellIdentifierValue);
    }

    public static void extractDirectionCoordinates(String randomCellIdentifier) {
        int extractedIdentifier = Integer.parseInt(randomCellIdentifier);
        currentRowCoordinate = extractedIdentifier / 10;
        currentColumnCoordinate = extractedIdentifier % 10;
    }

    public static int generateRandomIdentifier(int size) {
        if (size != 0) {
            return (random.nextInt(size));
        } else {
            System.out.println("No more tanks can be placed");
            System.exit(-1);
            return 0;
        }

    }

    private static int generateRandomCoordinates(int size) {
        if (size != 0) {
            return (random.nextInt(size));
        } else {
            return -1;
        }
    }

}
