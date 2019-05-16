package webLogin;

import java.net.ServerSocket;
import java.net.Socket;

import gui.Main_Agario;

public class LoginServer {

	public LoginServer() {

		try {
			ServerSocket serverSocket = new ServerSocket(8081);

			System.out.println("Login server started");
			while (true) {

				Socket remote = serverSocket.accept();
				new Thread(new LoginThread(remote)).start();

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		LoginServer ls = new LoginServer();
	}

}
