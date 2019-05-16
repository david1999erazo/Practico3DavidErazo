package control;

import gui.Space;

public class ThreadRepaint extends Thread {

	private Space space;
	private boolean control;

	public ThreadRepaint(Space space) {
		this.space = space;
		control = true;
	}

	@Override
	public void run() {
		while (control) {
			try {
				this.space.repaint();
				sleep(150);
			} catch (Exception ex) {
				System.out.println("Error in repaint");
			}
		}
	}
	
	public void stopPaint() {
		control = false;
	}

}
