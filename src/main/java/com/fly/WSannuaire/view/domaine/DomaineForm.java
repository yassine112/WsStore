package com.fly.WSannuaire.view.domaine;

import com.fly.WSannuaire.bean.DomaineBean;
import com.fly.WSannuaire.bean.ProjectBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.DomaineEvent;
import com.fly.WSannuaire.service.DomaineServices;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class DomaineForm extends FormLayout {
	private static final long serialVersionUID = 1L;

	private TextField tfName;
	private TextArea taDescription;

	private Button btnSave, btnDelete;

	private DomaineBean domaine;
	private ProjectBean projectBean;
	private Binder<DomaineBean> binder;

	public DomaineForm(ProjectBean projectBean) {
		this.projectBean = projectBean;

		binder = new Binder<>(DomaineBean.class);
		domaine = new DomaineBean();

		HorizontalLayout buttons = new HorizontalLayout();

		setSizeUndefined();
		tfName = new TextField("Nom Domaine");
		taDescription = new TextArea("Description");

		tfName.setWidth(250, Unit.PIXELS);
		taDescription.setWidth(250, Unit.PIXELS);

		btnSave = new Button("Enregistrer");
		btnDelete = new Button("Supprimer");

		btnSave.setStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.setClickShortcut(KeyCode.ENTER);

		btnSave.addClickListener(e -> addOrSave());
		btnDelete.addClickListener(e -> delete());

		dataBinding();

		buttons.addComponents(btnSave, btnDelete);
		addComponents(tfName, taDescription, buttons);

		MyBus.getInstance().register(this);
	}

	public void setCustomer(DomaineBean domaine) {
		this.domaine = domaine;
		binder.setBean(domaine);
		setVisible(true);
		tfName.selectAll();
	}

	/**
	 * bind object to fields
	 */
	void dataBinding() {
		binder.forField(tfName).asRequired("Ce champ est obligatoire").bind(DomaineBean::getName, DomaineBean::setName);
		binder.forField(taDescription).bind(DomaineBean::getDescription, DomaineBean::setDescription);
	}

	void addOrSave() {
		if (binder.isValid()) {
			domaine.setIdProject(projectBean);
			DomaineServices.getInstance().addOrSave(domaine);
			setVisible(false);
			MyBus.getInstance().post(new DomaineEvent(domaine));
		} else {
			detectError();
		}
	}

	void delete() {
		try {
			DomaineServices.getInstance().delete(domaine);
			MyBus.getInstance().post(new DomaineEvent(domaine));
			setCustomer(new DomaineBean());
			setVisible(false);
		} catch (Exception e) {
			// TODO: handle exception
			Notification.show("Erreur","Des web services sont associé a ce domaine. Vous pouvez pas le supprimer", Notification.Type.ERROR_MESSAGE);
		}
	}

	void detectError() {
		if (tfName.isEmpty()) {
			tfName.setComponentError(new UserError("Ce champ est obligatoire"));
		}
	}

	public void enabledDelete(boolean isNew) {
		btnDelete.setEnabled(!isNew);
	}
}
