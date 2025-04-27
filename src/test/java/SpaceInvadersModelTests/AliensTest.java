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

public class AliensTest {
	
	SpaceInvadersModel model = new SpaceInvadersModel();
	SpaceInvadersBoard board = new SpaceInvadersBoard(23, 26);
	
	@BeforeEach
    public void setUp() {
        board = new SpaceInvadersBoard(23, 26);
        model = new SpaceInvadersModel();
        model.createAlienGrid(5, 11);
    }
	
	@Test
    public void alienGridSizeTest() {
        assertEquals(55, model.getAliens().size());
    }
	
	@Test
    public void aliensMoveTest() {
        Aliens alien = model.getAliens().get(0);
        int start_x = alien.getX();
   
        alien.moveAlien();
        assertEquals(start_x + 12, alien.getX());
        
        alien.reverseDirection();
        alien.moveAlien();
        assertEquals(start_x, alien.getX());
    }
	
	@Test
    public void alienDropDownTest() {
        Aliens alien = model.getAliens().get(0);
        int start_y = alien.getY();
        
        alien.dropDown();
        assertEquals(start_y + 10, alien.getY());
    }

	@Test
	public void alienGridTypeTest() {
		for (int i = 0 ; i < 11 ; i++) {
			Aliens alien = model.getAliens().get(i);
			int row = i / 11;
			
			if (row == 0) {
				assertEquals(alien.getAlienType(), AlienType.SQUID);
			} else if (row <= 2) {
				assertEquals(alien.getAlienType(), AlienType.CRAB);
			} else if (row <= 5) {
				assertEquals(alien.getAlienType(), AlienType.BLOB);
			}
		}	
	}
	
	@Test
    public void alienGridScoreValueTest() {
        for (Aliens alien : model.getAliens()) {
            switch (alien.getAlienType()) {
                case SQUID -> assertEquals(30, alien.getScoreValue());
                case CRAB -> assertEquals(20, alien.getScoreValue());
                case BLOB -> assertEquals(10, alien.getScoreValue());
            }
        }
    }
	
	@Test
    public void alienKillTest() {
        Aliens alien = model.getAliens().get(0);
        alien.kill();
        assertFalse(alien.isAlive());
        assertEquals(0, alien.getDeathTicks());
    }
	
	@Test
    public void aliensUpdateTest() {
        Aliens alien = model.getAliens().get(0);
        alien.kill();
        
        alien.update();
        assertEquals(1, alien.getDeathTicks());
        
        alien.update();
        assertEquals(2, alien.getDeathTicks());
    }
	
	@Test
	public void aliensAliveTest() {
	   Aliens alien = model.getAliens().get(1);
	   assertTrue(alien.isAlive());
	        
	   alien.update();
	   assertEquals(0, alien.getDeathTicks());
	}
}
