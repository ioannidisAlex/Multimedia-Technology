package games.battleship.battleship3;

public class Cell {

	private final Ship ship;
	private boolean hit;
	private int points;

	public Cell(Ship ship) {
		this.ship = ship;
		setHit(false);
		if (ship != null) {
			switch (ship.getShipType()) {
				case "Carrier":
					this.points= 350;
					break;
				case "Battleship":	
					this.points= 250;
					break;
				case "Cruiser":
					this.points= 100;
					break;
				case "Submarine":	
					this.points= 100;
					break;
				case "Destroyer":
					this.points= 50;
			};
		}
		else
			this.points = 0;
	}

	public Ship getShip() {
		return ship;
	}

	
	public char getCharacter() {
		return ship != null ? ship.getCharacter() : IBattleship.CELL_OCEAN;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public String getShipType() {
		return ship != null ? ship.getShipType() : null;
	}

	public boolean isShip() {
		return ship != null;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public boolean isHit() {
		return this.hit;
	}

	public String toString() {
		char c;
        if (isHit())
            c = (isShip()) ? IBattleship.CELL_SHIP_HIT : IBattleship.CELL_EMPTY;
        else
            c = IBattleship.CELL_OCEAN;
        return String.valueOf(c);
	}
}
