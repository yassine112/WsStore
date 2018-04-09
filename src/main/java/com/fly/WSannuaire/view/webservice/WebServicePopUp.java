package com.fly.WSannuaire.view.webservice;

import com.fly.WSannuaire.bean.DomaineBean;
import com.fly.WSannuaire.bean.WebServiceBean;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;

public class WebServicePopUp extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Panel panel;
	private WebServiceForm webServiceForm;
	
	public WebServicePopUp(WebServiceBean webServiceBean, DomaineBean domaineBean){
		super("Nouveau Web Service");
		
		webServiceForm = new WebServiceForm(domaineBean);
		webServiceForm.setCustomer(webServiceBean);
		panel = new Panel(webServiceForm);
		settingWindow();
		
		setContent(panel);
	}
	
	void settingWindow() {
		setWidth(60, Unit.PERCENTAGE);
//		setHeight(80, Unit.PERCENTAGE);
		setResizable(false);
		setModal(true);
		center();
	}
}
