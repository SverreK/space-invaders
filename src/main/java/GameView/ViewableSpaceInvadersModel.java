package GameView;

import GameModel.Aliens;
import GameModel.GameState;
import GameModel.Ship;
import GameModel.Interfaces.GridDimension;

public interface ViewableSpaceInvadersModel {
	
	GridDimension getDimension();
	
	Ship getShip();
	
	GameState getGameState();
	
	int getScore();
	
	int getLevel();

}
