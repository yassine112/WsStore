package com.fly.WSannuaire.view.login;

import com.fly.WSannuaire.bean.UserBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.UserLogin;
import com.fly.WSannuaire.service.UserServices;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("deprecation")
public class LoginPage extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public LoginPage() {
		setSizeFull();
		setResponsive(true);
		HorizontalLayout loginForm = new HorizontalLayout(buildLogo(), buildSepa(), buildLoginForm());
		loginForm.setSpacing(true);
		loginForm.setSizeUndefined();
		addComponent(loginForm);
		setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
	}

	private Component buildLogo() {
		return new Embedded(null, new ThemeResource("img/logo.png"));
	}

	private Component buildSepa() {
		Embedded embedded = new Embedded(null, new ThemeResource("img/bg-overlay.png"));
		embedded.setWidth("10px");
		embedded.setHeight("100%");

		return embedded;
	}

	/**
	 * 
	 * @return
	 */
	private Component buildLoginForm() {
		final VerticalLayout loginPanel = new VerticalLayout();
		loginPanel.setSizeUndefined();
		loginPanel.setSpacing(true);

		Responsive.makeResponsive(loginPanel);

		loginPanel.addComponent(buildLabels());
		loginPanel.addComponent(buildFields());

		Label welcome = new Label("Merci de saisir votre login et mot de passe pour vous connecter");
		welcome.setSizeUndefined();
		welcome.addStyleName(ValoTheme.LABEL_TINY);
		welcome.addStyleName(ValoTheme.LABEL_BOLD);

		loginPanel.addComponent(welcome);
		return loginPanel;
	}

	private Component buildFields() {
		HorizontalLayout fields = new HorizontalLayout();
		fields.setSpacing(true);
		fields.addStyleName("fields");

		final TextField username = new TextField("Utilisateur");
		username.setIcon(FontAwesome.USER);
		username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		username.focus();
		username.setPlaceholder("Login");

		final PasswordField password = new PasswordField("Mot de Passe");
		password.setIcon(FontAwesome.LOCK);
		password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		password.setPlaceholder("Mot de pass");

		final Button signin = new Button("Se Connecter");
		signin.addStyleName(ValoTheme.BUTTON_DANGER);
		signin.setClickShortcut(KeyCode.ENTER);
		signin.setIcon(FontAwesome.UNLOCK);

		fields.addComponents(username, password, signin);
		fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);
		signin.addClickListener(e -> {
			if(username.getValue().isEmpty() || password.getValue().isEmpty()){
				Notification.show("Attention", "Login ou Mot de pass Incorrect !", Notification.Type.ERROR_MESSAGE);
			}else{
			
				UserBean user = UserServices.getInstance().getUsersByLogin(username.getValue(), password.getValue());
				
				if (user != null) {
					VaadinSession.getCurrent().setAttribute(UserBean.class, user);
					MyBus.getInstance().post(new UserLogin(username.getValue(), password.getValue()));
					
				} else {
					Notification.show("Attention", "Login ou Mot de pass Incorrect !", Notification.Type.ERROR_MESSAGE);
				}				
			} 
		});
		//
		return fields;
	}

	/**
	 * 
	 * @return
	 */
	private Component buildLabels() {
		CssLayout labels = new CssLayout();
		labels.addStyleName("labels");

		Label welcome = new Label("Bienvenue dans votre application ");
		welcome.setSizeUndefined();
		welcome.addStyleName(ValoTheme.LABEL_H3);
		welcome.addStyleName(ValoTheme.LABEL_COLORED);
		welcome.addStyleName(ValoTheme.LABEL_BOLD);
		labels.addComponent(welcome);

		return welcome;
	}

	public interface LoginListener {
		void userLoggedIn();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}
