package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import model.Avatar;
import model.Game;

public class Space extends Canvas {

	private Dimension dimPanel; // add private final
	private Game game;
	private Graphics gra;
	private String message;

	public Space(Game game, Dimension d) {

		this.message= "";
		this.game = game;
		this.setSize(d);
		this.dimPanel = d;
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void paint(Graphics g) {
		gra = g;
		// Paint background
		g.setColor(new Color(220, 220, 220));
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Main_Agario.WINDOW_WIDTH, Main_Agario.WINDOW_HEIGHT);
		g.setColor(new Color(220, 220, 220));

		int espacio = 40;
		for (int i = 0; i < dimPanel.width; i += espacio)
			g.drawLine(i, 0, i, dimPanel.height);
		for (int j = 0; j < dimPanel.height; j += espacio)
			g.drawLine(0, j, dimPanel.width, j);

		// Paint Avatars

		if (game.getAvatars() != null) {
			try {
				this.paintPlayer(game.getAvatars(), g);
			} catch (Exception e) {
				Logger.getLogger(Space.class.getName()).log(Level.SEVERE, null, e);

			}
		}

		if (game.getFood() == null) {
			System.out.println("food null");
		}

		// Paint Food
		if (game.getFood() != null) {
			try {
				this.paintFood(game.getFood(), g);
			} catch (Exception e) {
				Logger.getLogger(Space.class.getName()).log(Level.SEVERE, null, e);
			}
		}

		// Paint Leader board
		paintLeaderBoard(game.getTop(), g);
		
		// Paint message
		paintMessege();

	}

	public void paintPlayer(ArrayList<Avatar> avatars, Graphics g) throws RemoteException {

		for (int i = 0; i < avatars.size(); i++) {
			try {
				Avatar a = avatars.get(i);
				render(g, 1, a);

				double x = a.getPosX();
				double y = a.getPosY();
				double r = a.getRadious();
				g.setFont(new Font("Ubuntu", Font.BOLD, (int) r / 2));
				FontMetrics metrics = g.getFontMetrics(g.getFont());
				int xt = (int) x - metrics.stringWidth(a.getNickName()) / 2;
				int yt = (int) (y + r / 4);
				g.drawString(a.getNickName(), xt, yt);

			} catch (Exception e) {
				System.out.print("Problem in paintPlayer");
				e.printStackTrace();
			}
		}
	}

	public void paintFood(ArrayList<Avatar> food, Graphics g) throws RemoteException {
		for (int i = 0; i < food.size(); i++) {
			try {
				Avatar f = food.get(i);
				render(g, 1, f);

			} catch (Exception e) {
				System.out.print("Problem in paintFood");
				e.printStackTrace();
			}
		}
	}

	public static void render(Graphics g, double scale, Avatar a) {

		double r = a.getRadious();
		g.setColor(a.getColor());
		g.fillOval((int) (a.getPosX() - r), (int) (a.getPosY() - r), (int) (2 * r), (int) (2 * r));
		g.setColor(Color.BLACK);
		g.drawOval((int) (a.getPosX() - r), (int) (a.getPosY() - r), (int) (2 * r), (int) (2 * r));
	}

	private void paintLeaderBoard(ArrayList<Avatar> top, Graphics g) {

		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("Ubuntu", Font.BOLD, 15));
		g.drawString("LEADERBOARD", (int) dimPanel.getWidth() - 150, 50);
		g.drawString("----------------", (int) dimPanel.getWidth() - 175, 60);
		int i = 30;
		int pos = 1;
		g.drawString(game.reportScores(), (int) dimPanel.getWidth() - 150, 75);

	}

	public void paintMessege() {
		gra.setColor(Color.red);
		gra.setFont(new Font("Ubuntu", Font.BOLD, 80));
		gra.drawString(this.message, (int) dimPanel.getWidth() - 900, 300);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
