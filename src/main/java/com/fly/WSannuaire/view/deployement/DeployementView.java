package com.fly.WSannuaire.view.deployement;

import java.util.ArrayList;
import java.util.List;

import com.fly.WSannuaire.bean.DeployementBean;
import com.fly.WSannuaire.bean.ProjectBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.DeployementEvent;
import com.fly.WSannuaire.service.DeployementServices;
import com.google.common.eventbus.Subscribe;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class DeployementView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField tfFilter;
	private Button btnClearFilter;
	private Button btnAddNew;
	private DeployementForm form;

	private Grid<DeployementBean> grid;
	private List<DeployementBean> deployements;
	private ProjectBean projectBean;

	public DeployementView(ProjectBean projectBean) {
		this.projectBean = projectBean;

		grid = new Grid<>(DeployementBean.class);
		grid.setSizeFull();
		grid.asSingleSelect().addValueChangeListener(e -> {
			grid.deselectAll();
		});

		form = new DeployementForm(projectBean);
		form.setVisible(false);

		CssLayout filterLayout = new CssLayout();

		tfFilter = new TextField();
		tfFilter.setPlaceholder("Rechercher");
		tfFilter.addValueChangeListener(e -> {
			if (!e.getValue().isEmpty()) {
				search(e.getValue());
			} else {
				displayGrid();
			}
		});

		btnClearFilter = new Button();
		btnClearFilter.addStyleName(ValoTheme.BUTTON_DANGER);
		btnClearFilter.setIcon(VaadinIcons.CLOSE);
		btnClearFilter.addClickListener(e -> {
			tfFilter.clear();
			displayGrid();
		});

		filterLayout.addComponents(tfFilter, btnClearFilter);
		filterLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		btnAddNew = new Button("Nouveau déployment");
		btnAddNew.setIcon(VaadinIcons.PLUS);
		btnAddNew.addClickListener(e -> {
			grid.asSingleSelect().clear();
			form.setCustomer(new DeployementBean());
			form.enabledDelete(true);
		});

		HorizontalLayout mainLayout = new HorizontalLayout(grid, form);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(grid, 1);

		HorizontalLayout hl = new HorizontalLayout(filterLayout, btnAddNew);
		addComponents(hl, mainLayout);

		displayGrid();
		MyBus.getInstance().register(this);
	}

	@Subscribe
	public void updatGrid(DeployementEvent e) {
		displayGrid();
	}
	
	@Override
	public void detach() {
		super.detach();
		MyBus.getInstance().unregister(this);
	}

	private void displayGrid() {
		deployements = DeployementServices.getInstance().getDeployements(projectBean);
		grid.setItems(deployements);
		settingGrid();
	}

	private void search(String filterString) {
		List<DeployementBean> deployements = getDeployements(filterString);
		grid.setItems(deployements);
		settingGrid();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void settingGrid() {
		grid.setColumns("name", "description", "idProject");
		grid.getColumn("name").setCaption("Name").setRenderer(new ButtonRenderer(e -> {
			form.setCustomer((DeployementBean) e.getItem());
			form.enabledDelete(false);
		}));
		grid.getColumn("description").setCaption("Description");
		grid.getColumn("idProject").setCaption("Project");
		grid.addColumn(DeployementBean -> "List Des Base de données", new ButtonRenderer(e -> {
			
			this.getUI().getUI().addWindow(new DeployementDataBases((DeployementBean) e.getItem()));

		}));
	}
	
	/**
	 * filtring deployments list by name and project name
	 * 
	 * @param filterString
	 * @return
	 */
	public List<DeployementBean> getDeployements(String filterString) {
		List<DeployementBean> deployement = new ArrayList<>();

		for (DeployementBean d : deployements) {
			boolean passesFilter = (filterString == null || filterString.isEmpty()) || d.searchString().toLowerCase().contains(filterString.toLowerCase());
			if (passesFilter) {
				deployement.add(d);
			}
		}

		return deployement;
	}

}
