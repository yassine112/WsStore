package com.fly.WSannuaire.events;

import com.fly.WSannuaire.bean.UserBean;

public class UserEvent {
	UserBean user = new UserBean();
	
	public UserEvent(UserBean user){
		this.user = user;
	}
}
