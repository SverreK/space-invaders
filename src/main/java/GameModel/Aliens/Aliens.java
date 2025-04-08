package GameModel.Aliens;

import java.util.ArrayList;
import java.util.Iterator;

import GameController.Interfaces.CollidableSpaceInvadersModel;
import GameModel.Interfaces.AlienSpaceInvadersModel;
import GameModel.SpaceInvadersBoard;

public class Aliens implements CollidableSpaceInvadersModel, AlienSpaceInvadersModel {
	
	private int x, y;
	private int width, height;
	private int speed;
	private int cols = 11;
	private SpaceInvadersBoard board;
	
	boolean isAlive = true;
	
	public Aliens(int x, int y, int width, int height, int speed) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
	}
	
	public static Aliens newAlien(SpaceInvadersBoard board) {
		int width = board.size()*2;
		int height = board.size();
		int x = board.size();
		int y = board.size();
		
		return new Aliens(x, y, width, height, 5);
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
	public void moveAlien() {
		x += speed;
		
	}

	@Override
	public void reverseDirection() {
		x -= speed;
		
	}

	@Override
	public void dropDown() {
		y += 32;
		
	}

	@Override
	public void kill() {
		isAlive = false;
		
	}

	@Override
	public boolean isAlive() {
		return isAlive;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getAlienCols() {
		return cols;
	}

}
