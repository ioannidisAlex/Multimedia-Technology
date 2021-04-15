package games.battleship.battleship3;

import java.io.IOException;
import java.io.OutputStream;

import games.battleship.battleship3.exceptions.AdjacentTilesException;
import games.battleship.battleship3.exceptions.InvalidCountExeception;
import games.battleship.battleship3.exceptions.OverlapTilesException;
import games.battleship.battleship3.exceptions.OversizeException;

public interface IBattleshipPersistence {
	
	/**
	 * Initialises the IBattleship instance with the content of the provided InputStream. All other game state should be cleared.
	 * @param battleshipOne the IBattleship instance
	 * @param battleshipTwo the IEnemy which extends IBattleship instance
	 * @throws java.io.IOException
	 * @throws AdjacentTilesException
	 * @throws InvalidCountExeception
	 * @throws OverlapTilesException
	 * @throws OversizeException
	 */
	void load(IBattleship battleshipOne,IEnemy battleshipTwo) throws IOException,
															    OversizeException,
															    OverlapTilesException,
															    AdjacentTilesException,
															    InvalidCountExeception;

	/**
	 * Saves the state of the IBattleship instance to the provided OutputStream, so it can be restored with load
	 * @param battleship the IBattleship instance
	 * @param outputStream The stream to write the state to
	 * @throws java.io.IOException
	 */
	void save(IBattleshipGame battleship, OutputStream outputStream) throws IOException;
	
	
}