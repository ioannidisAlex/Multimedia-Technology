// All the nice code of ImageGrid and etc are from Hallvard Trætteberg's githubs account

package games.battleship.battleship3;

import games.IUpdateable;
import games.PersistableController;
import games.imagegrid.GridListener;
import games.imagegrid.ImageGridGame;
import games.imagegrid.ObservableGrid;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class BattleshipFX extends ImageGridGame<String> implements IUpdateable, GridListener {
	
	@FXML
	private Text messageText;
	
	@FXML
	private Text generalInfoGame;

    @FXML
    private IBattleshipGame game;

	@FXML private IBattleship playerBoard;
	@FXML private IEnemy enemy;

	private IBattleship[] battleships;
	DefaultBattleshipPersistence persistence;

	@FXML
	private PersistableController persistableController;

	@FXML
	protected void initialize() {
		super.initialize();
		game = new BattleshipGame();
		
		generalInfoGame.setText("Click Start up-right corner");
		//persistableController.setStateStore(game);
		//persistableController.setUpdate(this);

		battleships = new IBattleship[]{playerBoard, enemy};
	}

	private int player = 0, winner = -1; // 0 or 1, index into arrays
	private String[] responses = {", you're up!", ", it's time to show who's boss.", ", fire at will!", ", don't just sit there.", ", you know what to do."};

	private String randomResponse() {
		return responses[(int) (Math.random() * responses.length)];
	}

	@FXML
	private void startGame() {
		System.out.println("Startin");
        fillGrid(null);
		int size = 10;
        battleships[0].init(playerBoard.getShips());
        battleships[1].init(enemy.getShips());
        game.init(battleships[0], battleships[1]);
        //System.out.println("Size of list of ships "+game.getBoards()[0].getShips().size());
		imageGrid.setDimensions(size, size);
		enemyFire();
		updateCells();
		game.setRounds(1);
		updateState("Game started. It is Player " + player + "'s turn.");
		
		
	}
	
	@FXML
	private void loadSFT() {
		persistence = new DefaultBattleshipPersistence();
		try {
	        persistence.load(playerBoard,enemy);
	        //System.out.println(playerBoard.getShips());
	        }
	        catch(Exception e){
	        	System.out.println(e); 
	        }
	}

	private void showShipsOrNot(int x, int y) {
		char cellChar='K';
		if( player==0 && (game.getBoards()[player].shipOnCell(x,y)== true)) {
			switch (battleships[player].whichShipOnCell(x,y)) {
					case "Destroyer":
						cellChar = IBattleship.CELL_DESTROYER;
						break;
					case "Submarine":	
						cellChar = IBattleship.CELL_SUBMARINE;
						break;
					case "Cruiser":
						cellChar = IBattleship.CELL_CRUISER;
						break;
					case "Battleship":	
						cellChar = IBattleship.CELL_BATTLESHIP;
						break;
					case "Carrier":
						cellChar = IBattleship.CELL_CARRIER;
						break;		
			};
		}
		else {
			cellChar = IBattleship.CELL_OCEAN;
		}
		if (battleships[player].isCellHit(x, y)) {
			cellChar =  (battleships[player].getCellShip(x, y) == null) ? IBattleship.CELL_EMPTY : IBattleship.CELL_SHIP_HIT;
		}
		setCell(x, y, Character.toString(cellChar));
	}

	private void updateCells() {
		foreachCell(this::showShipsOrNot);
	}

	@Override
	protected boolean mouseCllicked(int x, int y, int clicks) {
        if (winner < 0) {
        	if(clicks%2 == 0 || clicks%2 ==1 ) {
	            //IBattleship battleship = battleships[0];
	            Boolean hit = game.getBoards()[1].fire(x, y);
	            //update(game.getBoards()[0], hit, x, y);
	            update(game.getBoards()[1], hit, x, y);
	            runDelayed(1500, () -> {
	                updateCells();
	                enemyFire();
	            });
        	}
        	else {
        		//System.out.println("alef");
        	}
        }
        return true;
    }

    private void enemyFire() {
    	int incCounterRound = game.getRounds();
    	incCounterRound ++;
    	game.setRounds(incCounterRound);
        GridLocation gridLocation = enemy.target();
        int x = gridLocation.getX();
        int y = gridLocation.getY();
        Boolean hit = enemy.fire(x, y);
        update(game.getBoards()[0], hit, x, y);
        runDelayed(1500, this::updateCells);
    }

	public static void main(String[] args) {
		launch(BattleshipFX.class);
	}

	@Override
	public void updateState(boolean fullUpdate) {}

	public void updateState(int x1, int y1, int x2, int y2) {
		updateState(true);
	}

	@Override
	public void updateState(String text) {
		messageText.setText(text);
	}

    @Override
    public void gridChanged(ObservableGrid grid, int x, int y, int w, int h) {
        showShipsOrNot(x, y);
    }

    private void update(IBattleship battleship, Boolean hit, int x, int y) {
    	if(game.getRounds()<=40) {
	    	showShipsOrNot(x, y);
	    	
	    	String Info;
	    	Info = ""+battleships[1].shipsAlive()+"Ships";
	    	Info += "  Points"+game.getBoards()[1].getAccPoints()+"";
	    	Info += "  "+game.getBoards()[1].getGreatHits()+" out of "+game.getBoards()[1].getAllHits();
	    	generalInfoGame.setText(Info);
	    	
	        
	        if ( battleship.countRects() == 0 ) {
	            winner = player;
	            updateState("GAME OVER! Player " + (winner + 1) + " won.");
	        } else {
	            String status;
	            if (hit == null) {
	                status = "Player " + (player + 1) + " missed!";
	            } else {
	                status = "Player " + (player + 1) + (hit ? " sunk your ship!" : " hit!");
	            }
	            status += " With score" + game.getBoards()[player].getAccPoints();
	            player = (player+1) % 2;
	            updateState(status + " Player " + (player + 1) + randomResponse());
	        }
    	}
    	else {
    		if(game.getBoards()[1].getAccPoints()>game.getBoards()[0].getAccPoints()) 
    			winner =1;
    		else
    			winner =0;
    		String FINISH= "FINISH";
    		updateState(FINISH + " Player " + (player + 1) + "WON!");
    	}
    }
}
