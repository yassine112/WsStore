package com.fly.WSannuaire.events;

import com.fly.WSannuaire.bean.WebServiceBean;

public class WebServiceEvent {
	WebServiceBean webService = new WebServiceBean();
	
	public WebServiceEvent(WebServiceBean webService){
		this.webService = webService;
	}
}
