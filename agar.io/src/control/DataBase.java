package control;

import java.io.*;
import java.util.*;
import model.*;

public class DataBase {
	public final static int FOOD_RADIUS = 10;
	public final static String USERS_PATH = "./resources/data/users.txt";
	public final static String GAME_PATH = "./resources/data/game.txt";
	public final static String GAME_USER_PATH = "./resources/data/userGame.txt";
	private static ArrayList<Player> players = new ArrayList<Player>();

	public Game loadGame() {
		
		Game game = null;
		try {
			InputStream file = new FileInputStream(GAME_PATH);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);

			game = (Game) input.readObject();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return game;
	}

	public static void saveGame(Game game) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAME_PATH));
			oos.writeObject(game);
			oos.close();
			oos.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void registerUser(String nickname, String password, String email) {
		readUsers();
		Player usr = new Player(nickname, password, email);
		players.add(usr);
		saveUsers();
	}

	public static void readUsers() {
		try {
			InputStream file = new FileInputStream(USERS_PATH);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);

			players = (ArrayList<Player>) input.readObject();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void saveUsers() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_PATH));
			oos.writeObject(players);
			oos.close();

		} catch (IOException e) {
		}
	}
	
	public static boolean validateLogin(String email, String password) {

		readUsers();
		boolean correct = false;
		for (int i = 0; i < players.size(); i++) {
			Player compare = players.get(i);

			if (compare.getEmail().equals(email) && compare.getPassword().equals(password)) {
				correct = true;

			}
		}
		return correct;

	}

	public static String findNickName(String email) {

		readUsers();
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getEmail().equals(email)) {
				return players.get(i).getNickname();
			}

		}
		return "";
	}

	public static void main(String[] args) {

		DataBase.registerUser("deibi", "12345", "deibi@");
		DataBase.registerUser("DaniG", "12345", "daniG@");
		DataBase.registerUser("Sarris", "12345", "laSarris@");
		DataBase.registerUser("Naicolas", "12345", "elBiober@");
	}

}
