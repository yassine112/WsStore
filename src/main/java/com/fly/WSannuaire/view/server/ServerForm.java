package com.fly.WSannuaire.view.server;

import java.util.ArrayList;
import java.util.List;

import com.fly.WSannuaire.bean.ServerBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.ServerEvent;
import com.fly.WSannuaire.service.ServerServices;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class ServerForm extends FormLayout {
	private static final long serialVersionUID = 1L;
	private Button btnHideForm;
	private TextField tfIp;
	private TextField tfName;
	private TextField tfSysExp;
	private ComboBox<String> localisation;
	private ComboBox<String> cbType;
	private TextArea remarque;
	private Button btnSave, btnDelete;

	private ServerBean server;

	private Binder<ServerBean> binder;

	public ServerForm() {

		binder = new Binder<>(ServerBean.class);
		HorizontalLayout buttons = new HorizontalLayout();

		List<String> locals = new ArrayList<>();
		locals.add("MA");
		locals.add("FR");

		List<String> types = new ArrayList<>();
		types.add("Test");
		types.add("Pre Prod");
		types.add("Prod");

		setSizeUndefined();
		btnHideForm = new Button();
		tfIp = new TextField("Adress IP");
		tfName = new TextField("Nom");
		tfSysExp = new TextField("système d'exploitation");
		localisation = new ComboBox<>("Localisation");
		cbType = new ComboBox<String>("Type");
		remarque = new TextArea("Remarque");

		localisation.setItems(locals);
		cbType.setItems(types);

		tfIp.setWidth("250px");
		tfName.setWidth("250px");
		tfSysExp.setWidth("250px");
		localisation.setWidth("250px");
		cbType.setWidth("250px");
		remarque.setWidth("250px");

		btnHideForm.setId("closeBtn");
		btnHideForm.setIcon(VaadinIcons.CLOSE);
		btnHideForm.addStyleName(ValoTheme.BUTTON_LINK);
		btnHideForm.addClickListener(e -> {
			setVisible(false);
		});
		
		btnSave = new Button("enregistrer");
		btnDelete = new Button("supprimer");

		btnSave.setStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.setClickShortcut(KeyCode.ENTER);

		buttons.addComponents(btnSave, btnDelete);
		addComponents(btnHideForm, tfIp, tfName, tfSysExp, localisation, cbType, remarque, buttons);

		btnSave.addClickListener(e -> addOrSave());
		btnDelete.addClickListener(e -> delete());

		bindingData();
	}

	private void bindingData() {
		binder.forField(tfIp).asRequired("Ce champ est obligatoire").bind(ServerBean::getIpAdress, ServerBean::setIpAdress);
		binder.forField(tfName).asRequired("Ce champ est obligatoire").bind(ServerBean::getName, ServerBean::setName);
		binder.forField(tfSysExp).asRequired("Ce champ est obligatoire").bind(ServerBean::getSysExploitation, ServerBean::setSysExploitation);
		binder.forField(localisation).bind(ServerBean::getLocalisation, ServerBean::setLocalisation);
		binder.forField(cbType).bind(ServerBean::getType, ServerBean::setType);
		binder.forField(remarque).bind(ServerBean::getRemarque, ServerBean::setRemarque);
	}

	private void delete() {
		try {
			ServerServices.getInstance().remove(server);
			MyBus.getInstance().post(new ServerEvent(server));
			setCustomer(new ServerBean());
			setVisible(false);
		} catch (Exception e) {
			Notification.show("Erreur", "Des Déployments sont associé a ce serveur. Vous pouvez pas le supprimer", Notification.Type.ERROR_MESSAGE);
		}
	}

	private void addOrSave() {
		if (binder.isValid()) {
			ServerServices.getInstance().addOrSave(server);
			setVisible(false);
			MyBus.getInstance().post(new ServerEvent(server));
		} else {
			detectError();
		}
	}

	public void detectError() {
		if (tfIp.isEmpty()) {
			tfIp.setComponentError(new UserError("Ce champ est obligatoire"));
		}

		if (tfName.isEmpty()) {
			tfName.setComponentError(new UserError("Ce champ est obligatoire"));
		}

		if (tfSysExp.isEmpty()) {
			tfSysExp.setComponentError(new UserError("Ce champ est obligatoire"));
		}
	}

	public void enabledDelete(boolean isNew) {
		btnDelete.setEnabled(!isNew);
	}

	public void setCustomer(ServerBean server) {
		// TODO Auto-generated method stub
		this.server = server;
		binder.setBean(server);
		setVisible(true);
		tfIp.selectAll();
	}
}
