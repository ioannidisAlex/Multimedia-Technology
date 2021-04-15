package games.battleship.battleship3;

import games.IPersistable;

/**
 * Created by mats some day ago.
 */
public interface IBattleshipGame extends IPersistable {

    /*
     * Initialize the game with the two given boards.
     */
    void init(IBattleship board1, IBattleship board2);

    /*
     * Returns the two boards belonging to the game.
     * If the game has not been initialized yet, it should be initialized with empty boards and return these.
     */
    IBattleship[] getBoards();
    
    /**
     * Returns the how many rounds have been played.
     * @return returns the counter rounds.
     */
    public int  getRounds();
    
    /*
     * Set the counter of rounds that have been played.
     */
    public void setRounds(int incrementedRound);

}
