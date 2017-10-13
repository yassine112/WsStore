package com.fly.WSannuaire.view.deployement;

import com.fly.WSannuaire.bean.DeployementBean;
import com.fly.WSannuaire.bean.ProjectBean;
import com.fly.WSannuaire.bean.ServerBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.DeployementEvent;
import com.fly.WSannuaire.events.ServerEvent;
import com.fly.WSannuaire.service.DeployementServices;
import com.fly.WSannuaire.service.ServerServices;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author EL HALAOUI Yassine
 *
 */
public class DeployementForm extends FormLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField tfName;
	private TextArea taDescription;
	private ComboBox<ServerBean> cbServer;

	private Button btnSave, btnDelete;

	private Binder<DeployementBean> binder;
	private ProjectBean projectBean;
	private DeployementBean deployementBean;

	public DeployementForm(ProjectBean projectBean) {
		binder = new Binder<>(DeployementBean.class);

		this.projectBean = projectBean;

		setSizeUndefined();
		tfName = new TextField("Nom");
		taDescription = new TextArea("Description");
		cbServer = new ComboBox<ServerBean>("Serveur");

		cbServer.setItems(ServerServices.getInstance().getservers());
		cbServer.setItemCaptionGenerator(ServerBean::getName);

		tfName.setWidth("250px");
		taDescription.setWidth("250px");
		cbServer.setWidth("250px");

		btnSave = new Button("Enregistrer");
		btnDelete = new Button("Supprimer");

		btnSave.setStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.setClickShortcut(KeyCode.ENTER);

		btnSave.addClickListener(e -> addOrSave());
		btnDelete.addClickListener(e -> delete());

		HorizontalLayout buttons = new HorizontalLayout(btnSave, btnDelete);
		addComponents(tfName, cbServer, taDescription, buttons);

		dataBinding();
		MyBus.getInstance().register(this);
	}

	@Subscribe
	public void upDateServerList(ServerEvent e) {
		cbServer.setItems(ServerServices.getInstance().getservers());
		cbServer.setItemCaptionGenerator(ServerBean::getName);
	}

	public void setCustomer(DeployementBean depolyementBean) {
		this.deployementBean = depolyementBean;
		binder.setBean(depolyementBean);
		setVisible(true);
	}

	void dataBinding() {
		binder.forField(tfName).asRequired("Ce champ est oblogatoire").bind(DeployementBean::getName, DeployementBean::setName);
		binder.forField(taDescription).bind(DeployementBean::getDescription, DeployementBean::setDescription);
		binder.forField(cbServer).asRequired("ce champ est obligatoire").bind(DeployementBean::getIdServer, DeployementBean::setIdServer);
	}

	void detectError() {
		if (tfName.isEmpty()) {
			tfName.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (cbServer.isEmpty()) {
			cbServer.setComponentError(new UserError("Ce champ est obligatoire"));
		}
	}

	void addOrSave() {
		if (binder.isValid()) {
			deployementBean.setIdProject(projectBean);
			DeployementServices.getInstance().addOrSave(deployementBean);
			setVisible(false);
			MyBus.getInstance().post(new DeployementEvent(deployementBean));
		} else {
			detectError();
		}
	}

	void delete() {
		try {
			DeployementServices.getInstance().delete(deployementBean);
			MyBus.getInstance().post(new DeployementEvent(deployementBean));
			setCustomer(new DeployementBean());
			setVisible(false);
		} catch (Exception e) {
			Notification.show("Erreur", "Des Base de données sont associé a ce déployment. Vous pouvez pas le supprimer", Notification.Type.ERROR_MESSAGE);
		}
	}

	public void enabledDelete(boolean isNew) {
		btnDelete.setEnabled(!isNew);
	}
}
