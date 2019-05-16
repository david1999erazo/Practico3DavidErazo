package gui;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import control.*;
import model.*;

public class Main_Agario extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final int WINDOW_WIDTH = 1400;
	public static final int WINDOW_HEIGHT = 800;
	public static final int WINDOW_POS_X = 50;
	public static final int WINDOW_POS_Y = 50;

	private Login_GUI loginWindow;
	private Registrer registrerWindow;
	private Space space;
	private Controller controller;
	private ThreadRepaint repaint;

	public Main_Agario() {
		controller = new Controller(this);
		this.loginWindow = new Login_GUI(this);
		this.loginWindow.setVisible(true);
		this.space = new Space(controller.getGame(), new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
	}
	
	public void play() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(false);
		this.setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setResizable(false);
		this.setFocusable(true);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		controller.startGame();
		paintGame(controller.getAvatar(),controller.getGame().getFood());
	}

	private void paintGame(Avatar avatar, ArrayList<Avatar> food) {
		// Add player with socket
		this.space.setFocusable(false);
		this.setIgnoreRepaint(false);
		this.add((Component) this.space);
		
		repaint= new ThreadRepaint(space);
		repaint.start();
	}


	@Override
	public void paint(Graphics g) {
		if (this.space != null) {
			this.space.paint(g);
		}
	}

	public void openWindowRegistrer() {
		registrerWindow = new Registrer(this);
		registrerWindow.setVisible(true);

	}

	public void closeRegistre() {
		registrerWindow.setVisible(false);
	}

	public int askPort(String request) {
		return controller.answerPort(request);
	}
	
	
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public static void main(String[] args) {
		Main_Agario m = new Main_Agario();
		m.setVisible(false);
	}
	
	public void showMessage(String message) {
		this.space.setMessage(message);
	}
	
	public Space getSpace() {
		return space;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

	public ThreadRepaint getRepaint() {
		return repaint;
	}

	public void setRepaint(ThreadRepaint repaint) {
		this.repaint = repaint;
	}
	
}