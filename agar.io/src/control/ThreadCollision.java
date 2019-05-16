package control;

import model.Game;

public class ThreadCollision extends Thread {
	private Game game;

	public ThreadCollision(Game game) {
		this.game = game;
	}

	public void run() {
		while (game.isOn()) {
			try {
				checkCollisions();
				sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void checkCollisions() {
		for (int i = 0; i < game.getAvatars().size(); i++) {
			for (int j = 0; j < game.getFood().size(); j++) {
				if (game.getFood().get(j) != null) {
					game.getAvatars().get(i).check_Collision(game.getFood().get(j));
				}
			}

		}
		for (int i = 0; i < game.getAvatars().size(); i++) {
			for (int j = 0; j < game.getAvatars().size(); j++) {
				if (game.getAvatars().get(j) != null && game.getAvatars().get(i) != null) {
					game.getAvatars().get(i).check_Collision(game.getAvatars().get(j));
				}
			}
		}

		game.deleteAvatars();
		game.deleteFood();
	}
}
