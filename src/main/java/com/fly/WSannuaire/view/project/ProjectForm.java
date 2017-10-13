package com.fly.WSannuaire.view.project;

import com.fly.WSannuaire.bean.ProjectBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.ProjectEvent;
import com.fly.WSannuaire.service.ProjectServices;
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

public class ProjectForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private TextField tfName;
	private TextArea tfDescription;
	private TextField tfNameCVS;

	private Button btnSave, btnDelete;

	private ProjectBean projetBean;
	private Binder<ProjectBean> binder;

	public ProjectForm() {
		binder = new Binder<>(ProjectBean.class);
		HorizontalLayout buttons = new HorizontalLayout();

		setMargin(true);
		setSizeFull();

		tfName = new TextField("Nom");
		tfDescription = new TextArea("Déscription");
		tfNameCVS = new TextField("Nom CVS");

		tfName.setWidth(100, Unit.PERCENTAGE);
		tfDescription.setWidth(100, Unit.PERCENTAGE);
		tfNameCVS.setWidth(100, Unit.PERCENTAGE);

		btnSave = new Button("enregistrer");
		btnDelete = new Button("supprimer");

		btnSave.setStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.setClickShortcut(KeyCode.ENTER);

		buttons.addComponents(btnSave, btnDelete);
		addComponents(tfName, tfDescription, tfNameCVS, buttons);

		btnSave.addClickListener(e -> addOrSave());
		btnDelete.addClickListener(e -> delete());

		bindingData();
	}

	public void setCustomer(ProjectBean projet) {
		this.projetBean = projet;
		binder.setBean(projet);

		if (projet.getIdProject() == null) {
			enabledDelete(true);
		}

		tfName.selectAll();
	}

	private void addOrSave() {

		try {
			if (binder.isValid()) {
				ProjectServices.getInstance().addOrSave(projetBean);
				MyBus.getInstance().post(new ProjectEvent(projetBean));
				enabledDelete(false);
				tfName.selectAll();
			} else {
				detectError();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Tester la modife
	private void delete() {

		try {
			ProjectServices.getInstance().remove(projetBean);
			MyBus.getInstance().post(new ProjectEvent(new ProjectBean()));
			setCustomer(new ProjectBean());
			tfName.selectAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Notification.show("Erreur","Des domaines sont associé a ce projet. Vous pouvez pas le supprimer", Notification.Type.ERROR_MESSAGE);
		}
 
	}

	/**
	 * Binding fields with Bean
	 */
	private void bindingData() {
		binder.forField(tfName).asRequired("Ce champs est obligatoire").bind(ProjectBean::getName, ProjectBean::setName);
		binder.forField(tfDescription).bind(ProjectBean::getDescription, ProjectBean::setDescription);
		binder.forField(tfNameCVS).asRequired("Ce champ est obligatoire").bind(ProjectBean::getCvsName, ProjectBean::setCvsName);
	}

	private void detectError() {
		if (tfName.isEmpty()) {
			tfName.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (tfNameCVS.isEmpty()) {
			tfNameCVS.setComponentError(new UserError("Ce champ est obligatoire"));
		}
	}

	public void enabledDelete(boolean isNew) {
		btnDelete.setEnabled(!isNew);
	}
}
