package SpaceInvadersModelTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import GameModel.Bullet;
import GameModel.SpaceInvadersBoard;
import GameModel.SpaceInvadersModel;
import GameModel.Aliens.AlienType;
import GameModel.Aliens.Aliens;
import GameModel.Aliens.redShip;

public class BulletsTest {
	
	SpaceInvadersModel model = new SpaceInvadersModel();
	SpaceInvadersBoard board = new SpaceInvadersBoard(23,26);
	Bullet bullet = model.createBullet();
	
	@Test
	public void createBulletTest() {
		int width = (board.cellSize() / 8) - 2;
		int height = board.cellSize() / 2;
	    assertTrue(model.getBullets().contains(bullet));
	    assertEquals(width, bullet.getWidth());
	    assertEquals(height, bullet.getHeight());
	}
	
	@Test
	public void bulletMovesUpwardsTest() {
		int start_y = bullet.getY();
		
		model.moveBullets();
		assertTrue(bullet.getY() < start_y);
	}
	
	@Test
	public void bulletsOutOfBoundRemovedTest() {
		for (int i = 0; i < 100; i++) {
			model.moveBullets();
		}
		
		assertFalse(model.getBullets().contains(bullet));
	}
	
	@Test
	public void bulletKillTest() {
	    model.getAliens().clear();
	    Aliens alien = new Aliens(100, 100, 20, 20, AlienType.BLOB, 10);
	    model.getAliens().add(alien);

	    Bullet bullet = new Bullet(105, 119, 5, 20);
	    model.getBullets().add(bullet);

	    model.moveBullets();

	    assertFalse(alien.isAlive());
	    assertTrue(bullet.isUsed());
	}

}
