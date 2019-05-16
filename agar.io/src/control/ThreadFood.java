package control;

import model.Avatar;
import model.Game;

public class ThreadFood extends Thread {

	private Game game;

	public ThreadFood(Game game) {
		this.game = game;
	}

	/**
	 * Creates a new circle and adds it to the food ArrayList every 2.5 seconds. The
	 * circle has a radius of 10 and a random color.
	 */
	public void run() {

		try {
			while (game.isOn()) {

				game.getFood().add(new Avatar());
				sleep(1500);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
