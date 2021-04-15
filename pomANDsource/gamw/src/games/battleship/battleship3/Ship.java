package games.battleship.battleship3;

public class Ship {

	public final String shipType;
	private final int x, y;
	private final boolean orientation;
	public int alive;

	public Ship(String shipType, int startx, int starty, boolean orientation) {
		this.shipType = shipType;
		this.orientation = orientation;
		this.x = startx;
		this.y = starty;
		switch (this.shipType) {
			case "Carrier":
				this.alive= 5;
				break;
			case "Battleship":	
				this.alive= 4;
				break;
			case "Cruiser":
				this.alive= 3;
				break;
			case "Submarine":	
				this.alive= 3;
				break;
			case "Destroyer":
				this.alive= 2;
		};
	}
	
	public int getLength() {
		switch (this.shipType) {
				case "Carrier":
					return 5;
				case "Battleship":	
					return 4;
				case "Cruiser":
					return 3;
				case "Submarine":	
					return 3;
				case "Destroyer":
					return 2;
			};
			return 0;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public boolean getOrientationIsVertical() {
		return orientation;
	}
	
	public int getAlive() {
		return this.alive;
	}
	
	public char getCharacter() {
		switch (this.getTypeOfShip ()) {
				case "Carrier":
					return 'C';
				case "Battleship":	
					return 'B';
				case "Cruiser":
					return 'U';
				case "Submarine":	
					return 'S';
				case "Destroyer":
					return 'D';
			};
			return 0;
		
	}

	public String getShipType() {
		return shipType;
	}

	public String getTypeOfShip (){
		return shipType;
	}
	
	public int howAlive() {
		return (this.getLength() - this.alive);
	}
	
	public void setAlive(int x) {
		this.alive = x;
	}

}
