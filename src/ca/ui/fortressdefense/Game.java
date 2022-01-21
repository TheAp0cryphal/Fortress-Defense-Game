package ca.ui.fortressdefense;

import ca.model.fortressdefense.*;

/**
 * Responsible for running game
 * Initializes a gameboard and then calls on gametextui to take user input
 * Changes the gameboard based on user input
 */

public class Game {

    private static final int NUM_OF_ROW = 10;
    private static final int NUM_OF_COLUMNS = 10;
    private static final String error = "Wrong Number of Arguments (0, 1 or 2)";
    private static int numOfTanks;
    private static String gameMode = "normal";
    private static final int EXIT = -1;

    public static void main(String[] args) {

        if (args.length == 2) {
            numOfTanks = Integer.parseInt(args[0]);
            gameMode = args[1];
            if (!gameMode.equalsIgnoreCase("--cheat")) {
                System.out.println("Did you mean --cheat? Try again with proper spelling");
                System.exit(-1);
            }
        } else if (args.length == 1) {
            numOfTanks = Integer.parseInt(args[0]);
        } else if (args.length == 0) {
            numOfTanks = 5;
        } else {
            System.out.println(error);
            System.exit(EXIT);
        }

        if (numOfTanks == 0) {
            System.out.println("Error.. You need atleast 1 tank to play.");
            System.exit(-1);
        }

        GameBoard gameBoard = new GameBoard();
        gameBoard.initGrid(NUM_OF_ROW, NUM_OF_COLUMNS);
        gameBoard.initializeGameBoard(numOfTanks);

        Fortress fortress = new Fortress();
        GameTextUI gameTextUI = new GameTextUI();

        Cell[][] grid = gameBoard.getGrid();

        System.out.println("\nWelcome to Fortress Defense!");
        System.out.println("Starting game with " + numOfTanks + " tanks.\n");


        //Cheat mode
        if (gameMode.equalsIgnoreCase("--cheat")) {
            gameTextUI.displayGrid(grid, 'c');
        }


        int totalDamageToTanks = 0;

        while (!isGameOver(fortress.getHealth(), totalDamageToTanks, numOfTanks, grid)) {
            System.out.println(" ");
            grid = gameBoard.getGrid();

            //for normal mode
            gameTextUI.displayGrid(grid, 'n');
            System.out.println("Fortress Structure Left: " + fortress.getHealth());
            System.out.println(" (Lower case tank letters are where you shot.)");

            int[] input = gameTextUI.getInput();
            int row = input[0];
            int col = input[1];

            Cell cell = grid[row][col];

            totalDamageToTanks = fortressTurn(cell, totalDamageToTanks);

            tankTurn(fortress);

        }
    }

    private static void tankTurn(Fortress fortress) {

        int fortressDamageThisTurn = 0;

        for (int i = 0; i < numOfTanks; i++) {
            Tank tank = TankManager.getTank(i);

            int thisDamage = tank.getDamage();
            fortressDamageThisTurn += thisDamage;
            System.out.println("Alive tank #" + i + " of " + numOfTanks + " shot you for " + thisDamage);
        }
        // hit fortress with current damage
        fortress.damage(fortressDamageThisTurn);
    }

    private static int fortressTurn(Cell cell, int totalDamage) {
        if (cell.isOccupied() && cell.isActive()) {
            cell.setActive(false);
            char belong = cell.getHoldingValue();

            Tank tank = TankManager.getTank(belong - 'A');
            char holdingValueLower = Character.toLowerCase(belong);

            cell.hit(holdingValueLower);
            tank.damageTank();
            totalDamage++;

            System.out.println("Successful Hit!");
        } else if (!cell.isOccupied() && cell.isActive()) {
            System.out.println("Miss!");
            cell.setActive(false);
            cell.hit(' ');
        } else if (cell.isOccupied() && !cell.isActive()) {
            System.out.println("Successful Hit!");
        } else {
            System.out.println("Miss!");
        }
        return totalDamage;
    }

    private static boolean isGameOver(int health, int totalDamage, int numOfTanks, Cell[][] grid) {
        if (health == 0) {
            System.out.println("Fortress Structure Left : 0.");
            System.out.println("RIP Fortress 2k22 -- You lost!");
            GameTextUI gameTextUI = new GameTextUI();
            gameTextUI.displayGrid(grid, 'e');
            return true;
        } else if (totalDamage == (numOfTanks * 5)) {
            System.out.println("Congratulations! You've beat the Blitzkrieg (Wait for Luftwaffe now!)");
            GameTextUI gameTextUI = new GameTextUI();
            gameTextUI.displayGrid(grid, 'e');
            return true;
        }
        return false;
    }
}
