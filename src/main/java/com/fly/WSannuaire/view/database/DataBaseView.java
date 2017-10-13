package com.fly.WSannuaire.view.database;

import java.util.ArrayList;
import java.util.List;

import com.fly.WSannuaire.bean.DataBaseBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.DataBaseEvent;
import com.fly.WSannuaire.service.DataBaseServices;
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
public class DataBaseView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	private TextField tfFilter;
	private Button btnClearFilter;
	private Button btnAddNew;
	private DataBaseForm form;

	private Grid<DataBaseBean> grid;
	private List<DataBaseBean> dataBases;

	/**
	 * Construct the page
	 */
	public DataBaseView() {
		form = new DataBaseForm();
		grid = new Grid<>(DataBaseBean.class);
		HorizontalLayout mainLayout = new HorizontalLayout(grid, form);
		CssLayout filterLayout = new CssLayout();

		form.setVisible(false);
		tfFilter = new TextField();
		btnClearFilter = new Button();

		btnClearFilter.addClickListener(e -> {
			tfFilter.clear();
			displayGrid();
		});
		btnClearFilter.addStyleName(ValoTheme.BUTTON_DANGER);

		tfFilter.addValueChangeListener(e -> {

			if (!e.getValue().isEmpty()) {
				search(e.getValue());
			} else {
				displayGrid();
			}
		});

		grid.setSizeFull();
		grid.asSingleSelect().addValueChangeListener(e -> {
			grid.deselectAll();
		});
		tfFilter.setPlaceholder("Rechercher");

		btnClearFilter.setIcon(VaadinIcons.CLOSE_SMALL);
		filterLayout.addComponents(tfFilter, btnClearFilter);
		filterLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		mainLayout.setSizeFull();
		grid.setSizeFull();
		mainLayout.setExpandRatio(grid, 1);

		btnAddNew = new Button("nouvelle Base de donnée");
		btnAddNew.addClickListener(e -> {
			grid.asSingleSelect().clear();
			form.setCustomer(new DataBaseBean());
			form.enabledDelete(true);
		});
		btnAddNew.setIcon(VaadinIcons.PLUS);

		HorizontalLayout hl = new HorizontalLayout(filterLayout, btnAddNew);
		displayGrid();
		addComponents(hl, mainLayout);

		MyBus.getInstance().register(this);
	}

	@Subscribe
	public void updatGrid(DataBaseEvent e) {
		displayGrid();
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

	/**
	 * load data from database to grid
	 */
	public void displayGrid() {
		dataBases = DataBaseServices.getInstance().getDataBases();
		grid.setItems(dataBases);
		settingGrid();
	}

	public void search(String filtringString) {
		List<DataBaseBean> dataBases = getDataBases(filtringString);
		grid.setItems(dataBases);
		settingGrid();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void settingGrid() {
		grid.setColumns("name", "url", "login", "type", "remarque");
		grid.getColumn("name").setCaption("Nom").setRenderer(new ButtonRenderer(e -> {
			if (e.getItem() == null) {
				form.setVisible(false);
			} else {
				form.setCustomer((DataBaseBean) e.getItem());
				form.enabledDelete(false);
			}
		}));
	}

	/**
	 * Rechercher dans la grid
	 * 
	 * @param stringFilter
	 * @return
	 */
	public synchronized List<DataBaseBean> getDataBases(String stringFilter) {
		ArrayList<DataBaseBean> arrayList = new ArrayList<>();
		try {
			for (DataBaseBean u : dataBases) {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty()) || u.stringSearch().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(u);
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
