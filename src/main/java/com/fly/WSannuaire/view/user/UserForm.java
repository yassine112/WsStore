package com.fly.WSannuaire.view.user;

import javax.persistence.RollbackException;

import com.fly.WSannuaire.bean.UserBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.UserEvent;
import com.fly.WSannuaire.service.UserServices;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author EL HALAOUI Yassine
 *
 */
public class UserForm extends FormLayout {
	private static final long serialVersionUID = 1L;
	private TextField tfFname;
	private TextField tfLname;
	private TextField tfLogin;
	private PasswordField pfPassWord;
	private Button btnSave, btnDelete;

	private UserBean user;
	private Binder<UserBean> binder;

	public UserForm() {
		binder = new Binder<>(UserBean.class);
		HorizontalLayout buttons = new HorizontalLayout();

		setSizeUndefined();
		tfFname = new TextField("Prénom");
		tfLname = new TextField("Nom");
		tfLogin = new TextField("Login");
		pfPassWord = new PasswordField("Mot de pass");

		tfFname.setWidth("250px");
		tfLname.setWidth("250px");
		tfLogin.setWidth("250px");
		pfPassWord.setWidth("250px");

		btnSave = new Button("enregistrer");
		btnDelete = new Button("supprimer");

		btnSave.setStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.setClickShortcut(KeyCode.ENTER);

		buttons.addComponents(btnSave, btnDelete);
		addComponents(tfFname, tfLname, tfLogin, pfPassWord, buttons);

		btnSave.addClickListener(e -> addOrSave());
		btnDelete.addClickListener(e -> delete());

		bindingData();
	}

	public void bindingData() {
		/**
		 * First Name Field
		 */
		binder.forField(tfFname).asRequired("Ce champ est obligatoire").bind(UserBean::getFname, UserBean::setFname);
		/**
		 * Last Name Field
		 */
		binder.forField(tfLname).asRequired("Ce champ est obligatoire").bind(UserBean::getLname, UserBean::setLname);
		/**
		 * Login Field
		 */
		binder.forField(tfLogin).asRequired("Ce champ est obligatoire").bind(UserBean::getLogin, UserBean::setLogin);
		/**
		 * PassWord Field
		 */
		binder.forField(pfPassWord).asRequired("Ce champ est obligatoire").bind(UserBean::getPass, UserBean::setPass);
	}

	/**
	 * Import User from grid to Form
	 * 
	 * @param user
	 */
	public void setCustomer(UserBean user) {
		this.user = user;
		binder.setBean(user);
		setVisible(true);
		tfFname.selectAll();
	}

	public void addOrSave() {
		try {
			if (binder.isValid()) {
				UserServices.getInstance().addOrSave(user);
				setVisible(false);
				MyBus.getInstance().post(new UserEvent(user));
			} else {
				detectError();
			}
		} catch (RollbackException e) {
			Notification errorNotification = new Notification("Error", "Le login que vous avez choisi existe déjà", Notification.Type.ERROR_MESSAGE);
			errorNotification.setPosition(Position.TOP_CENTER);
			errorNotification.show(Page.getCurrent());
		}
	}

	public void detectError() {
		if (tfFname.isEmpty()) {
			tfFname.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (tfLname.isEmpty()) {
			tfLname.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (tfLogin.isEmpty()) {
			tfLogin.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (pfPassWord.isEmpty()) {
			pfPassWord.setComponentError(new UserError("Ce champ est obligatoire"));
		}
	}

	public void delete() {
		UserServices.getInstance().remove(user);
		MyBus.getInstance().post(new UserEvent(user));
		setCustomer(new UserBean());
		setVisible(false);
	}

	public void enabledDelete(boolean isNew) {
		btnDelete.setEnabled(!isNew);
	}
}
