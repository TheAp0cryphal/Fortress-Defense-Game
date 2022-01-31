package ca.model.fortressdefense;

/**
 * Represent a cell in a grid
 * Building blocks of Tanks
 * Responsible for telling if cells are active or occupied by a tank
 */


public class Cell {
    private boolean isActive;
    private boolean isOccupied = false;
    private char holdingValue;
    private int rowCoordinate;
    private int columnCoordinate;

    public Cell(int rowCoordinate, int y, char holdingValue){
        this.rowCoordinate = rowCoordinate;
        this.columnCoordinate = y;
        this.holdingValue = holdingValue;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public char getHoldingValue() {
        return holdingValue;
    }

    public void setHoldingValue(char holdingValue) {
        this.holdingValue = holdingValue;
    }

    public void hit(char holdingValue){
        this.isActive = false;
        this.holdingValue = holdingValue;
    }

    public void displayCheat(){
        System.out.print(holdingValue);
        System.out.print(" ");
    }
    public void displayNormal(){
        // not active = does not belong to tank (isOccupied = false) or has already been hit (is occupied = true)
        if(!isActive && isOccupied) {
            System.out.print("X");
        }
        else if (isActive && isOccupied) {
            System.out.print("~");
        }
        else if (!isActive) {
            System.out.print(" ");
        }
        else {
            System.out.print("~");
        }

        System.out.print(" ");
    }

    public boolean isActive() {
        return isActive;
    }
}
