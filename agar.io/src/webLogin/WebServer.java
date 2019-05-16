package webLogin;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	public WebServer() {
		try {
			ServerSocket serverSocket = new ServerSocket(80);
			while (true) {
				Socket remote = serverSocket.accept();
				new Thread(new ClientHandler(remote)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new WebServer();
	}
}


