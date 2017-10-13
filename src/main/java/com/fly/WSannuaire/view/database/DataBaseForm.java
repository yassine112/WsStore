package com.fly.WSannuaire.view.database;

import java.util.ArrayList;
import java.util.List;

import com.fly.WSannuaire.bean.DataBaseBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.DataBaseEvent;
import com.fly.WSannuaire.service.DataBaseServices;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class DataBaseForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private TextField tfUrl;
	private TextField tfName;
	private TextField tfLogin;
	private PasswordField pfPassWord;
	private ComboBox<String> cbType;
	private TextArea taRemarque;
	private Button btnSave, btnDelete;

	private DataBaseBean dbb;

	private Binder<DataBaseBean> binder;

	public DataBaseForm() {
		binder = new Binder<>(DataBaseBean.class);
		HorizontalLayout buttons = new HorizontalLayout();

		setSizeUndefined();
		tfUrl = new TextField("URL");
		tfName = new TextField("Nom");
		tfLogin = new TextField("Login");
		pfPassWord = new PasswordField("Mot de Pass");
		cbType = new ComboBox<>("Type");
		taRemarque = new TextArea("Remarque");

		List<String> types = new ArrayList<>();
		types.add("Test");
		types.add("Pre Prod");
		types.add("Prod");

		tfUrl.setWidth("250px");
		tfName.setWidth("250px");
		tfLogin.setWidth("250px");
		pfPassWord.setWidth("250px");
		cbType.setWidth("250px");
		taRemarque.setWidth("250px");

		cbType.setItems(types);

		btnSave = new Button("enregistrer");
		btnDelete = new Button("supprimer");

		btnSave.setStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.setClickShortcut(KeyCode.ENTER);

		buttons.addComponents(btnSave, btnDelete);
		addComponents(tfUrl, tfName, tfLogin, pfPassWord, cbType, taRemarque, buttons);

		btnSave.addClickListener(e -> addOrSave());
		btnDelete.addClickListener(e -> delete());

		bindingData();
	}

	private void bindingData() {
		/**
		 * Url Field
		 */
		binder.forField(tfUrl).asRequired("Ce champ est obligatoire").bind(DataBaseBean::getUrl, DataBaseBean::setUrl);
		/**
		 * Nmae Field
		 */
		binder.forField(tfName).asRequired("Ce champ est obligatoire").bind(DataBaseBean::getName, DataBaseBean::setName);
		/**
		 * Login field
		 */
		binder.forField(tfLogin).asRequired("Ce champ est obligatoire").bind(DataBaseBean::getLogin,
				DataBaseBean::setLogin);
		/**
		 * PassWord Field
		 */
		binder.forField(pfPassWord).asRequired("Ce champ est obligatoire").bind(DataBaseBean::getPass,
				DataBaseBean::setPass);
		/**
		 * type combobox
		 */
		binder.forField(cbType).bind(DataBaseBean::getType, DataBaseBean::setType);
		/**
		 * Remarque Rich Text Area
		 */
		binder.forField(taRemarque).bind(DataBaseBean::getRemarque, DataBaseBean::setRemarque);
	}

	/**
	 * Import User from grid to Form
	 * 
	 * @param user
	 */
	public void setCustomer(DataBaseBean dbb) {
		this.dbb = dbb;
		binder.setBean(dbb);
		setVisible(true);
		tfUrl.selectAll();
	}

	private void delete() {
		DataBaseServices.getInstance().remove(dbb);
		MyBus.getInstance().post(new DataBaseEvent(dbb));
		setCustomer(new DataBaseBean());
		setVisible(false);
	}

	private void addOrSave() {
		if (binder.isValid()) {
			DataBaseServices.getInstance().addOrSave(dbb);
			setVisible(false);
			MyBus.getInstance().post(new DataBaseEvent(dbb));
		} else {
			detectError();
		}
	}

	private void detectError() {
		if (tfUrl.isEmpty()) {
			tfUrl.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (tfName.isEmpty()) {
			tfName.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (tfLogin.isEmpty()) {
			tfLogin.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (pfPassWord.isEmpty()) {
			pfPassWord.setComponentError(new UserError("Ce champ est obligatoire"));
		}
	}

	public void enabledDelete(boolean isNew) {
		btnDelete.setEnabled(!isNew);
	}
}
