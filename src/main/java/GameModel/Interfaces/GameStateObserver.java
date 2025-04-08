package GameModel.Interfaces;

import GameModel.GameState;

public interface GameStateObserver {
	
	GameState getGameState();
	
	int getTimeInterval();
	
	void clockTick();


}
