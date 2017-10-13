package com.fly.WSannuaire.view.domaine;

import java.util.ArrayList;
import java.util.List;

import com.fly.WSannuaire.bean.DomaineBean;
import com.fly.WSannuaire.bean.ProjectBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.AddTabEvent;
import com.fly.WSannuaire.events.DomaineEvent;
import com.fly.WSannuaire.service.DomaineServices;
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

/**
 * @author EL HALAOUI Yassine
 *
 */
public class DomaineView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	private TextField tfFilter;
	private Button btnClearFilter;
	private Button btnAddNew;
	private DomaineForm form;

	private Grid<DomaineBean> grid;
	private List<DomaineBean> domaines;

	private ProjectBean projectBean;

	public DomaineView(ProjectBean projectBean) {
		grid = new Grid<>(DomaineBean.class);
		form = new DomaineForm(projectBean);
		this.projectBean = projectBean;

		form.setVisible(false);

		tfFilter = new TextField();
		tfFilter.setPlaceholder("Rechercher");
		btnClearFilter = new Button();
		btnClearFilter.setIcon(VaadinIcons.CLOSE);
		btnClearFilter.addStyleName(ValoTheme.BUTTON_DANGER);

		btnAddNew = new Button("Nouveau domaine");
		btnAddNew.setIcon(VaadinIcons.PLUS);

		tfFilter.addValueChangeListener(e -> {
			if (!e.getValue().isEmpty()) {
				displayGrid(e.getValue());
			} else {
				displayGrid();
			}
		});

		btnClearFilter.addClickListener(e -> {
			tfFilter.clear();
			displayGrid();
		});

		btnAddNew.addClickListener(e -> {
			grid.asSingleSelect().clear();
			form.setCustomer(new DomaineBean());
			form.enabledDelete(true);
		});

		CssLayout filterLayout = new CssLayout();
		HorizontalLayout mainLayout = new HorizontalLayout(grid, form);
		HorizontalLayout hl = new HorizontalLayout(filterLayout, btnAddNew);

		filterLayout.addComponents(tfFilter, btnClearFilter);
		filterLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(grid, 1);

		grid.setSizeFull();
		grid.asSingleSelect().addValueChangeListener(e -> {
			grid.deselectAll();
		});

		displayGrid();

		addComponents(hl, mainLayout);
		MyBus.getInstance().register(this);
	}

	@Subscribe
	private void upDateGrid(DomaineEvent e) {
		displayGrid();
	}

	@Override
	public void detach() {
		super.detach();
		MyBus.getInstance().unregister(this);
	}

	private void displayGrid() {
		domaines = DomaineServices.getInstance().getDomaines(projectBean);
		grid.setItems(domaines);
		settingGrid();
	}

	private void displayGrid(String filterString) {
		List<DomaineBean> domaines = getDomaines(filterString);
		grid.setItems(domaines);
		settingGrid();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void settingGrid() {

		grid.setColumns("name", "description", "idProject");
		grid.getColumn("description").setCaption("Description");
		grid.getColumn("idProject").setCaption("Projet");
		grid.getColumn("name").setCaption("Nom").setRenderer(new ButtonRenderer(e -> {

			if (e.getItem() == null) {
				form.setVisible(false);
			} else {
				form.setCustomer((DomaineBean) e.getItem());
				form.enabledDelete(false);
			}
		}));
		grid.addColumn(DomaineBean -> "List web service", new ButtonRenderer(e -> {
			MyBus.getInstance().post(new AddTabEvent((DomaineBean) e.getItem()));
		}));

	}

	/**
	 * filtering array by Name and project
	 * 
	 * @param filterString
	 * @return
	 */
	public List<DomaineBean> getDomaines(String filterString) {
		List<DomaineBean> domaineBeans = new ArrayList<>();

		for (DomaineBean d : domaines) {
			boolean passesFilter = (filterString == null || filterString.isEmpty()) || d.searchString().toLowerCase().contains(filterString.toLowerCase());
			if (passesFilter) {
				domaineBeans.add(d);
			}
		}

		return domaineBeans;
	}

}
