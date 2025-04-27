package GameModel.Aliens;

import GameController.Interfaces.GameObject;
import GameModel.Ship;
import GameModel.SpaceInvadersBoard;
import GameModel.Interfaces.AlienSpaceInvadersModel;

/**
 * Represents the special red ship that occasionally appears at the top of the screen
 * This ship provides a high score when hit and moves horizontally across the board
 */
public class redShip implements GameObject, AlienSpaceInvadersModel {
	
	private AlienType type;
	private int x, y, width, height, speed, deathTicks, scoreValue;
	public SpaceInvadersBoard board;
	boolean isAlive = false;
	private boolean movingRight = true;
	
	/**
	 * Constructs a new red ship with given position and size
	 *
	 * @param x      The x-position of the ship
	 * @param y      The y-position of the ship
	 * @param width  The width of the ship
	 * @param height The height of the ship
	 */
	public redShip (int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = 20;
		this.type = AlienType.REDSHIP;
		this.scoreValue = 250;
	}
	
	/**
	 * Creates and returns a new red ship centered at the top of the board
	 *
	 * @param board The game board the ship belongs to
	 * @return A new redShip instance
	 */
	public static redShip newRedShip(SpaceInvadersBoard board) {
	    double scaleFactor = 0.7;

	    int unscaledWidth = board.cellSize() * 2;
	    int unscaledHeight = board.cellSize();

	    int width = (int) (unscaledWidth * scaleFactor);
	    int height = (int) (unscaledHeight * scaleFactor);

	    int centerX = (board.cellSize() * board.cols()) / 2;
	    int x = centerX - (width / 2);

	    int y = board.cellSize() * 7 + (unscaledHeight - height) / 2;

	    return new redShip(x, y, width, height);
	}
	
	/**
	 * Revives the red ship, making it alive again.
	 */
	public void revive() {
		isAlive = true;
	}
	
	@Override
	public void update() {
		if (!isAlive) {
			deathTicks++;
		}
	}

	@Override
	public void moveAlien() {
		if (movingRight) {
            x += speed; 
        } else {
            x -= speed; 
        }
	}

	@Override
	public void reverseDirection() {
		movingRight = !movingRight;
	}
	
	@Override
	public boolean isAlive() {
		return isAlive;
	}

	@Override
	public void kill() {
		isAlive = false;
		deathTicks = 0;
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
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getScoreValue() {
		return scoreValue;
	}

	@Override
	public int getDeathTicks() {
		return deathTicks;
	}
	
	@Override
	public AlienType getAlienType() {
		return type;
	}

}
