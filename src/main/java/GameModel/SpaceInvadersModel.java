package GameModel;

import java.util.ArrayList;
import java.util.Iterator;

import GameModel.Interfaces.AlienSpaceInvadersModel;
import GameModel.Interfaces.GameStateObserver;
import GameModel.Interfaces.GridDimension;
import GameView.ViewableSpaceInvadersModel;

public class SpaceInvadersModel implements ViewableSpaceInvadersModel, GameStateObserver {
	
	private SpaceInvadersBoard board;
	
	private Ship ship;
	private Projectile projectile;
	
	private GameState state = GameState.ACTIVE_GAME;
	
	public SpaceInvadersModel() {
		this(new SpaceInvadersBoard(16, 16));
	}
	
	public SpaceInvadersModel(SpaceInvadersBoard board) {
		this.board = board;
		this.ship = Ship.newShip(board);
	}
	
	public boolean moveShip(int delta_x, int delta_y) {
		Ship shifted = ship.shiftedBy(delta_x, delta_y);
		if(shifted.getX() >= 0 && shifted.getX() + shifted.getWidth() <= board.size() * board.cols()) {
			this.ship = shifted;
			return true;
		}
		
		return false;
	}
	
	@Override
	public GridDimension getDimension() {
		return board;
	}
	
	@Override
	public Ship getShip() {
		return ship;
	}

	@Override
	public int getTimeInterval() {
		return (int) (1000 * Math.pow(0.9, getLevel()));
	}

	@Override
	public void clockTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GameState getGameState() {
		return state;
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

}
