package com.fly.WSannuaire.view.webservice;

import java.util.ArrayList;
import java.util.List;

import com.fly.WSannuaire.bean.DomaineBean;
import com.fly.WSannuaire.bean.WebServiceBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.WebServiceEvent;
import com.fly.WSannuaire.service.WebServiceServices;
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
public class WebServiceView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	private TextField tfFilter;
	private Button btnClearFilter;
	private Button btnAddNew;
	private WebServiceForm form;

	private WebServicePopUp webServicePopUp;

	private Grid<WebServiceBean> grid;
	private List<WebServiceBean> WebServices;
	private DomaineBean domaineBean;

	public WebServiceView(DomaineBean domaineBean) {
		this.domaineBean = domaineBean;

		grid = new Grid<>(WebServiceBean.class);
		form = new WebServiceForm(domaineBean);

		HorizontalLayout mainLayout = new HorizontalLayout(grid, form);
		CssLayout filterLayout = new CssLayout();

		tfFilter = new TextField();
		btnClearFilter = new Button();

		form.setVisible(false);

		btnClearFilter.addClickListener(e -> {
			tfFilter.clear();
			displayGrid(domaineBean);
		});

		btnClearFilter.addStyleName(ValoTheme.BUTTON_DANGER);
		btnClearFilter.setIcon(VaadinIcons.CLOSE);

		tfFilter.addValueChangeListener(e -> {
			if (!e.getValue().isEmpty()) {
				search(e.getValue());
			} else {
				displayGrid(domaineBean);
			}
		});

		tfFilter.setPlaceholder("Rechercher");
		filterLayout.addComponents(tfFilter, btnClearFilter);
		filterLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		mainLayout.setSizeFull();
		grid.setSizeFull();
		mainLayout.setExpandRatio(grid, 1);

		btnAddNew = new Button("Nouveau Web service");
		btnAddNew.addClickListener(e -> {
			showPopUp(new WebServiceBean());
		});
		btnAddNew.setIcon(VaadinIcons.PLUS);

		grid.asSingleSelect().addValueChangeListener(e -> {
			grid.deselectAll();
		});

		HorizontalLayout hl = new HorizontalLayout(filterLayout, btnAddNew);
		displayGrid(domaineBean);
		addComponents(hl, mainLayout);

		MyBus.getInstance().register(this);
	}

	@Subscribe
	void updatGrid(WebServiceEvent e) {
		displayGrid(domaineBean);
		try {
			this.getUI().getUI().removeWindow(webServicePopUp);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void detach() {
		super.detach();
		MyBus.getInstance().unregister(this);
	}

	void showPopUp(WebServiceBean webServiceBean) {
		webServicePopUp = new WebServicePopUp(webServiceBean, domaineBean);
		this.getUI().getUI().addWindow(webServicePopUp);
	}

	void displayGrid(DomaineBean domaineBean) {
		WebServices = WebServiceServices.getInstance().getWebServices(domaineBean);
		grid.setItems(WebServices);
		settingGrid();
	}

	void search(String filterString) {
		List<WebServiceBean> WebServices = getWebServices(filterString);
		grid.setItems(WebServices);
		settingGrid();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	void settingGrid() {
		grid.setColumns("name", "urlBroker", "description", "methode", "developper");
		grid.getColumn("name").setCaption("Nom").setRenderer(new ButtonRenderer(e -> {
			showPopUp((WebServiceBean) e.getItem());
		}));
		grid.getColumn("urlBroker").setCaption("Url Broker");
		grid.getColumn("description").setCaption("Description");
		grid.getColumn("methode").setCaption("Methode");
		grid.getColumn("developper").setCaption("Développeur");
	}

	/**
	 * shearch in the list of web services using <b>name , methode ,
	 * developper</b> to shearch
	 * 
	 * @param stringFilter
	 * @return
	 */
	public List<WebServiceBean> getWebServices(String stringFilter) {
		List<WebServiceBean> webService = new ArrayList<>();
		for (WebServiceBean w : WebServices) {
			boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
					|| w.searchString().toLowerCase().contains(stringFilter.toLowerCase());
			if (passesFilter) {
				webService.add(w);
			}
		}
		return webService;
	}
}
