package games.battleship.battleship3;

import games.imagegrid.ObservableGrid;

import java.util.Collection;
import java.util.List;

public interface IBattleship extends ObservableGrid {

	char CELL_OCEAN = '~';
	char CELL_EMPTY = '.';
	char CELL_SHIP_HIT = 'X';
	char CELL_DESTROYER = 'D';
	char CELL_SUBMARINE = 'S';
	char CELL_CRUISER = 'U';
	char CELL_BATTLESHIP = 'B';
	char CELL_CARRIER = 'C';


	/*
     * Initializes a game with a List representation of all the ships on the board.
     */
	void init(List<Ship> ships);
	
	/*
     * Initializes a game with a Collection representation of all the ships on the board.
     */
	void init(Collection<Ship> ships);
	
	/**
	 * @return All the points from fireshots.
	 */	
	int getAccPoints();

	/**
	 * @return The width/height of the grid, as the height and width should be equal.
	 */
	int getSize();

	/**
	 * @return All ships placed on the board
	 */
	Collection<Ship> getShips();


	/**
	 * Gets the hit state of the cell, i.e. if it has been fired at.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The hit state of the cell.
	 */
	boolean isCellHit(int x, int y);

	/**
	 * Gets the hit state of the cell, i.e. if it has been fired at.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return there is a ship in the cell
	 */
	boolean shipOnCell(int x, int y);
	
	/**
	 * Gets the hit state of the cell, i.e. if it has been fired at.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return the character state of the cell of the ship
	 */
	String whichShipOnCell(int x, int y);

	/**
	 * Counts the free fired cells.
	 * @return the count
	 */
	int countRects();
	
	/**
	 * Counts the ships that are not destroyed.
	 * @return the count.
	 */
	public int shipsAlive();

	/**
	 * Fires a shot for the current player.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return Null if no ship at coordinate. True if the ship was sunk, false if not.
	 */
	Boolean fire(int x, int y);
	
	/**
	 * Get the ship in that cell, or null if the cell is empty.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The ship.
	 */
	Ship getCellShip(int x, int y);
	
	/**
	 * Get Cell back from specific coordinates.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The Cell.
	 */
	Cell getCell(int x, int y);

	/**
	 * Get Cell back from specific coordinates.
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The Cell.
	 */
	Cell getCell2(int x, int y) ;
	
	/**
	*  @return The counter of the shots.
	*/
	int getAllHits();
	
	/**
	*  @return The counter of the winning shots.
	*/
	int getGreatHits();
}
