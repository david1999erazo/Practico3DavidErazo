package model;

import java.io.Serializable;



public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nickname;
	private String password;
	private String email;
	

	public Player(String nickname, String password, String email) {

		this.nickname = nickname;
		this.password = password;
		this.email = email;

	}


	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

}
