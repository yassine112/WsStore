package com.fly.WSannuaire.service;

import java.util.List;

import com.fly.WSannuaire.bean.DataBaseBean;
import com.fly.WSannuaire.common.EntityManagerHelper;

/**
 * @author EL HALAOUI Yassine
 *
 */
public class DataBaseServices extends EntityManagerHelper {

	private static DataBaseServices instance;
	private List<DataBaseBean> dataBases;

	public DataBaseServices() {
		super();
	}

	public static DataBaseServices getInstance() {
		if (instance == null) {
			instance = new DataBaseServices();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public List<DataBaseBean> getDataBases() {
		try {
			dataBases = getEntityManager().createNamedQuery("getDataBases").getResultList();
		} finally {

		}
		return dataBases;
	}

	/**
	 * Get DataBases By Type
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DataBaseBean> getDataBasesByType(String type) {
		try {

			dataBases = getEntityManager().createNamedQuery("getDataBasesByType").setParameter("type", type).getResultList();

		} finally {

		}
		return dataBases;
	}

	/**
	 * Get Databases where type is null
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DataBaseBean> getDataBasesWithoutType() {
		try {

			dataBases = getEntityManager().createNamedQuery("getDataBasesWithoutType").getResultList();

		} finally {

		}
		return dataBases;
	}

	/**
	 * If The database Exists We Edit it If The database Not Exists We Add A New
	 * One
	 * 
	 * @param database
	 */
	public void addOrSave(DataBaseBean database) {
		try {

			if (database.getIdDataBase() == null) {
				getEntityManager().persist(database);
			} else {
//				DataBaseBean oldBean = getEntityManager().find(DataBaseBean.class, database.getIdDataBase());
//				oldBean.copyProperties(database);
//				getEntityManager().persist(oldBean);
				
				getEntityManager().merge(database);
			}
			getEntityManager().getTransaction().commit();

		} finally {

		}

	}

	/**
	 * Remove User
	 * 
	 * @param User
	 */
	public void remove(DataBaseBean database) {
		if (!getEntityManager().contains(database)) {
			database = getEntityManager().merge(database);
		}
		getEntityManager().remove(database);
		getEntityManager().getTransaction().commit();
	}
}
