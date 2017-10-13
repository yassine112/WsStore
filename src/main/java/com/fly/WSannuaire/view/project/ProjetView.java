package com.fly.WSannuaire.view.project;

import java.util.ArrayList;
import java.util.List;

import com.fly.WSannuaire.bean.ProjectBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.ProjectEvent;
import com.fly.WSannuaire.service.ProjectServices;
import com.google.common.eventbus.Subscribe;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author EL HALAOUI Yassine
 *
 */
public class ProjetView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	private TextField tfFilter;
	private Button btnClearFilter;
	private Button btnAddNew;

	private Grid<ProjectBean> grid;
	private List<ProjectBean> projets;

	private ProjectPopUp window;

	public ProjetView() {
		grid = new Grid<>(ProjectBean.class);

		CssLayout filterLayout = new CssLayout();

		tfFilter = new TextField();
		btnClearFilter = new Button();

		btnClearFilter.addClickListener(e -> {
			tfFilter.clear();
			displayGrid();
		});
		btnClearFilter.addStyleName(ValoTheme.BUTTON_DANGER);
		btnClearFilter.setIcon(VaadinIcons.CLOSE);

		tfFilter.addValueChangeListener(e -> {

			if (!e.getValue().isEmpty()) {
				displayGrid(e.getValue());
			} else {
				displayGrid();
			}

		});

		tfFilter.setPlaceholder("Rechercher");
		filterLayout.addComponents(tfFilter, btnClearFilter);
		filterLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		grid.setSizeFull();

		btnAddNew = new Button("Nouveau projet");
		btnAddNew.addClickListener(e -> {
			showPopUp(new ProjectBean());
		});
		btnAddNew.setIcon(VaadinIcons.PLUS);

		grid.asSingleSelect().addValueChangeListener(e -> {
			grid.deselectAll();
		});

		HorizontalLayout hl = new HorizontalLayout(filterLayout, btnAddNew);
		displayGrid();
		addComponents(hl, grid);

		MyBus.getInstance().register(this);
	}

	@Subscribe
	private void updateGrid(ProjectEvent e) {
		displayGrid();

		if (e.projectBean == null) {
			this.getUI().getUI().removeWindow(window);
		}

	}

//	@Override
//	public void detach() {
//		super.detach();
//		try {
//			MyBus.getInstance().unregister(this);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}

	private void displayGrid() {
		projets = ProjectServices.getInstance().getProjets();
		grid.setItems(projets);
		settingGrid();
	}

	private void displayGrid(String word) {
		List<ProjectBean> projets = getProjets(word);
		grid.setItems(projets);
		settingGrid();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void settingGrid() {
		grid.setColumns("name", "description", "cvsName");
		grid.getColumn("name").setCaption("Nom").setRenderer(new ButtonRenderer(e -> {
			showPopUp((ProjectBean) e.getItem());
		}));
		grid.getColumn("description").setCaption("Déscription");
		grid.getColumn("cvsName").setCaption("Nom CVS");
	}

	void showPopUp(ProjectBean projectBean) {
		window = new ProjectPopUp(projectBean);
		this.getUI().getUI().addWindow(window);
	}

	/**
	 * filtering array by name, description et nameCVS
	 * 
	 * @param stringFilter
	 * @return
	 */
	public List<ProjectBean> getProjets(String stringFilter) {
		ArrayList<ProjectBean> arrayList = new ArrayList<>();
		try {
			for (ProjectBean p : projets) {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty()) || p.searchString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(p);
				}
			}
		} finally {

		}
		return arrayList;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}
