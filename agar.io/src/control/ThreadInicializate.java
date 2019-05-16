package control;

import model.Game;

public class ThreadInicializate extends Thread {

	private Game game;
	private Long startTime;
	
	public ThreadInicializate(Game game) {
		this.game = game;
		startTime = System.currentTimeMillis();
	}
	
	@Override
	public void run() {
		while (!game.isOn()) {
			
			if(game.getAvatars().size()==Game.MAX_PLAYERS) {
				game.startGame();
			}
			
			Long Currently = System.currentTimeMillis();
			Long diferent = Currently - startTime;
			
			if(diferent>=Game.TIME_START) {
				if(game.getAvatars().size()>=Game.MIN_PLAYERS){
					game.startGame();	
				}
			}
		}
		
		System.out.println("*********** Game was Started ************");
		startTime = System.currentTimeMillis();
		while(game.isOn()) {
			
			Long Currently = System.currentTimeMillis();
			Long diferent = Currently - startTime;
			if(diferent>=Game.TIME_OUT) {
				game.setOn(false);
				
			}
		}
	
	
	}
	
	
	
}
