package com.fly.WSannuaire.view.deployement;

import java.util.List;

import com.fly.WSannuaire.bean.DataBaseBean;
import com.fly.WSannuaire.bean.DeployementBean;
import com.fly.WSannuaire.common.MyBus;
import com.fly.WSannuaire.events.DataBaseEvent;
import com.fly.WSannuaire.service.DataBaseServices;
import com.fly.WSannuaire.service.DeployementDatabaseServices;
import com.google.common.eventbus.Subscribe;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;

/**
 * @author EL HALAOUI Yassine
 * @Date 11 sept. 2017
 *
 */
public class DeployementDataBases extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ComboBox<DataBaseBean> cbDataBases;
	private Button btnAddDataBase;
	private Grid<DataBaseBean> grid;

	private DeployementBean deployementBean;

	public DeployementDataBases(DeployementBean deployementBean) {
		super("Associé les base de donnée au déployment");
		settingWindow();
		this.deployementBean = deployementBean;

		grid = new Grid<>(DataBaseBean.class);
		grid.setSizeFull();
		cbDataBases = new ComboBox<DataBaseBean>();
		cbDataBases.setPlaceholder("Base de donnée");
		cbDataBases.setSizeFull();
		cbDataBases.setItems(DataBaseServices.getInstance().getDataBases());
		cbDataBases.setItemCaptionGenerator(DataBaseBean::getName);

		btnAddDataBase = new Button("ajouter base de donnée");
		btnAddDataBase.setIcon(VaadinIcons.PLUS);
		btnAddDataBase.addClickListener(e -> addDataBase());

		HorizontalLayout layout = new HorizontalLayout(cbDataBases, btnAddDataBase);
		layout.setSizeFull();

		VerticalLayout mainLayout = new VerticalLayout(layout, grid);

		displayGrid();

		setContent(mainLayout);
		MyBus.getInstance().register(this);
	}

	@Subscribe
	public void updatGrid(DataBaseEvent e) {
		cbDataBases.setItems(DataBaseServices.getInstance().getDataBases());
	}

	private void addDataBase() {
		if (cbDataBases.isEmpty()) {
			cbDataBases.setComponentError(new UserError("Merci de choisir une base de donnée"));
			return;
		}
		DeployementDatabaseServices.getinstance().addDatabase(deployementBean, cbDataBases.getValue());
		displayGrid();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void displayGrid() {
		List<DataBaseBean> databases = DeployementDatabaseServices.getinstance().getDataBases(deployementBean);
		grid.setItems(databases);
		grid.setColumns("name", "url", "type");
		grid.addColumn(DataBaseBean -> "Delete", new ButtonRenderer(e -> {
			DeployementDatabaseServices.getinstance().removeDataBase(deployementBean, (DataBaseBean) e.getItem());
			displayGrid();
		}));
	}

	void settingWindow() {
		setWidth(60, Unit.PERCENTAGE);
		setHeight(80, Unit.PERCENTAGE);
		setResizable(false);
		setModal(true);
		center();
	}
}
