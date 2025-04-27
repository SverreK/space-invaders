package GameModel;

import java.util.ArrayList;
import java.util.List;

import GameController.Interfaces.ControllableSpaceInvadersModel;
import GameController.Interfaces.GameObject;
import GameModel.Aliens.AlienType;
import GameModel.Aliens.Aliens;
import GameModel.Aliens.redShip;
import GameModel.Interfaces.GridDimension;
import GameView.ViewableSpaceInvadersModel;

/**
 * The main model class for the Space Invaders game
 * Handles all game logic, including ship movement, bullet updates, alien behavior,
 * red ship events, scoring, level progression, and game state
 * Implements both viewable and controllable interfaces
 */
public class SpaceInvadersModel implements ViewableSpaceInvadersModel, ControllableSpaceInvadersModel {
	
	//Game objects
	private Ship ship;
	private redShip redAlienShip;
	private SpaceInvadersBoard board;
	
	//Game object functionalities
	private boolean alienToggle = false;
	private boolean shouldDrop = false;
	private int alienCount;

	//Game object lists
	private List<Aliens> aliens;
	private List<Bullet> bullets;

	//ClockTick tasks
	private GameState state = GameState.TITLE_SCREEN;
	private boolean gameOverHandled = false;
	private int tickCounter = 0;
	private int shootCoolDown = 40;
	private int timeSinceLastShot = shootCoolDown;
	private int ALIEN_MOVE_INTERVAL = 50;
	private int REDSHIP_SPAWN_INTERVAL = 500;
	private int REDSHIP_MOVE_INTERVAL = 4;
	private int REDSHIP_KILL_INTERVAL = 1000;
	
	//Level and score
	private int level = 1;
	private int score, hiScore;
	
	/**
	 * Default constructor for the SpaceInvadersModel
	 * Initializes the game with a default board size of 23 rows by 26 columns
	 */
	public SpaceInvadersModel() {
		this(new SpaceInvadersBoard(23, 26));
	}
	
	/**
	 * Constructs a SpaceInvadersModel with a given board configuration
	 * Initializes the player ship, and creates empty lists for aliens and bullets
	 *
	 * @param board the game board defining grid dimensions and cell size
	 */
	public SpaceInvadersModel(SpaceInvadersBoard board) {
		this.board = board;
		this.ship = Ship.newShip(board);
		this.aliens = new ArrayList<>();
		this.bullets = new ArrayList<>();
	}
	
	/**
	 * Creates the red alien ship if it is not already alive
	 * The ship appears periodically while the game is active
	 */
	public void createRedShip() {
		if (!redAlienShip.isAlive()) {
			redAlienShip = redShip.newRedShip(board);
			redAlienShip.revive();
		}
	}
	
	/**
     * Creates the grid of alien enemies with specified rows and columns
     * Initializes each alien's type and score value based on its row
     *
     * @param rows the number of rows of aliens
     * @param cols the number of columns of aliens
     */
	public void createAlienGrid(int rows, int cols) {
		AlienType type;
		int scoreValue;
		
		double scaleFactor = 0.8;
		
	    int totalGridWidth = cols * board.cellSize();
	    int boardWidth = board.width();
	    
	    int x_start = (boardWidth - totalGridWidth) / 2;
	    int y_start = board.cellSize() * 8;
	    
	    int alienWidth = (int) (board.cellSize() * scaleFactor);
	    int alienHeight = (int) (board.cellSize() * scaleFactor);

	    for (int row = 0; row < rows; row++) {
	        for (int col = 0; col < cols; col++) {
	            int x = x_start + (col * board.cellSize()) + (board.cellSize() - alienWidth) / 2;
	            int y = y_start + (row * board.cellSize()) + (board.cellSize() - alienHeight) / 2;

	            if (row == 0) {
	                type = AlienType.SQUID;
	                scoreValue = 30;
	            } else if (row <= 2) {
	                type = AlienType.CRAB;
	                scoreValue = 20;
	            } else {
	                type = AlienType.BLOB;
	                scoreValue = 10;
	            }

	            Aliens alien = new Aliens(x, y, alienWidth, alienHeight, type, scoreValue);
	            
	            aliens.add(alien);
	        }
	    }
		alienCount = aliens.size();
	}

	/**
     * Checks if the given object has hit the horizontal edges of the board
     * with optional padding from the edge
     *
     * @param obj the game object to check
     * @param padding the space from the edges
     * @return true if object is at or beyond the edge, otherwise false
     */
	public boolean hitEdge(GameObject obj, int padding) {
	    int leftEdge = board.cellSize() * 4;
	    int rightEdge = board.width() - leftEdge;

	    return obj.getX() <= leftEdge + padding || 
	           obj.getX() + obj.getWidth() >= rightEdge - padding;
	}

