package com.fly.WSannuaire.view.project;

import com.fly.WSannuaire.bean.ProjectBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.AddTabEvent;
import com.fly.WSannuaire.events.ProjectEvent;
import com.fly.WSannuaire.view.deployement.DeployementView;
import com.fly.WSannuaire.view.domaine.DomaineView;
import com.fly.WSannuaire.view.webservice.WebServiceView;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * @author EL HALAOUI Yassine
 *
 */
public class ProjectPopUp extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Panel panel;
	private ProjectForm projectForm;
	private DomaineView domaineView;
	private WebServiceView webServiceView;
	private DeployementView deployementView;

	private TabSheet projectTabSheet;
	private TabSheet domaineTabSheet;

	public ProjectPopUp(ProjectBean projectBean) {
		super("Projet");
		settingWindow();
		projectTabSheet = new TabSheet();
		
		// Form du projet : ajouter modifier et supprimer un projet
		projectForm = new ProjectForm();
		projectForm.setCustomer(projectBean);
		panel = new Panel(projectForm);
		projectTabSheet.addTab(panel, "Information sur le projet");

		// Domaine and web service
		panel = new Panel(buildTabSheetDomaine(projectBean));
		projectTabSheet.addTab(panel, "Domaines associé au projet");

		// Grid affiche les deployemnt du projet
		deployementView = new DeployementView(projectBean);
		deployementView.setSizeFull();
		panel = new Panel(deployementView);
		projectTabSheet.addTab(panel, "Déployment associé au projet");

		// vertical layout added just for adding marging to content of PopUp
		VerticalLayout verticalLayout = new VerticalLayout(projectTabSheet);
		verticalLayout.setMargin(true);
		setContent(verticalLayout);

		if (projectBean.getIdProject() == null) {
			projectTabSheet.getTab(1).setEnabled(false);
			projectTabSheet.getTab(2).setEnabled(false);
		}

		MyBus.getInstance().register(this);
	}

	@Subscribe
	private void updateGrid(ProjectEvent e) {
		if (e.projectBean.getIdProject() == null) {
			projectTabSheet.getTab(1).setEnabled(false);
			projectTabSheet.getTab(2).setEnabled(false);
		} else {
			projectTabSheet.getTab(1).setEnabled(true);
			projectTabSheet.getTab(2).setEnabled(true);
		}
	}

	@Subscribe
	public void anableTab(AddTabEvent e) {
		webServiceView = new WebServiceView(e.domaineBean);
		// Added right now
//		Constant.domaineUrl = e.domaineBean.getDomaineUrl();
		webServiceView.setSizeFull();
		domaineTabSheet.addTab(webServiceView, "Web Services du : " + e.domaineBean.getName()).setClosable(true);
		domaineTabSheet.setSelectedTab(webServiceView);
	}

	/**
	 * 
	 * @param projectBean
	 * @return
	 */
	TabSheet buildTabSheetDomaine(ProjectBean projectBean) {
		domaineTabSheet = new TabSheet();

		// Domaine view and domaine form
		domaineView = new DomaineView(projectBean);
		domaineView.setSizeFull();
		domaineTabSheet.addTab(domaineView, "Information du domaine");

		return domaineTabSheet;
	}

	/**
	 * 
	 */
	void settingWindow() {
		setWidth(80, Unit.PERCENTAGE);
		setHeight(80, Unit.PERCENTAGE);
		setResizable(false);
		setModal(true);
		center();
	}

}
