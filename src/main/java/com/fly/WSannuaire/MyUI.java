package com.fly.WSannuaire;

import javax.servlet.annotation.WebServlet;

import com.fly.WSannuaire.bean.UserBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.UserLogin;
import com.fly.WSannuaire.events.UserLogout;
import com.fly.WSannuaire.view.login.LoginPage;
import com.fly.WSannuaire.view.mainview.MainView;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

/**
 * @author EL HALAOUI Yassine	
 *
 */

@Title("AnnuaireWS")
@Theme("mytheme")
public class MyUI extends UI { 
	private static final long serialVersionUID = 1L;

    
	@Override
    protected void init(VaadinRequest vaadinRequest) {
//		System.out.println(VaadinSession.getCurrent().getSession().getMaxInactiveInterval());
		
		Responsive.makeResponsive(this);
		loadContent();
		
		MyBus.getInstance().register(MyUI.this);
	}
	
	@Subscribe
	public void userLoginRequested(UserLogin e){		
		loadContent();		
	}

	@Subscribe
	public void userLogOutRequested(UserLogout e){		
		loadContent();		
	}
	
	/**
	 * Charger le contenu de la page
	 */
	private void loadContent(){
		UserBean user = (UserBean) VaadinSession.getCurrent().getAttribute(UserBean.class);
		
		if(user != null){
	        setContent(new MainView());	        
		}else{
			setContent(new LoginPage());
		}
		
	}

	@Override
	public void detach() {
		super.detach();
		try{
			MyBus.getInstance().unregister(MyUI.this);
		}catch (IllegalArgumentException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = true)
    public static class MyUIServlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
    }

}
