package control;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Client.Client_Login_Signin;
import Client.Client_Play_Game;
import gui.Main_Agario;
import model.*;
import services.*;

public class Controller {

	public final static int PORT = 8000;
	// public final static String SERVER_ADRESS = "172.30.174.251";
	public final static String SERVER_ADRESS = "localHost";

	private Socket socket;
	private Game game;
	private boolean correctLogin;
	private String nickName;
	private int id;
	private Main_Agario main_Agario;

	public Controller(Main_Agario main_Agario) {

		this.main_Agario = main_Agario;
		this.correctLogin = false;
		this.game = new Game();

		try {
			this.socket = new Socket(SERVER_ADRESS, PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int answerPort(String request) {

		int answer = -1;
		DataOutputStream out;
		DataInputStream in;

		try {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());

			out.writeUTF(request);
			String message = in.readUTF();
			answer = Integer.parseInt(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return answer;
	}

	public void login(String mail, String password) {
		int x = answerPort(Server.LOGIN);
		Client_Login_Signin cls = new Client_Login_Signin(this, mail, password, x);
	}

	public void register(String mail, String password, String nickname) {
		int x = answerPort(Server.LOGIN);
		Client_Login_Signin cls = new Client_Login_Signin(this, mail, password, nickname, x);
	}

	public void startGame() {
		int x = answerPort(Server.PLAY);
		Client_Play_Game cpg = new Client_Play_Game(this, x);
		cpg.start();
	}

	private ArrayList<Avatar> readPlayers(String[] infoPlayers) {
		ArrayList<Avatar> players = new ArrayList<Avatar>();

		for (int i = 0; i < infoPlayers.length; i++) {

			String[] player = infoPlayers[i].split("/");
			int id = Integer.parseInt(player[0]);
			String nickname = player[1];
			double radious = Double.parseDouble(player[2]);
			double posX = Double.parseDouble(player[3]);
			double posY = Double.parseDouble(player[4]);
			int rgb = Integer.parseInt(player[5]);

			boolean alive = player[6].equals("true") ? true : false;

			if (this.id == id && alive == false) {
				return null;
			}
			Avatar player_avatar = new Avatar(nickname, id);
			player_avatar.setColor(new Color(rgb));
			player_avatar.setPosX(posX);
			player_avatar.setPosY(posY);
			player_avatar.setRadious(radious);
			player_avatar.setAlive(alive);
			players.add(player_avatar);
		}

		return players;

	}

	private ArrayList<Avatar> readFood(String[] infoBalls) {
		ArrayList<Avatar> food = new ArrayList<Avatar>();

		for (int i = 0; i < infoBalls.length; i++) {
			String[] ballInfo = infoBalls[i].split("/");
			int rgb = Integer.parseInt(ballInfo[0]);
			double posX = Double.parseDouble(ballInfo[1]);
			double posY = Double.parseDouble(ballInfo[2]);

			try {
				posX = Double.parseDouble(ballInfo[1]);
				posY = Double.parseDouble(ballInfo[2]);
			} catch (Exception e) {
			}
			Avatar bl = new Avatar();
			bl.setColor(new Color(rgb));
			bl.setPosX(posX);
			bl.setPosY(posY);
			food.add(bl);
		}
		return food;
	}

	public void updateWorld(String[] infoPlayers, String[] infoBalls) {
		ArrayList<Avatar> players = readPlayers(infoPlayers);
		if (players != null) {
			game.updateWorld(players, readFood(infoBalls), this.id);
		}
	}

	public void initializeWorld(String[] infoPlayers, String[] infoBalls) {
		ArrayList<Avatar> players = readPlayers(infoPlayers);
		if (players != null) {
			game.setOn(true);
			game.initializeWorld(players, readFood(infoBalls));
		}
	}

	public Avatar getAvatar() {
		return game.getAvatar(this.id);
	}

	public void startMoving() {
		ThreadMoving m = new ThreadMoving(id, this.game);
		m.start();
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean isCorrectLogin() {
		return correctLogin;
	}

	public void setCorrectLogin(boolean correctLogin) {
		this.correctLogin = correctLogin;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void showLose() {
		String message = "";
		message = "¡¡ You Lose !!";
		main_Agario.showMessage(message);
		System.out.println("lose");
	}

	public void showWait() {
		String message = "Please Wait";
		main_Agario.showMessage(message);
	}

	public void showWin() {
		String message = "¡¡ You Win !!";
		System.out.println("win");
		main_Agario.showMessage(message);
	}

	public boolean youWin() {
		if (getGame().getAvatars().size() == 1 && getGame().getAvatars().get(0).getId() == id) {
			return true;
		}
		return false;
	}

	public void cleanMessage() {
		main_Agario.showMessage("");
	}

}