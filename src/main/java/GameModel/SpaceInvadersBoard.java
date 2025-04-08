package GameModel;

import GameModel.Interfaces.GridDimension;

public class SpaceInvadersBoard implements GridDimension {
	
	private static final int TILE_SIZE = 32;
	private final int rows;
	private final int cols;
	
	public SpaceInvadersBoard(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		
	}
	


	@Override
	public int rows() {
		return rows;
	}

	@Override
	public int cols() {
		return cols;
	}

	@Override
	public int size() {
		return TILE_SIZE;
	}
	
	

}