	/**
	 * Checks whether any alive alien has collided with the player's ship
	 * or reached the bottom of the screen
	 *
	 * @return true if an alien hits the ship or reaches the bottom, false otherwise
	 */
	public boolean hitShipOrBottom() {
		for (Aliens alien : aliens) {
			if (alien.isAlive() && detectCollision(alien.getX(), alien.getY(), alien.getWidth(), alien.getHeight(),
								ship.getX(), ship.getY(), ship.getWidth(), ship.getHeight())) {
				return true;
				
			} else if (alien.getY() + alien.getHeight() >= ship.getY() + ship.getHeight()) {
			    return true;
			}
		}
		return false;
	}
	
	/**
	 * Creates and returns a bullet object fired by the player's ship if the cooldown allows
	 * The bullet is positioned at the top-center of the ship and added to the bullets list
	 * Resets the cooldown timer upon firing
	 *
	 * @return a new Bullet object if shooting is allowed, otherwise null
	 */
	public Bullet createBullet() {
		int bulletWidth = (board.cellSize() / 8) - 2;
		int bulletHeight = board.cellSize() / 2;
		
		if (canShoot()) {
			Bullet bullet = new Bullet(ship.getX() + ship.getWidth() * 15 / 32, ship.getY(), bulletWidth, bulletHeight);
			bullets.add(bullet);
			timeSinceLastShot = 0;
			
			return bullet;
		}
		return null;
	}
	
	/**
	 * Detects whether two rectangular game objects (defined by position and size) collide
	 *
	 * @param x1 the x-coordinate of the first object
	 * @param y1 the y-coordinate of the first object
	 * @param w1 the width of the first object
	 * @param h1 the height of the first object
	 * @param x2 the x-coordinate of the second object
	 * @param y2 the y-coordinate of the second object
	 * @param w2 the width of the second object
	 * @param h2 the height of the second object
	 * @return true if the two rectangles overlap (collision), false otherwise
	 */
	public boolean detectCollision(int x1, int y1, int w1, int h1,
            					   int x2, int y2, int w2, int h2) {
			return x1 < x2 + w2 &&
				   x1 + w1 > x2 &&
				   y1 < y2 + h2 &&
				   y1 + h1 > y2;
	}
	
	/**
     * Checks for, and processes collisions between a single bullet and all aliens including red ship
     * Updates score and marks bullet as used and aliens as dead
     *
     * @param bullet the bullet to check for collisions
     */
	private void checkBulletCollision(Bullet bullet) {
	    for (Aliens alien : aliens) {
	        if (!bullet.isUsed() && alien.isAlive() &&
	            detectCollision(alien.getX(), alien.getY(), alien.getWidth(), alien.getHeight(),
	                            bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight())) {
	            
	            bullet.remove();
	            alien.kill();
	            addScore(alien.getScoreValue());
	            alienCount--;
	            return;
	        }
	    }

	    if (!bullet.isUsed() && redAlienShip != null && redAlienShip.isAlive() &&
	        detectCollision(redAlienShip.getX(), redAlienShip.getY(), redAlienShip.getWidth(), redAlienShip.getHeight(),
	                        bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight())) {

	        bullet.remove();
	        redAlienShip.kill();
	        addScore(redAlienShip.getScoreValue());
	    }
	}
	
	/**
     * Moves all bullets upwards and checks for collisions with aliens or red ship
     * Also removes bullets that have left the screen or are marked as used
     */
	public void moveBullets() {
		for(Bullet bullet : bullets) {
			bullet.update();
			checkBulletCollision(bullet);
		}	
		while (bullets.size() > 0 && (bullets.get(0).isUsed() || bullets.get(0).getY() < 0)) {
			bullets.remove(0);
		}
	}
	
	/**
	 * Moves the red alien ship if it is alive
	 * Reverses direction if it hits the board edges
	 */
	public void moveRedShip() {
	    if (redAlienShip.isAlive()) {
	        if (!hitEdge(redAlienShip, 20)) {
	            redAlienShip.moveAlien();
	        } else {
	            redAlienShip.reverseDirection();
	            redAlienShip.moveAlien();
	        }
	    }
	}
	
	/**
	 * Moves all alive aliens in their current direction
	 * If any alien hits the edge, triggers them all to drop down and reverse direction on next tick
	 */
	public void moveAliens() {
		
		if (shouldDrop) {
			for (Aliens alien: aliens) {
				alien.dropDown();
				alien.reverseDirection();
			}
			shouldDrop = false;
			return;
		}
		
		boolean edgeHit = false;
		
		for (Aliens alien : aliens) {
			if (alien.isAlive()) {
				alien.moveAlien();
				if (hitEdge(alien, 20)) {
					edgeHit = true;
				}
			}
		}
		if (edgeHit) shouldDrop = true;
	}
	
	/**
	 * Checks if the player ship is allowed to shoot based on its cooldown
	 *
	 * @return true if the player ship can shoot, false otherwise
	 */
	public boolean canShoot() {
		return timeSinceLastShot >= shootCoolDown;
	}
	
	/**
	 * Increases the current score by a given number of points
	 *
	 * @param points the number of points to add to the score
	 */
	public void addScore(int points) {
		score += points;
	}
	
