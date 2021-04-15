package games.battleship.battleship3;

import games.imagegrid.GridListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Battleship implements IBattleship {

	private int size = 10;
	private List<Cell> board;
	
	private int accumPoints = 0;
	private int allHits=0;
	private int greatHits=0;

	private Collection<Ship> ships = new ArrayList<>();

    private List<GridListener> listeners = new ArrayList<>();
	
    public Battleship() {}


    public void addShip(Ship ship) {
        ships.add(ship);
        if(ship.getOrientationIsVertical()==false){
            int col = ship.getX(); 
            for (int row = ship.getY(); row < ship.getY() + ship.getLength(); row++) {
                board.set(row * size + col, new Cell(ship));
            }
        }
        else {
            int row = ship.getY();
            for (int col = ship.getX(); col < ship.getX() + ship.getLength(); col++){
                board.set(row * size + col, new Cell(ship));
            }
        }
    }
	
	public void init(List<Ship> shipss) {

        board = new ArrayList<>();
        this.ships = shipss;
        // Fill board list with empty cells
		for (int i = 0; i < 100; i++) {
            board.add(new Cell(null));
        }

        // Place ships
		for (Ship ship : this.ships) {
			int x = ship.getX();
            int y = ship.getY();
            int length;
            //System.out.println("thesi (x,y)= "+x+y);
            if(ship.getOrientationIsVertical()==true){
                length=ship.getLength();
                //System.out.println("len"+height);
                for (int col = x; col < x + length; col++) {
                    Cell cell = new Cell(ship);
                    cell.setHit(false);
                    board.set(col * size + y, cell);
                }
            }
            else {
                length=ship.getLength();
                //System.out.println("len"+height);
                for (int row = y; row < y + length; row++) {
                        Cell cell = new Cell(ship);
                        cell.setHit(false);
                        board.set(x * size + row, cell);
                }
            }
        }
	}
	
	public void init(Collection<Ship> shipss) {

        board = new ArrayList<>();
        this.ships = shipss;
        // Fill board list with empty cells
		for (int i = 0; i < 100; i++) {
            board.add(new Cell(null));
        }
		//System.out.println("posa ships"+ships.size());
        // Place ships
		
        for (Ship ship : this.ships) {
        	
        	//ships.add(ship);

            int x = ship.getX();
            int y = ship.getY();
            int length;
            //System.out.println("thesi (x,y)= "+x+y);
            if(ship.getOrientationIsVertical()==true){
                length=ship.getLength();
                //System.out.println("len"+height);
                for (int col = x; col < x + length; col++) {
                    Cell cell = new Cell(ship);
                    cell.setHit(false);
                    board.set(col * size + y, cell);
                }
            }
            else {
                length=ship.getLength();
                //System.out.println("len"+height);
                for (int row = y; row < y + length; row++) {
                        Cell cell = new Cell(ship);
                        cell.setHit(false);
                        board.set(x * size + row, cell);
                }
            }
        }
	}

    @Override
    public Collection<Ship> getShips() {
        return ships;
    }


    @Override
    public Ship getCellShip(int x, int y) {
        return board.get(y * size + x).getShip();
    }

    @Override
    public int getSize() {
        return size;
    }

    public Cell getCell(int x, int y) {
    	//System.out.printf("GetFrom (%d ,%d )", x, y);
        return board.get(y * size + x);
    }
    
    public Cell getCell2(int x, int y) {
    	//System.out.printf("GetFrom (%d ,%d )", x, y);
        return board.get(x * size + y);
    }
    
    public int getPoints(int x, int y) {
    	return board.get(y * size + x).getPoints();
    }
    
    public int getAllHits() {
    	return this.allHits;
    }
    
    public int getGreatHits() {
    	return this.greatHits;
    }
    
    public int getAccPoints() {
    	return accumPoints;
    }

    @Override
    public boolean isCellHit(int x, int y) {
        return getCell(x, y).isHit();
    }
    
    @Override
    public boolean shipOnCell(int x, int y) {
        int epil =getCell(x, y).getCharacter();
        if (epil == 'D'|| epil =='S'|| epil == 'U'|| epil == 'B'|| epil == 'C') {
        	return true;
        }
        return false;
    }
    
    @Override
    public String whichShipOnCell(int x, int y) {
        return getCell(x, y).getShipType();
    }
    

	public boolean isSunk(Ship ship) {
        if(ship.getOrientationIsVertical()==true){
            for (int row = ship.getX(); row < ship.getX() + ship.getLength(); row++) {
            	if(! getCell(ship.getY(),row).isHit()) {
                	System.out.println("wwhere is"+ship.getX()+""+ship.getY());
                	return false;
                }
            }
            if(ship.getAlive()>0) {
				switch (ship.getShipType()) {
					case "Carrier":
						accumPoints += 1000;
						ship.setAlive(0);
						break;
					case "Battleship":	
						accumPoints += 500;
						ship.setAlive(0);
						break;
					case "Cruiser":
						accumPoints += 250;
						ship.setAlive(0);
						break;
					case "Submarine":	
						accumPoints += 0;
						ship.setAlive(0);
						break;
					case "Destroyer":
						accumPoints += 0;
						ship.setAlive(0);
				};
            }
        }
        else {// may a change will come 
            //int row = ship.getX();
            for (int col = ship.getY(); col < ship.getY() + ship.getLength(); col++){
                if (! getCell(col,ship.getX()).isHit()) {
                    return false;
                }
            }
            if(ship.getAlive()>0) {
				switch (ship.getShipType()) {
					case "Carrier":
						accumPoints += 1000;
						ship.setAlive(0);
						break;
					case "Battleship":	
						accumPoints += 500;
						ship.setAlive(0);
						break;
					case "Cruiser":
						accumPoints += 250;
						ship.setAlive(0);
						break;
					case "Submarine":	
						accumPoints += 0;
						ship.setAlive(0);
						break;
					case "Destroyer":
						accumPoints += 0;
						ship.setAlive(0);
					};
            }
        }
		return true;
	}

	@Override
    public int countRects() {
		return (int) board.stream().filter(cell -> cell.isShip() && (cell.isHit() == false)).count();
    }
	
	@Override
	public int shipsAlive() {
		return 5-((int) ships.stream().filter(ship -> (ship.getAlive()==0)).count());
	}

	@Override
	public Boolean fire(int x, int y) {
		Cell cell = getCell(x, y);
		if(!cell.isHit()) {
			accumPoints += cell.getPoints();
			//System.out.println("HIT");
			cell.setHit(true);
			//System.out.println(cell.isHit());
		}
		allHits++;
	    Ship ship = cell.getShip();
	    if(ship == null) {
	    	return null;
	    }
	    else {
	    	greatHits++;
	    	//System.out.println(ship.getShipType());
	    	boolean b =isSunk(ship);
	    	return b;
	    }
        //return (ship == null) ? null: isSunk(ship);
    }

    @Override
    public void addGridListener(GridListener gridListener) {
        if (! listeners.contains(gridListener))
            listeners.add(gridListener);
    }

    @Override
    public void removeGridListener(GridListener gridListener) {
        if (listeners.contains(gridListener))
            listeners.remove(gridListener);

    }

}
