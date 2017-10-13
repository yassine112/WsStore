package com.fly.WSannuaire.view.mainview;

import com.fly.WSannuaire.MyUI;
import com.fly.WSannuaire.bean.UserBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.UserLogout;
import com.fly.WSannuaire.view.database.DataBaseView;
import com.fly.WSannuaire.view.project.ProjetView;
import com.fly.WSannuaire.view.server.ServerView;
import com.fly.WSannuaire.view.user.UserView;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings({ "serial", "deprecation" })
public class MainView extends VerticalLayout {

	SideBar root = new SideBar();
	ComponentContainer viewDisplay = root.getContentContainer();
	CssLayout menu = new CssLayout();
	CssLayout menuItemsLayout = new CssLayout();
	Navigator nav = new Navigator(MyUI.getCurrent(), viewDisplay);

	public MainView() {
		setHeight(100, Unit.PERCENTAGE);
		Responsive.makeResponsive(this);
		addComponent(root);

		root.setWidth("100%");
		root.setHeight(100, Unit.PERCENTAGE);

		root.addMenu(buildMenu());
		addStyleName(ValoTheme.UI_WITH_MENU);
		setMargin(false);

		nav.addView("", new ProjetView());
		nav.addView("database", new DataBaseView());
		nav.addView("server", new ServerView());
		nav.addView("users", new UserView());
		nav.setErrorView(new ProjetView());

		// Defaulte page to show
		nav.navigateTo("");

	}

	CssLayout buildMenu() {

		HorizontalLayout top = new HorizontalLayout();
		top.setWidth("100%");
		top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		top.addStyleName(ValoTheme.MENU_TITLE);
		menu.addComponent(top);

		Button showMenu = new Button("Menu", new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (menu.getStyleName().contains("valo-menu-visible")) {
					menu.removeStyleName("valo-menu-visible");
				} else {
					menu.addStyleName("valo-menu-visible");
				}
			}
		});
		showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
		showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
		showMenu.addStyleName("valo-menu-toggle");
		showMenu.setIcon(FontAwesome.LIST);
		menu.addComponent(showMenu);

		Label title = new Label("<h3>Annuaire <strong>Web Services</strong></h3>", ContentMode.HTML);
		title.setSizeUndefined();
		top.addComponent(title);
		top.setExpandRatio(title, 1);

		menuItemsLayout.setPrimaryStyleName("valo-menuitems");
		menu.addComponent(menuItemsLayout);

		Label label = null;

		label = new Label("Menu", ContentMode.HTML);
		label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
		label.addStyleName(ValoTheme.LABEL_H4);
		label.addStyleName("sideBarItems");
		label.setSizeUndefined();
		menuItemsLayout.addComponent(label);

		Button b;

		/**
		 * Menu Item Navigate to Project View
		 */
		b = new Button("<font color=\"#56b8ff\">Projet</font>", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				nav.navigateTo("");
			}
		});
		b.setIcon(FontAwesome.CUBES);
		b.setHtmlContentAllowed(true);
		b.setPrimaryStyleName(ValoTheme.MENU_ITEM);

		menuItemsLayout.addComponent(b);

		/**
		 * Menu Item Navigate to DataBase View
		 */
		b = new Button("<font color=\"#56b8ff\">Base de donnée</font>", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				nav.navigateTo("database");
			}
		});
		b.setIcon(FontAwesome.DATABASE);
		b.setHtmlContentAllowed(true);
		b.setPrimaryStyleName(ValoTheme.MENU_ITEM);

		menuItemsLayout.addComponent(b);

		/**
		 * Menu Item Navigate to Server View
		 */
		b = new Button("<font color=\"#56b8ff\">Serveurs</font>", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				nav.navigateTo("server");
			}
		});
		b.setIcon(FontAwesome.SERVER);
		b.setHtmlContentAllowed(true);
		b.setPrimaryStyleName(ValoTheme.MENU_ITEM);

		menuItemsLayout.addComponent(b);

		/**
		 * Menu Item Navigate to Users View
		 */
		b = new Button("<font color=\"#56b8ff\">Utilisateurs</font>", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				nav.navigateTo("users");
			}
		});
		b.setIcon(FontAwesome.USER);
		b.setHtmlContentAllowed(true);
		b.setPrimaryStyleName(ValoTheme.MENU_ITEM);

		menuItemsLayout.addComponent(b);

		/**
		 * Menu Item Logout
		 */
		b = new Button("<font color=\"#ed473b\">Déconnéxion</font>", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				VaadinSession.getCurrent().setAttribute(UserBean.class, null);
				MyBus.getInstance().post(new UserLogout());
			}
		});
		b.setIcon(FontAwesome.SIGN_OUT);
		b.setHtmlContentAllowed(true);
		b.setPrimaryStyleName(ValoTheme.MENU_ITEM);

		menuItemsLayout.addComponent(b);

		return menu;
	}

}
