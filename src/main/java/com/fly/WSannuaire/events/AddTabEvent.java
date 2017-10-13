package com.fly.WSannuaire.events;

import com.fly.WSannuaire.bean.DomaineBean;

public class AddTabEvent {
	public DomaineBean domaineBean = new DomaineBean();

	public AddTabEvent(DomaineBean domaineBean) {
		this.domaineBean = domaineBean;
	}
}
