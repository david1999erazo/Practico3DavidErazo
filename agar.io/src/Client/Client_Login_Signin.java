package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ResourceBundle.Control;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import control.Controller;
import services.Server;

public class Client_Login_Signin {

	private Controller controller;
	private String mail;
	private String password;
	private String nickname;
	private int port;
	private boolean login;

	public Client_Login_Signin(Controller controller,String mail,String password,String nickname,int port) {
		this.controller = controller;
		this.mail = mail;
		this.password = password;
		this.nickname = nickname;
		this.port = port;
		this.login= false;
		
		connectionSSL();
	}
	
	public Client_Login_Signin(Controller controller,String mail,String password,int port) {
		this.controller = controller;
		this.mail = mail;
		this.password = password;
		this.port = port;
		this.login= true;
		
		connectionSSL();
	}

	
	private void connectionSSL() {
		System.setProperty("javax.net.ssl.trustStore", "./resources/data/MyClient.jks");
		ObjectOutputStream os = null;
		ObjectInputStream is = null;
		SSLSocket sslsocket = null;

		try {
			SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
			sslsocket = (SSLSocket) f.createSocket(Controller.SERVER_ADRESS,port);
			sslsocket.startHandshake();

			
			os = new ObjectOutputStream(sslsocket.getOutputStream());
			is = new ObjectInputStream(sslsocket.getInputStream());
			
			if (login) {
				login(os, is);
			}
			if (!login) {
				register(os, is);
			}
			
			sslsocket.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login(ObjectOutputStream os, ObjectInputStream is) throws Exception {

		os.writeObject(Server.LOGIN);
		os.writeObject(this.mail);
		os.writeObject(this.password);
		os.flush();
		System.out.println("email: " + mail);
		System.out.println("pasword: " + password);
		String s = (String) is.readObject();
		System.out.println(s);
		System.out.println("*****************");
		if (s.equals(Server.LOGIN_OK)) {
			this.controller.setCorrectLogin(true);
			int id =  Integer.parseInt( (String)is.readObject());
			this.controller.setId(id);
		}else {
			this.controller.setCorrectLogin(false);
		} 
	}

	public void register(ObjectOutputStream os, ObjectInputStream is) throws Exception {
		
		os.writeObject(Server.SIGN_IN);
		os.writeObject(nickname);
		os.writeObject(mail);
		os.writeObject(password);

		System.out.println("nickname: " + nickname);
		System.out.println("mail: " + mail);
		System.out.println("password: " + password);
		os.flush();

		String s = (String) is.readObject();
		System.out.println(s);
		System.out.println("*****************");
	}

}
