package com.fly.WSannuaire.events;

import com.fly.WSannuaire.bean.DeployementBean;

public class DeployementEvent {
	public DeployementBean deployementBean = new DeployementBean();

	public DeployementEvent(DeployementBean deployementBean) {
		this.deployementBean = deployementBean;
	}
}
