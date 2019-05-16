package webLogin;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	public WebServer() {
		System.out.println("Webserver Started");
		try {
			ServerSocket serverSocket = new ServerSocket(80);
			while (true) {
				System.out.println("Waiting for the client request");
				Socket remote = serverSocket.accept();
				System.out.println("Connection made");
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


