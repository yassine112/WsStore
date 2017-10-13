package com.fly.WSannuaire.events;

import com.fly.WSannuaire.bean.DataBaseBean;

public class DataBaseEvent {
	DataBaseBean dbb = new DataBaseBean();
	
	public DataBaseEvent(DataBaseBean dbb){
		this.dbb = dbb;
	}
}
