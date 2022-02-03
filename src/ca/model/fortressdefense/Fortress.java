package ca.model.fortressdefense;

/**
 * Represents fortress
 * Responsible Managing fortress health and damage
 */

public class Fortress {
    private int health;

    public Fortress() {
        this.health = 2500;
    }

    public int getHealth() {
        return health;
    }

    public void damage(int damage){

        this.health = this.health - damage;
        if(this.health < 0) {
            this.health = 0;
        }
    }

}
