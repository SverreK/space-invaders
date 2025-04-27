package SpaceInvadersModelTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import GameModel.Ship;
import GameModel.SpaceInvadersBoard;
import GameModel.SpaceInvadersModel;

public class ShipTest {
	
	SpaceInvadersModel model = new SpaceInvadersModel();
	SpaceInvadersBoard board = new SpaceInvadersBoard(23,26);
	Ship ship = model.getShip();
	
	@Test
	public void shipPositionTest() {
		int start_x = (board.width()/2) - (ship.getWidth()/2);
		int start_y = board.cellSize() * (board.rows() - 6);
		assertEquals(ship.getX(), start_x);
		assertEquals(ship.getY(), start_y);
	}
	
	@Test
	public void shipMovesRightTest() {
	     int start_x = ship.getX();
	     assertTrue(model.moveShipRight());
	     assertTrue(model.getShip().getX() == start_x + ship.getSpeed());
	    }
	 
	@Test
	public void shipMovesLeftTest() {
		 model.moveShipRight();
	     int start_x = model.getShip().getX();
	     assertTrue(model.moveShipLeft());
	     assertTrue(model.getShip().getX() == start_x - ship.getSpeed());
	    }
	 
	   @Test
	    public void shipShiftedByTest() {
	       
	        Ship shiftedRight = ship.shiftedBy(10, 0);
	        assertEquals(ship.getX() + 10, shiftedRight.getX());
	        assertEquals(ship.getY(), shiftedRight.getY());
	        
	        Ship shiftedLeft = ship.shiftedBy(-5, 0);
	        assertEquals(ship.getX() - 5, shiftedLeft.getX());
	        assertEquals(ship.getY(), shiftedLeft.getY());
	 
	        Ship shiftedUp = ship.shiftedBy(0, -3);
	        assertEquals(ship.getX(), shiftedUp.getX());
	        assertEquals(ship.getY() - 3, shiftedUp.getY());
	        
	        Ship shiftedBoth = ship.shiftedBy(7, -2);
	        assertEquals(ship.getX() + 7, shiftedBoth.getX());
	        assertEquals(ship.getY() - 2, shiftedBoth.getY());
	    }
	 
	@Test
	public void shipMaintainedDimensionsTest() {
		 Ship shifted = ship.shiftedBy(5, 5);
		 assertEquals(ship.getWidth(), shifted.getWidth());
	     assertEquals(ship.getHeight(), shifted.getHeight());
	    }
		
	@Test
	public void correctShipHeightTest() {
		assertEquals(board.cellSize(), ship.getHeight());
	}
	
	@Test
	public void correctShipSpeedTest() {
		assertEquals(12, ship.getSpeed());
	}


}
