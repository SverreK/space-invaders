package no.uib.inf101.spaceinvaders;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import GameController.SpaceInvadersController;
import GameModel.SpaceInvadersModel;
import GameView.SpaceInvadersView;

public class Main {
	
	public static final String WINDOW_TITLE = "SpaceInvaders";

	public static void main(String[] args) {
		
		SpaceInvadersModel model = new SpaceInvadersModel();
		SpaceInvadersView view = new SpaceInvadersView(model);
		new SpaceInvadersController(model, view);
		
		JFrame frame = new JFrame(WINDOW_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(view);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		// Open GUI in the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
		
		
		
	}
}
