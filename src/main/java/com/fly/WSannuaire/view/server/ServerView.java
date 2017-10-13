package com.fly.WSannuaire.view.server;

import java.util.ArrayList;
import java.util.List;

import com.fly.WSannuaire.bean.ServerBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.ServerEvent;
import com.fly.WSannuaire.service.ServerServices;
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
public class ServerView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	private TextField tfFilter;
	private Button btnClearFilter;
	private Button btnAddNew;
	private ServerForm form;

	private Grid<ServerBean> grid;
	private List<ServerBean> servers;

	public ServerView() {
		form = new ServerForm();
		grid = new Grid<>(ServerBean.class);
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

		tfFilter.setPlaceholder("Rechercher");
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

		btnClearFilter.setIcon(VaadinIcons.CLOSE_SMALL);
		filterLayout.addComponents(tfFilter, btnClearFilter);
		filterLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		mainLayout.setSizeFull();
		grid.setSizeFull();
		mainLayout.setExpandRatio(grid, 1);

		btnAddNew = new Button("Nouveau serveur");
		btnAddNew.addClickListener(e -> {
			grid.asSingleSelect().clear();
			form.setCustomer(new ServerBean());
			form.enabledDelete(true);
		});
		btnAddNew.setIcon(VaadinIcons.PLUS);

		HorizontalLayout hl = new HorizontalLayout(filterLayout, btnAddNew);
		displayGrid();
		addComponents(hl, mainLayout);

		MyBus.getInstance().register(this);
	}

	@Subscribe
	public void updatGrid(ServerEvent e) {
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

	private void search(String word) {
		List<ServerBean> servers = getservers(word);
		grid.setItems(servers);
		settingGrid();
	}

	private void displayGrid() {
		servers = ServerServices.getInstance().getservers();
		grid.setItems(servers);
		settingGrid();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void settingGrid() {
		grid.setColumns("name", "ipAdress", "sysExploitation", "localisation", "type", "remarque");
		grid.getColumn("ipAdress").setCaption("Adress IP");
		grid.getColumn("name").setCaption("Nom").setRenderer(new ButtonRenderer(e -> {
			if (e.getItem() == null) {
				form.setVisible(false);
			} else {
				form.setCustomer((ServerBean) e.getItem());
				form.enabledDelete(false);
			}
		}));
		;
		grid.getColumn("sysExploitation").setCaption("System d'exploitation");
		grid.getColumn("localisation").setCaption("Localisation");
		grid.getColumn("type").setCaption("Type");
		grid.getColumn("remarque").setCaption("Remarque");
	}

	/**
	 * Looking for Server Using : <b>ipAdress, name, sysExploitation,
	 * localisation, remarque</b>
	 * 
	 * @param stringFilter
	 * @return
	 */
	public synchronized List<ServerBean> getservers(String stringFilter) {
		ArrayList<ServerBean> arrayList = new ArrayList<>();
		try {
			for (ServerBean s : servers) {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty()) || s.stringSearch().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(s);
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
