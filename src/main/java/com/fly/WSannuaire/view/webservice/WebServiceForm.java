package com.fly.WSannuaire.view.webservice;

import java.util.ArrayList;
import java.util.List;

import com.fly.WSannuaire.bean.DomaineBean;
import com.fly.WSannuaire.bean.WebServiceBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.WebServiceEvent;
import com.fly.WSannuaire.service.WebServiceServices;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author EL HALAOUI Yassine
 *
 */
public class WebServiceForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private TextField tfName;
	private TextArea taDescription;
	private TextField tfUrlDirect;
	private TextField tfUrlBroker;
	private ComboBox<String> cbMethode;
	private TextArea taMsgEntree;
	private TextArea taMsgRetour;
	private TextField tfDevelopper;

	private Button btnSave, btnDelete;

	private WebServiceBean webService;
	private DomaineBean domaineBean;
	private Binder<WebServiceBean> binder;

	public WebServiceForm(DomaineBean domaineBean) {
		this.domaineBean = domaineBean;

		binder = new Binder<>(WebServiceBean.class);

		setMargin(true);
		setSizeFull();

		tfName = new TextField("Nom");
		taDescription = new TextArea("Description");
		tfUrlDirect = new TextField("Url Direct");
		tfUrlBroker = new TextField("Url Broker");
		cbMethode = new ComboBox<String>("Methode");
		taMsgEntree = new TextArea("Message D'entrée");
		taMsgRetour = new TextArea("Message De retour");
		tfDevelopper = new TextField("Développeur");

		tfName.setWidth(100, Unit.PERCENTAGE);
		taDescription.setWidth(100, Unit.PERCENTAGE);
		tfUrlDirect.setWidth(100, Unit.PERCENTAGE);
		tfUrlBroker.setWidth(100, Unit.PERCENTAGE);
		cbMethode.setWidth(100, Unit.PERCENTAGE);
		taMsgEntree.setWidth(100, Unit.PERCENTAGE);
		taMsgRetour.setWidth(100, Unit.PERCENTAGE);
		tfDevelopper.setWidth(100, Unit.PERCENTAGE);

		cbMethode.setItems(methods());

		btnSave = new Button("enregistrer");
		btnDelete = new Button("supprimer");

		HorizontalLayout buttons = new HorizontalLayout(btnSave, btnDelete);

		btnSave.setStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.setClickShortcut(KeyCode.ENTER);

		btnSave.addClickListener(e -> addOrSave());
		btnDelete.addClickListener(e -> delete());

		bindingData();

		addComponents(tfName, taDescription, tfUrlDirect, tfUrlBroker, cbMethode, taMsgEntree, taMsgRetour, tfDevelopper, buttons);
	}

	public void setCustomer(WebServiceBean webService) {
		this.webService = webService;
		this.webService.setIdDomaine(domaineBean);
		binder.setBean(webService);
		if (webService.getIdWebService() == null) {
			enabledDelete(true);
		}
		tfName.selectAll();
	}

	void bindingData() {
		binder.forField(tfName).asRequired("Ce champ est obligatoire").bind(WebServiceBean::getName, WebServiceBean::setName);
		binder.forField(taDescription).bind(WebServiceBean::getDescription, WebServiceBean::setDescription);
		binder.forField(tfUrlDirect).asRequired("Ce champ est obligatoire").bind(WebServiceBean::getUrlDirect, WebServiceBean::setUrlDirect);
		binder.forField(tfUrlBroker).asRequired("Ce champ est obligatoire").bind(WebServiceBean::getUrlBroker, WebServiceBean::setUrlBroker);
		binder.forField(cbMethode).asRequired("Ce champ est obligatoire").bind(WebServiceBean::getMethode, WebServiceBean::setMethode);
		binder.forField(taMsgEntree).asRequired("Ce champ est obligatoire").bind(WebServiceBean::getMsgEntree, WebServiceBean::setMsgEntree);
		binder.forField(taMsgRetour).asRequired("Ce champ est obligatoire").bind(WebServiceBean::getMsgRetour, WebServiceBean::setMsgRetour);
		binder.forField(tfDevelopper).asRequired("Ce champ est obligatoire").bind(WebServiceBean::getDevelopper, WebServiceBean::setDevelopper);
	}

	void addOrSave() {
		if (binder.isValid()) {
			WebServiceServices.getInstance().addOrSave(webService);
			MyBus.getInstance().post(new WebServiceEvent(webService));
		} else {
			detectError();
		}
		tfName.selectAll();
	}

	void delete() {
		WebServiceServices.getInstance().remove(webService);
		MyBus.getInstance().post(new WebServiceEvent(webService));
		setCustomer(new WebServiceBean());
	}

	void detectError() {
		if (tfName.isEmpty()) {
			tfName.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (taDescription.isEmpty()) {
			taDescription.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (tfUrlDirect.isEmpty()) {
			tfUrlDirect.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (tfUrlBroker.isEmpty()) {
			tfUrlBroker.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (cbMethode.isEmpty()) {
			cbMethode.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (taMsgEntree.isEmpty()) {
			taMsgEntree.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (taMsgRetour.isEmpty()) {
			taMsgRetour.setComponentError(new UserError("Ce champ est obligatoire"));
		}
		if (tfDevelopper.isEmpty()) {
			tfDevelopper.setComponentError(new UserError("Ce champ est obligatoire"));
		}
	}

	public void enabledDelete(boolean isNew) {
		btnDelete.setEnabled(!isNew);
	}

	List<String> methods() {
		List<String> methods = new ArrayList<>();
		methods.add("Post");
		methods.add("Get");
		methods.add("Put");
		methods.add("Delete");
		return methods;
	}
}
