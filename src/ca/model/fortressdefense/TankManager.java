package ca.model.fortressdefense;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Responsible for managing multiple tanks
 */

public class TankManager implements Iterable<Tank>{

    private static List<Tank> tanks = new ArrayList<>();

    public static void add(Tank tank){
        tanks.add(tank);
    }

    public static int getSize(){
        return tanks.size();
    }

    public static Tank getTank(int index){
        return tanks.get(index);
    }
    @Override
    public Iterator<Tank> iterator() {
        return tanks.iterator();
    }
}
