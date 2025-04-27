package GameView;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameModel.GameState;
import GameModel.Bullet;
import GameModel.Ship;
import GameModel.Aliens.Aliens;
import GameModel.Aliens.redShip;
import GameModel.Interfaces.GridDimension;

public class SpaceInvadersView extends JPanel {
	
	private ViewableSpaceInvadersModel model;
	Dimension preferredSize;
	
	public SpaceInvadersView(ViewableSpaceInvadersModel model) {
		this.model = model;
		this.preferredSize = getDefaultSize(model.getDimension());
		
	    setPreferredSize(preferredSize);
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;
	    
	    drawGameFrame(g2);
	    //drawGrid(g2);

	    if (model.getGameState() == GameState.TITLE_SCREEN) {
	    	drawTitleScreen(g2);
		} else if (model.getGameState() == GameState.GAME_OVER) {
	       drawGameOver(g2);
	    } else {
	        drawGameObjects(g2);
	    }
    }
	
	 private static Dimension getDefaultSize(GridDimension gd) {
	     int width = (int) (gd.cellSize() * gd.cols());
	     int height = (int) (gd.cellSize() * gd.rows());
	     return new Dimension(width, height);
	    }
	   
	 //GAME ARCADE FRAME
	 private void drawGameFrame(Graphics g2) {
		 g2.drawImage(SpriteLoader.get("board_frame"), -1, 0, preferredSize.width, preferredSize.height, null);
	 }
	 
	 //GAME STATES
	 private void drawTitleScreen(Graphics2D g2) {
		 float font_size_title = 24f;
		 float font_size_start_title = 12f;
		 
		 //Draw title
		 g2.setColor(Color.GREEN);
		 FontLoader.setFont(g2, "src/main/resources/fonts/PressStart2P-Regular.ttf", font_size_title);
		 Inf101Graphics.drawCenteredString(g2, "SPACE INVADERS", preferredSize.width/2, model.getDimension().cellSize() * 10 );
		 
		 //Draw start text
		 FontLoader.setFont(g2, "src/main/resources/fonts/PressStart2P-Regular.ttf", font_size_start_title);
		 
		 flimmerText(g2, "PRESS ENTER TO START", 40, preferredSize.width/2, model.getDimension().cellSize() * 14);
	 }
	 
	 private void drawGameOver(Graphics2D g2) {
		 
		 //GAME OVER
		 g2.setColor(Color.GREEN);
		 FontLoader.setFont(g2, "src/main/resources/fonts/PressStart2P-Regular.ttf", 32f);
		 Inf101Graphics.drawCenteredString(g2, "GAME OVER", getBounds());
		 //YOUR SCORE
		 g2.setColor(Color.WHITE);
		 FontLoader.setFont(g2, "src/main/resources/fonts/PressStart2P-Regular.ttf", 16f);
		 Inf101Graphics.drawCenteredString(g2, "Your score: " + model.getScore(), preferredSize.width/2, model.getDimension().cellSize() * 13);
	     //DO YOU WANT TO PLAY AGAIN?
		 FontLoader.setFont(g2, "src/main/resources/fonts/PressStart2P-Regular.ttf", 12f);
		 flimmerText(g2, "Do you want to play again(y/n)?", 40, preferredSize.width/2, model.getDimension().cellSize() * 15);
	       
	 	}
	 