	@Override
	public boolean moveShipLeft() {
	    Ship shifted = ship.shiftedBy(-ship.getSpeed(), 0);
	    if (!hitEdge(shifted, 10)) {
	        ship = shifted;
	        return true;
	    }
	    return false;
	}
	
	@Override
	public boolean moveShipRight() {
	    Ship shifted = ship.shiftedBy(ship.getSpeed(), 0);
	    if (!hitEdge(shifted, 10)) {
	        ship = shifted;
	        return true;
	    }
	    return false;
	}

	@Override
	public GridDimension getDimension() {
		return board;
	}
	
	@Override
	public GameState getGameState() {
		return state;
	}
	
	@Override
	public Ship getShip() {
		return ship;
	}
	
	@Override
	public redShip getRedShip() {
		return redAlienShip;
	}
	
	@Override
	public List<Aliens> getAliens() {
		return aliens;
	}
	
	@Override
	public List<Bullet> getBullets() {
		return bullets;
	}
	
	@Override
	public boolean getAlienToggle() {
		return alienToggle;
	}
	
	@Override
	public int getAlienInterval() {
		return ALIEN_MOVE_INTERVAL;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getLevel() {
		return this.level;
	}
	
	@Override
	public int getHighScore() {
		return hiScore;
	}
	
	@Override
	public int getTickCounter() {
		return tickCounter;
	}
	
	@Override
	public int getTimeInterval() {
		return (int) (10 * Math.pow(0.9, getLevel()));
	}

	/**
     * Called every game tick to update the game logic if the game is active.
     * Handles all main gameplay tasks such as movement, collisions, spawning, etc.
     */
	@Override
	public void clockTick() {
	
		if (isGameActive()) {
			checkEndConditions();
			handleAliens();
			handleRedAlienShip();
			moveBullets();
		
			timeSinceLastShot++;
		}
		tickCounter++;
	}
	
	/**
     * Checks if the game is currently running
     */
	private boolean isGameActive() {
		return state == GameState.ACTIVE_GAME;
	}
	
	/**
     * Called every tick to update aliens: movement, toggling animation, etc.
     * Aliens move at a decreasing interval as the level increases / they move faster for each level
     */
	private void handleAliens() {
		for (Aliens alien : aliens) {
			if (!alien.isAlive()) alien.update();
		}
		
		int moveInterval = Math.max(1, ALIEN_MOVE_INTERVAL - 5 * level);
		
		if (tickCounter % moveInterval == 0) {
			moveAliens();
			alienToggle = !alienToggle;
		}
	}
	
	/**
     * Called every tick to update the red alien ship's behavior
     * Handles spawning, movement, and auto-killing after a duration
     */
	private void handleRedAlienShip() {
		if (tickCounter % REDSHIP_SPAWN_INTERVAL == 0) createRedShip();
		if (redAlienShip.isAlive() && tickCounter % REDSHIP_KILL_INTERVAL == 0) redAlienShip.kill();
		if (tickCounter % REDSHIP_MOVE_INTERVAL == 0) moveRedShip();
	}
	
	/**
     * Proceeds the game to the next level after all aliens are eliminated
     * Creates a new alien grid and empties all lists to save memory
     */
	public void nextLevel() {
		level++;
		aliens.clear();
		createAlienGrid(5, 11);
		bullets.clear();
	}
	
	/**
     * Checks whether the game should transition to game over or move to the next level.
     */
	private void checkEndConditions() {
	    if (hitShipOrBottom()) setGameOver();
	    if (alienCount == 0) nextLevel();
	}

	/**
     * Starts a new game session by resetting state and setting the game to active
     */
	public void startGame() {
		resetGameState();
		state = GameState.ACTIVE_GAME;
	}
	
	/**
	 * Kills the game and sets the game state to GAME_OVER.
	 */
	public void killGame() {
		state = GameState.GAME_OVER;
	}

	/**
     * Resets all game variables to default, clearing aliens and bullets,
     * reinitializing the ship and red ship, and resetting level/score
     */
	public void restartGame() {
		resetGameState();
		state = GameState.ACTIVE_GAME;
	}
	
	/**
	 * Resets all game state variables including score, level, alien count,
	 * tick counter, red ship, and bullets and prepares the game for a fresh start
	 */
	public void resetGameState() {
	    score = 0;
	    level = 1;
	    tickCounter = 0;
	    alienCount = 0;
	    timeSinceLastShot = shootCoolDown;
	    alienToggle = false;
	    shouldDrop = false;
	    bullets.clear();
	    aliens.clear();
	    createAlienGrid(5, 11);
	    ship = Ship.newShip(board);
	    redAlienShip = redShip.newRedShip(board);
	    redAlienShip.kill();
	    
	    gameOverHandled = false;
	}
	
	/**
	 * Transitions the game to GAME_OVER state and updates high score
	 * if the current score exceeds the previous high score
	 */
	public void setGameOver() {
		state = GameState.GAME_OVER;
		if (!gameOverHandled) {
	        if (score > hiScore) {
	            hiScore = score;
	        }
		}
		gameOverHandled = true;
	}
}
