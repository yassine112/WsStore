package com.fly.WSannuaire.view.user;

import java.util.ArrayList;
import java.util.List;

import com.fly.WSannuaire.bean.UserBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.UserEvent;
import com.fly.WSannuaire.service.UserServices;
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
public class UserView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	private TextField tfFilter;
	private Button btnClearFilter;
	private Button btnAddNew;
	private UserForm form;

	private Grid<UserBean> grid;
	private List<UserBean> Users;

	public UserView() {

		grid = new Grid<>(UserBean.class);
		grid.setSizeFull();
		grid.asSingleSelect().addValueChangeListener(e -> {
			grid.deselectAll();
		});

		form = new UserForm();
		form.setVisible(false);

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

		CssLayout filterLayout = new CssLayout();
		filterLayout.addComponents(tfFilter, btnClearFilter);
		filterLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		HorizontalLayout mainLayout = new HorizontalLayout(grid, form);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(grid, 1);

		btnAddNew = new Button("Nouveau utilisateur");
		btnAddNew.setIcon(VaadinIcons.PLUS);
		btnAddNew.addClickListener(e -> {
			grid.asSingleSelect().clear();
			form.setCustomer(new UserBean());
			form.enabledDelete(true);
		});

		HorizontalLayout hl = new HorizontalLayout(filterLayout, btnAddNew);
		displayGrid();
		addComponents(hl, mainLayout);

		MyBus.getInstance().register(this);
	}

	@Subscribe
	public void updatGrid(UserEvent e) {
		displayGrid();
	}
	
//	@Override
//	public void detach() {
//		super.detach();
//		MyBus.getInstance().unregister(this);
//	}

	public void displayGrid() {
		Users = UserServices.getInstance().getUsers();
		grid.setItems(Users);
		settingGrid();
	}

	public void search(String filterString) {
		List<UserBean> Users = getUsers(filterString);
		grid.setItems(Users);
		settingGrid();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void settingGrid() {
		grid.setColumns("login", "fname", "lname");
		grid.getColumn("fname").setCaption("Prénom");
		grid.getColumn("lname").setCaption("Nom");
		grid.getColumn("login").setCaption("Login").setRenderer(new ButtonRenderer(e -> {

			if (e.getItem() == null) {
				form.setVisible(false);
			} else {
				form.setCustomer((UserBean) e.getItem());
				form.enabledDelete(false);
			}

		}));
	}

	/**
	 * Looking for Users Using First Name, Last Name and Login
	 * 
	 * @param stringFilter
	 * @return
	 */
	public synchronized List<UserBean> getUsers(String stringFilter) {
		ArrayList<UserBean> arrayList = new ArrayList<>();
		try {
			for (UserBean u : Users) {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty()) || u.toString().toLowerCase().contains(stringFilter.toLowerCase());
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
