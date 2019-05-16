package services;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import control.*;
import model.*;

public class Server {

	// Since client
	public final static String LOGIN = "LOGIN";
	public final static String SIGN_IN = "SIGN_IN";
	public final static String PLAY = "PLAY";
	public final static String MUSIC = "MUSIC";
	public final static String OBSERVE = "OBSERVE";

	// Answers
	public final static String LOGIN_OK = "LOGIN_OK";
	public final static String LOGIN_INCORRECT = "LOGIN_INCORRECT";
	public final static String SAVE = "SAVE";

	// Ports
	public static int PORT_CONNECTION = 8000;// tcp
	public static int PORT_LOGIN = 9000;// ssl
	public static int PORT_PLAY = 8195;// tcp
	public static int PORT_MUSIC = 8222;// udp
	public static int PORT_STREAMING = 8580;// udp

	public final static int END_TIME = 300000;
	public final static int WAIT_TIME = 10000;

	private static ServerSocket serverSocket;
	private int gamers;
	private Game game;

	public Server() throws IOException {
		System.out.println("****** Server online ******");
		serverSocket = new ServerSocket(PORT_CONNECTION);

		game = new Game();
		int maxGamers = 0;

		ThreadInicializate th = new ThreadInicializate(this.game);
		th.start();

		while (maxGamers < Game.MAX_PLAYERS) {
			Socket socket;
			socket = serverSocket.accept();
			AssignPort assign = new AssignPort(socket, this);
			assign.start();
			maxGamers++;
		}
	}

	public void singin(String nickname, String email, String password) {
		DataBase.registerUser(nickname, password, email);
	}

	public int validateLogin(String email, String password) {
		boolean log = DataBase.validateLogin(email, password);
		if (log) {
			gamers++;
			int id = nextId();
			game.addAvatar(findNickname(email), id);
			System.out.println("gamers: " + gamers);
			return id;
		} else {
			return -1;
		}
	}

	private String findNickname(String email) {
		return DataBase.findNickName(email);
	}

	public int nextId() {
		int id_ = game.getIdAvailable();
		return id_;
	}

	public String sendBaseGame() {

		ArrayList<Avatar> gamers = game.getAvatars();
		String message = "";

		for (int i = 0; i < gamers.size(); i++) {

			if (gamers.get(i) != null) {
				String id = gamers.get(i).getId() + "";
				String nickname = gamers.get(i).getNickName();
				String radious = gamers.get(i).getRadious() + "";
				String posX = gamers.get(i).getPosX() + "";
				String posY = gamers.get(i).getPosY() + "";
				String rgb = gamers.get(i).getColor().getRGB() + "";
				String live = gamers.get(i).isAlive() ? "true" : "false";
				String player = "";

				if (i == gamers.size() - 1) {
					player = id + "/" + nickname + "/" + radious + "/" + posX + "/" + posY + "/" + rgb + "/" + live;
				} else {
					player = id + "/" + nickname + "/" + radious + "/" + posX + "/" + posY + "/" + rgb + "/" + live
							+ ",";
				}

				message += player;
			}
		}

		message += "_";

		ArrayList<Avatar> food = game.getFood();

		for (int i = 0; i < food.size(); i++) {

			if (food.get(i) != null) {
				String rgb = food.get(i).getColor().getRGB() + "";
				String posX = food.get(i).getPosX() + "";
				String posY = food.get(i).getPosY() + "";
				String ball = rgb + "/" + posX + "/" + posY;

				if (i < food.size() - 1) {
					ball += ",";
				}
				message += ball;
			}
		}
		return message;
	}

	public static ServerSocket getServerSocket() {
		return serverSocket;
	}

	public static void setServerSocket(ServerSocket serverSocket) {
		Server.serverSocket = serverSocket;
	}

	public int getGamers() {
		return gamers;
	}

	public void setGamers(int gamers) {
		this.gamers = gamers;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public static void main(String[] args) {
		try {
			Server s = new Server();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void updateGame(String[] player) {
		int id = Integer.parseInt(player[0]);
		double x = Double.parseDouble(player[1]);
		double y = Double.parseDouble(player[2]);

		boolean isAlive = false;

		if (player[3].equalsIgnoreCase("true")) {
			isAlive = true;
		}

		double radious = Double.parseDouble(player[4]);

		if (this.game.getAvatar(id) != null) {
			this.game.updatePlayer(id, x, y, isAlive, radious);
		}
	}

}