package com.fly.WSannuaire.events;

import com.fly.WSannuaire.bean.ServerBean;

public class ServerEvent {
	ServerBean server = new ServerBean();
	
	public ServerEvent(ServerBean server){
		this.server = server;
	}
}
