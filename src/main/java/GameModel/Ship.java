package GameModel;

import java.awt.image.BufferedImage;

import GameController.Interfaces.CollidableSpaceInvadersModel;
import GameController.Interfaces.MoveableSpaceInvadersModel;

public class Ship implements MoveableSpaceInvadersModel, CollidableSpaceInvadersModel {
	
	private int x, y;
	private int width, height;
	private int lives = 3;
	private int speed = -5;
	
	public Ship(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public static Ship newShip(SpaceInvadersBoard board) {
		int width = board.size()*2;
		int height = board.size();
		int x = board.size() * board.cols() / 2 - board.size();
		int y = board.size() * (board.rows() - 2);
		
		return new Ship(x, y, width, height);
	}
	
	public Ship shiftedBy(int delta_x, int delta_y) {
		return new Ship(x + delta_x, y + delta_y, width, height);
	}
	

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getX() {
		System.out.println(this.x);
		return this.x;
	}

	@Override
	public int getY() {
		System.out.println(this.y);
		return this.y;
	}

}
