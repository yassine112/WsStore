package com.fly.WSannuaire.common;

import com.google.common.eventbus.EventBus;

public class MyBus {
	private static EventBus bus;
	
	public static EventBus getInstance(){
		if(bus == null){
			bus = new EventBus();
		}
		
		return bus;
	}
}
