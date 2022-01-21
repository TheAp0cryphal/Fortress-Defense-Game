package ca.ui.fortressdefense;

import ca.model.fortressdefense.Cell;

import java.util.Scanner;

/*
 * Responsible for taking user input and displaying GameBoard to the user
 * Validates the user's input
 */

public class GameTextUI {

    private int rowVal;
    private int colVal;

    public int[] getInput() {
        Scanner sc = new Scanner(System.in);
        String userInput;

        do {
            System.out.print("Enter Coordinates to Hit -> ");
            userInput = sc.nextLine();
        } while (!validateInput(userInput));

        int[] in = new int[2];
        in[0] = rowVal;
        in[1] = colVal;
        return in;
    }

    public boolean validateInput(String userInput) {

        if (userInput.length() == 2 || userInput.length() == 3) {
            userInput = userInput.toUpperCase();

            char firstChar = userInput.charAt(0);
            char secondChar = userInput.charAt(1);
            char thirdChar = 'x';

            if (userInput.length() == 3) {
                if (userInput.charAt(2) == '0') {
                    thirdChar = '0';
                } else {
                    System.out.print("Error...");
                    return false;
                }
            }

            if (firstChar > 'J' || firstChar < 'A' || secondChar < '1' || secondChar > '9') {
                System.out.println("Error... Wrong Coordinates! Try Again!");
                return false;
            } else {
                rowVal = (int) firstChar - 'A';
                colVal = (int) secondChar - '1';
                if (secondChar == '1' && thirdChar == '0') {
                    colVal = 9;
                }
                return true;
            }
        }
        System.out.println("Error... Wrong Coordinates! Try Again!");
        return false;
    }

    public void displayGrid(Cell[][] grid, char mode) {
        int colSize = grid.length;
        int rowSize = grid.length;

        for (int i = 0; i < rowSize; i++) {
            if (i == 0) {
                System.out.print("  ");
            }
            System.out.print((i + 1) + " ");
        }

        System.out.print("\n");

        for (int i = 0; i < rowSize; i++) {
            char x = 'A';
            System.out.print((char) (x + i) + " ");
            for (int j = 0; j < colSize; j++) {
                if (mode == 'c') {
                    grid[i][j].displayCheat();
                } else if (mode == 'n') {
                    grid[i][j].displayNormal();
                } else {
                    grid[i][j].displayCheat();
                }
            }
            System.out.print("\n");
        }
    }
}


