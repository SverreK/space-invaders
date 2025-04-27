package SpaceInvadersModelTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GameModel.SpaceInvadersBoard;
import GameModel.SpaceInvadersModel;
import GameModel.Aliens.AlienType;
import GameModel.Aliens.Aliens;
import GameModel.Aliens.redShip;

public class RedAlienShipTest {
	
	SpaceInvadersModel model;
	SpaceInvadersBoard board;
	redShip redAlienShip;
	
	@BeforeEach
    public void setUp() {
		model = new SpaceInvadersModel();
        board = new SpaceInvadersBoard(23, 26);
        redAlienShip = redShip.newRedShip(board);
    }
	
	@Test
    public void redAlienShipPositionTest() {
	    double scaleFactor = 0.7;

	    int unscaledWidth = board.cellSize() * 2;
	    int unscaledHeight = board.cellSize();

	    int width = (int) (unscaledWidth * scaleFactor);
	    int height = (int) (unscaledHeight * scaleFactor);

	    int centerX = (board.cellSize() * board.cols()) / 2;
	    int x = centerX - (width / 2);

	    int y = board.cellSize() * 7 + (unscaledHeight - height) / 2;

        assertEquals(x, redAlienShip.getX());
        assertEquals(y, redAlienShip.getY());
        assertEquals(width, redAlienShip.getWidth());
        assertEquals(height, redAlienShip.getHeight());
    }
	
	@Test
    public void redAlienShipMoveTest() {
        int start_x = redAlienShip.getX();
   
        redAlienShip.moveAlien();
        assertEquals(start_x + 20, redAlienShip.getX());
        
        redAlienShip.reverseDirection();
        redAlienShip.moveAlien();
        assertEquals(start_x, redAlienShip.getX());
    }

	@Test
    public void redAlienShipScoreTest() {
		assertEquals(250, redAlienShip.getScoreValue());
    }
	
	@Test
    public void redAlienShipKillTest() {
        redAlienShip.revive();
        redAlienShip.kill();
        assertFalse( redAlienShip.isAlive());
        assertEquals(0,  redAlienShip.getDeathTicks());
    }
	
	@Test
    public void redAlienShipUpdateTest() {
		redAlienShip.revive();
        redAlienShip.kill();
        
        redAlienShip.update();
        assertEquals(1, redAlienShip.getDeathTicks());
        
        redAlienShip.update();
        assertEquals(2, redAlienShip.getDeathTicks());
    }
	
	@Test
	public void redAlienShipAliveTest() {
		redAlienShip.revive();
	    assertTrue(redAlienShip.isAlive());
	        
	    redAlienShip.update();
	    assertEquals(0, redAlienShip.getDeathTicks());
	}
	
	@Test
    public void getTypeTest() {
        assertEquals(AlienType.REDSHIP, redAlienShip.getAlienType());
    }
	
}

