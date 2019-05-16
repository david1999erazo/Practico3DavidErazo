package services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import services.*;

public class AssignPort extends Thread {

	private Socket socket;
	private Server server;

	public AssignPort(Socket ss, Server server) {
		this.socket = ss;
		this.server = server;
	}

	@Override
	public void run() {

		boolean control = true;
		while (control) {

			try {

				if (socket.isConnected()) {

					DataInputStream in;
					DataOutputStream out;

					in = new DataInputStream(socket.getInputStream());
					out = new DataOutputStream(socket.getOutputStream());

					String key = in.readUTF();

					if (key.equals(Server.LOGIN) || key.equals(Server.SIGN_IN)) {
						out.writeUTF(Server.PORT_LOGIN + "");
						Server_Login_Signin sls = new Server_Login_Signin(this.server);
					}
					if (key.equals(Server.PLAY)) {
						out.writeUTF(Server.PORT_PLAY + "");
						Server_Play_Game spg = new Server_Play_Game(this.server);
						spg.start();
					}
					if (key.equals(Server.MUSIC)) {
						out.writeUTF(Server.PORT_MUSIC + "");
					}
					if (key.equals(Server.OBSERVE)) {
						out.writeUTF(Server.PORT_STREAMING + "");
					}
				}
			} catch (Exception e) {
				control=false;
				System.out.println("client desconnected");
			}

		}
	}
}
