package games.battleship.battleship3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomEnemy extends Battleship implements IEnemy {

    private boolean targetMode = false;
    private List<GridLocation> targetStack = new ArrayList<>();

    public GridLocation targetRandomly() {
        Random rand = new Random();
        int rand_int_x = rand.nextInt(10);
        int rand_int_y = rand.nextInt(10);
        while (rand_int_x>9 || rand_int_y > 9 || isCellHit(rand_int_x, rand_int_y)) {
        	rand_int_x = rand.nextInt(10);
        	rand_int_y = rand.nextInt(10);
        }
        return new GridLocation(rand_int_x, rand_int_y);
    }
    
    @Override
    public GridLocation target() {

        if (! targetMode) {
            GridLocation loc = targetRandomly();
            if (getCellShip(loc.getX(), loc.getY()) != null) {
                targetMode = true;
                targetStack.addAll(0, loc.getNeighbors(getSize()));
            }
            return loc;
        }

        else {
            if (! targetStack.isEmpty())  {
                GridLocation next = targetStack.remove(0);
                if (getCellShip(next.getX(), next.getY()) != null) {
                    next.getNeighbors(getSize()).stream()
                            .filter(n -> (!targetStack.contains(n)) && !isCellHit(n.getX(), n.getY()))
                            .forEach(n ->
                                targetStack.add(0, n)
                    );
                }
                return next;
            }
            else {
                targetMode = false;
                return targetRandomly();
            }
        }
    }
}
