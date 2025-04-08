package GameController;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import GameController.Interfaces.MoveableSpaceInvadersModel;
import GameModel.SpaceInvadersModel;
import GameView.SpaceInvadersView;
import GameModel.GameState;

public class SpaceInvadersController implements KeyListener {
	
	private SpaceInvadersModel model;
	private SpaceInvadersView view;
	
	  private Timer timer;
	
	public SpaceInvadersController(SpaceInvadersModel model, SpaceInvadersView view) {
		this.model = model;
        this.view = view;
        view.setFocusable(true);
        view.addKeyListener(this);
        this.timer = new Timer(model.getTimeInterval(), this::clockTick);
        timer.start();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (model.getGameState() == GameState.ACTIVE_GAME) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> model.moveShip(-10, 0);
                case KeyEvent.VK_RIGHT -> model.moveShip(10, 0);
                
                }
        }
        view.repaint();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void clockTick(ActionEvent e) {
        if (model.getGameState() == GameState.ACTIVE_GAME) {
            model.clockTick();
            updateTimeDelay();
            view.repaint();
        }
    }

    private void updateTimeDelay() {
        int delay = model.getTimeInterval();
        timer.setDelay(delay);
        timer.setInitialDelay(delay);
    }

}
