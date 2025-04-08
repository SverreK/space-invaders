package GameModel.Aliens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlienFactory {

	private static final int START_X = 100;
	private static final int START_Y = 50;
	private static final int ALIEN_WIDTH = 30;
	private static final int ALIEN_HEIGHT = 20;
	private static final int PADDING = 10;

	public static List<Aliens> createAlienGrid(int rows, int cols) {
	        List<Aliens> aliens = new ArrayList<>();

	        for (int row = 0; row < rows; row++) {
	            for (int col = 0; col < cols; col++) {
	                int x = START_X + col * (ALIEN_WIDTH + PADDING);
	                int y = START_Y + row * (ALIEN_HEIGHT + PADDING);

	                // Valg av type alien, f.eks basert på rad eller tilfeldig
	                Aliens alien = switch (row) {
	                    case 0 -> new AlienTop(x, y, ALIEN_WIDTH, ALIEN_HEIGHT);
	                    case 1 -> new AlienMiddle(x, y, ALIEN_WIDTH, ALIEN_HEIGHT);
	                    default -> new AlienBottom(x, y, ALIEN_WIDTH, ALIEN_HEIGHT);
	                };

	                aliens.add(alien);
	            }
	        }

	        return aliens;
	    }
	}


}
