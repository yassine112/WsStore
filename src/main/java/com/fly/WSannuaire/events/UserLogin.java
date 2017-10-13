package com.fly.WSannuaire.events;

public class UserLogin {
	private final String login;
	private final String passWord;

	public UserLogin(String login, String passWord) {
		super();
		this.login = login;
		this.passWord = passWord;
	}

	public String getLogin() {
		return login;
	}

	public String getPassWord() {
		return passWord;
	}

}