	 //GUI
	 public void drawTopGUI(Graphics2D g2) {
		 float gui_font_size = 24f;
		 float gui_score_font_size = 20f;
		 
		 //Draw GUI titles
		 FontLoader.setFont(g2, "src/main/resources/fonts/CosmicAlien-V4Ax.ttf", gui_font_size);
		 g2.setColor(Color.WHITE);
		 g2.drawString("SCORE", model.getDimension().cellSize() * 5, model.getDimension().cellSize() * 5);
		 g2.drawString("HI-SCORE", model.getDimension().cellSize() * 10, model.getDimension().cellSize() * 5);
		 g2.drawString("LEVEL", model.getDimension().cellSize() * 17, model.getDimension().cellSize() * 5);
		 
		 //Draw score
		 FontLoader.setFont(g2, "src/main/resources/fonts/Pixel LCD-7.ttf", gui_score_font_size);
		 String score = String.valueOf(model.getScore());
		 String level = String.valueOf(model.getLevel());
		 String hiScore = String.valueOf(model.getHighScore());
		 
		 g2.drawString(score, model.getDimension().cellSize() * 5, model.getDimension().cellSize() * 6);
		 g2.drawString(hiScore, model.getDimension().cellSize() * 10, model.getDimension().cellSize() * 6);
		 g2.drawString(level, model.getDimension().cellSize() * 17, model.getDimension().cellSize() * 6);
	 } 
	 
	 //GAME OBJECTS
	 private void drawShip(Graphics2D g2) {
		 Ship ship = model.getShip();
		 g2.drawImage(SpriteLoader.get("ship"), ship.getX(), ship.getY(), ship.getWidth(), ship.getHeight(), null);

	 }
	 
	 private void drawRedAlienShip(Graphics g2) {
		 redShip redAlienShip = model.getRedShip();
		 
		 if (redAlienShip.isAlive()) {
			 g2.drawImage(SpriteLoader.get("red_ship"), redAlienShip.getX(), redAlienShip.getY(), redAlienShip.getWidth(), redAlienShip.getHeight(), null);
		 }
	 }
	 
	 private void drawAliveAlien(Graphics2D g2, Aliens alien) {
		    BufferedImage sprite;
		    
		    boolean toggle = model.getAlienToggle();
		    switch (alien.getAlienType()) {
		        case SQUID -> sprite = SpriteLoader.get(toggle ? "squid" : "squid2");
		        case CRAB  -> sprite = SpriteLoader.get(toggle ? "crab" : "crab2");
		        case BLOB  -> sprite = SpriteLoader.get(toggle ? "blob" : "blob2");
		        default    -> sprite = SpriteLoader.get("blob");
		    }

		    g2.drawImage(sprite, alien.getX(), alien.getY(), alien.getWidth(), alien.getHeight(), null);		 
	 }
	 
	 private void drawHitEffect(Graphics2D g2, Aliens alien) {
		    double scaleFactor = 0.8;
		    g2.drawImage(SpriteLoader.get("alien_hit"),
		        (int) alien.getX(),
		        (int) alien.getY(),
		        (int) (alien.getWidth() * scaleFactor),
		        (int) (alien.getHeight() * scaleFactor),
		        null);
		}

	 private void drawAliens(Graphics2D g2) {
		    for (Aliens alien : model.getAliens()) {
		        if (alien.isAlive()) {
		            drawAliveAlien(g2, alien);
		        } else if (alien.getDeathTicks() <= 2) {
		            drawHitEffect(g2, alien);
		        }
		    }
		}

	 
	 private void drawBullets(Graphics2D g2) {
		 g2.setColor(Color.WHITE);
		 for (int i = 0; i < model.getBullets().size(); i++) {
			 Bullet bullet = model.getBullets().get(i);
			 if (!bullet.isUsed()) {
				 g2.drawRect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
			 }
		 }
	 }
	 
	 private void drawGameObjects(Graphics2D g2) {
		 drawTopGUI(g2);
		 drawShip(g2);
		 drawRedAlienShip(g2);
		 drawAliens(g2);
		 drawBullets(g2);
	 }
	 
	 //EXTRA
	 private void flimmerText(Graphics2D g2, String text, int flimmerRate, int x, int y) {
		 if ((model.getTickCounter() / flimmerRate) % 2 == 0) {
			    g2.setColor(Color.BLACK);
			} else {
			    g2.setColor(Color.GREEN);
			}
		 Inf101Graphics.drawCenteredString(g2, text, x, y);
		 
	 }
}
