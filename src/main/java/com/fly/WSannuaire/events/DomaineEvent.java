package com.fly.WSannuaire.events;

import com.fly.WSannuaire.bean.DomaineBean;

public class DomaineEvent {
	DomaineBean domaineBean = new DomaineBean();

	public DomaineEvent(DomaineBean domaineBean) {
		this.domaineBean = domaineBean;
	}

}
