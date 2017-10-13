package com.fly.WSannuaire.service;

import java.util.List;

import com.fly.WSannuaire.bean.DataBaseBean;
import com.fly.WSannuaire.bean.DeployementBean;
import com.fly.WSannuaire.common.EntityManagerHelper;

/**
 * @author EL HALAOUI Yassine
 * @Date 11 sept. 2017
 *
 */
public class DeployementDatabaseServices extends EntityManagerHelper {

	private static DeployementDatabaseServices instance;
	private List<DataBaseBean> dataBases;

	public DeployementDatabaseServices() {
	}

	public static DeployementDatabaseServices getinstance() {
		if (instance == null) {
			instance = new DeployementDatabaseServices();
		}
		return instance;
	}

	/**
	 * @param deployementBean
	 * @param dataBaseBean
	 */
	public void addDatabase(DeployementBean deployementBean, DataBaseBean dataBaseBean) {
		try {
			DeployementBean deployement = getEntityManager().find(DeployementBean.class, deployementBean.getIdDeployment());
			DataBaseBean dataBase = getEntityManager().find(DataBaseBean.class, dataBaseBean.getIdDataBase());

			dataBase.getDeployements().add(deployement);

			getEntityManager().persist(dataBase);
			getEntityManager().getTransaction().commit();
		} finally {
		}
	}

	/**
	 * @param deployementBean
	 * @param dataBaseBean
	 */
	public void removeDataBase(DeployementBean deployementBean, DataBaseBean dataBaseBean) {
		try {
			DeployementBean deployement = getEntityManager().find(DeployementBean.class, deployementBean.getIdDeployment());
			DataBaseBean dataBase = getEntityManager().find(DataBaseBean.class, dataBaseBean.getIdDataBase());
			
			dataBase.getDeployements().remove(deployement);
			getEntityManager().persist(dataBase);
			getEntityManager().getTransaction().commit();
		} finally {

		}
	}

	/**
	 * @param deployementBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DataBaseBean> getDataBases(DeployementBean deployementBean) {
		try {
			dataBases = getEntityManager().createNamedQuery("getDatabasesByDeployement").setParameter("dep", deployementBean).getResultList();
		} finally {
		}
		return dataBases;
	}

}
