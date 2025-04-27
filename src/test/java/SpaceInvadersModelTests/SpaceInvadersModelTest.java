package SpaceInvadersModelTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GameModel.Bullet;
import GameModel.GameState;
import GameModel.Ship;
import GameModel.SpaceInvadersBoard;
import GameModel.SpaceInvadersModel;
import GameModel.Aliens.Aliens;
import GameModel.Interfaces.GridDimension;

class SpaceInvadersModelTest {

    private SpaceInvadersModel model;
    private SpaceInvadersBoard board;
    private Ship ship;

    @BeforeEach
    void setUp() {
        board = new SpaceInvadersBoard(23, 26); 
        ship = Ship.newShip(board); 
        model = new SpaceInvadersModel(board);
    }

    @Test
    void testCreateAlienGrid() {
        model.createAlienGrid(5, 11);
        List<Aliens> aliens = model.getAliens();
        assertEquals(55, aliens.size());
    }

    @Test
    void testCanShootInitially() {
        assertTrue(model.canShoot());
    }

    @Test
    void testCreateBulletWhenAbleToShoot() {
        Bullet bullet = model.createBullet();
        assertNotNull(bullet);
        assertEquals(1, model.getBullets().size());
    }

    @Test
    void testHitEdgeLeft() {
        Ship ship = model.getShip();
        Ship shifted = ship.shiftedBy(-1000, 0);
        assertTrue(model.hitEdge(shifted, 10));
    }

    @Test
    void testHitEdgeRight() {
        Ship ship = model.getShip();
        Ship shifted = ship.shiftedBy(10000, 0);
        assertTrue(model.hitEdge(shifted, 10));
    }
    
    @Test
    public void testNextLevel() {
        model.createAlienGrid(5, 11); 
        assertEquals(1, model.getLevel());
        model.nextLevel();
        assertEquals(2, model.getLevel());
        assertEquals(55, model.getAliens().size());
    }
    
    @Test
    public void testRestartGame() {
        model.createAlienGrid(5, 11);  
        model.addScore(100);  
        model.nextLevel(); 
        assertEquals(2, model.getLevel());
        assertEquals(100, model.getScore());
        model.restartGame();
        assertEquals(1, model.getLevel());
        assertEquals(0, model.getScore());
        assertEquals(55, model.getAliens().size());
    }

    @Test
    void testRedShipSpawning() {
        model.startGame();
        model.clockTick();
        for (int i = 0; i < 500; i++) {
            model.clockTick();
        }
        assertNotNull(model.getRedShip());
        assertTrue(model.getRedShip().isAlive());
    }

    @Test
    void testStartGameResetsState() {
        model.startGame();
        assertEquals(GameState.ACTIVE_GAME, model.getGameState());
    }

    @Test
    void testAddScore() {
        int initialScore = model.getScore();
        model.addScore(50);
        assertEquals(initialScore + 50, model.getScore());
    }

    @Test
    void testGetAliens() {
        model.createAlienGrid(1, 1);
        List<Aliens> aliens = model.getAliens();
        assertNotNull(aliens);
        assertEquals(1, aliens.size());
    }

    @Test
    void testGetBullets() {
        Bullet bullet = model.createBullet();
        assertNotNull(bullet);
        List<Bullet> bullets = model.getBullets();
        assertNotNull(bullets);
        assertTrue(bullets.contains(bullet));
    }

    @Test
    void testGetAlienInterval() {
        int interval = model.getAlienInterval();
        assertEquals(50, interval);
    }

    @Test
    void testGetScore() {
        int initialScore = model.getScore();
        model.addScore(10);
        int updatedScore = model.getScore();
        assertTrue(updatedScore > initialScore);
    }

    @Test
    void testGetLevel() {
        int level = model.getLevel();
        assertEquals(1, level);
    }

    @Test
    void testGetHighScore() {
        int hiScore = model.getHighScore();
        assertEquals(0, hiScore);
        model.addScore(100);
        model.setGameOver();
        int updatedHiScore = model.getHighScore();
        assertEquals(100, updatedHiScore);
    }
    
    @Test
    public void testDetectCollision() {
        SpaceInvadersBoard board = new SpaceInvadersBoard(23, 26);
        SpaceInvadersModel model = new SpaceInvadersModel(board);
        
        int shipX = 10;
        int shipY = 20;
        int shipWidth = 5;
        int shipHeight = 2;
        
        int alienX = 12;
        int alienY = 20;
        int alienWidth = 5;
        int alienHeight = 2;

        boolean collisionDetected = model.detectCollision(shipX, shipY, shipWidth, shipHeight,
                                                          alienX, alienY, alienWidth, alienHeight);
        assertTrue(collisionDetected);
        
        alienX = 20;
        collisionDetected = model.detectCollision(shipX, shipY, shipWidth, shipHeight,
                                                  alienX, alienY, alienWidth, alienHeight);
        assertFalse(collisionDetected);
    }

    @Test
    void testGetTickCounter() {
        int initialTick = model.getTickCounter();
        model.clockTick();
        int updatedTick = model.getTickCounter();
        assertTrue(updatedTick > initialTick);
    }

    @Test
    void testGetTimeInterval() {
        int timeInterval = model.getTimeInterval();
        assertTrue(timeInterval > 0);
    }

    @Test
    void testGetDimension() {
        GridDimension dimension = model.getDimension();
        assertNotNull(dimension);
    }

    @Test
    void testGetGameState() {
        GameState gameState = model.getGameState();
        assertEquals(GameState.TITLE_SCREEN, gameState);
    }
}
