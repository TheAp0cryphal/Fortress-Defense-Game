package ca.model.fortressdefense;

/**
 * Represents a tank objects
 * Responsible for creating tank
 * Manages and computes its health and damage
 */


public class Tank {
    private int health;
    private char tankIdentifier;

    public Tank(char tankIdentifier) {
        this.health = 5;
        this.tankIdentifier = tankIdentifier;
    }

    public void damageTank() {
        this.health -= 1;
    }

    public int getDamage() {
        return switch (this.health) {
            case 5, 4 -> 20;
            case 3 -> 5;
            case 2 -> 2;
            case 1 -> 1;
            default -> 0;
        };
    }

    public char getTankIdentifier() {
        return tankIdentifier;
    }

}



