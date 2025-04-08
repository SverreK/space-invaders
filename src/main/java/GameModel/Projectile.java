package GameModel;

import java.awt.image.BufferedImage;

import GameController.Interfaces.CollidableSpaceInvadersModel;
import GameController.Interfaces.MoveableSpaceInvadersModel;
import GameModel.Interfaces.GameStateObserver;

public class Projectile implements MoveableSpaceInvadersModel, CollidableSpaceInvadersModel {
	
	private int x, y;
	private int speed = -5;
	private BufferedImage sprite;
	
	public Projectile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	@Override
	public void update() {
		y += speed;
		
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
	public int getWidth() {
		return this.sprite.getWidth();
	}

	@Override
	public int getHeight() {
		return this.sprite.getHeight();
	}

}
