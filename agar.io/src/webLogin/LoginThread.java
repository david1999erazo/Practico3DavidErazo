package webLogin;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import control.DataBase;

public class LoginThread implements Runnable {


	private final Socket socket;
	
	
	public LoginThread(Socket remote) {
		this.socket = remote;
	}

	@Override
	public void run() {
		try {
			DataInputStream in;
			DataOutputStream out;

			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			String email = in.readUTF();
			String password = in.readUTF();
			
			if(DataBase.validateLogin(email, password)) {
				out.writeUTF("yes");
			}else {
				out.writeUTF("no");
			}
			
		} catch (IOException e) {

		}
		
	}
	
}
