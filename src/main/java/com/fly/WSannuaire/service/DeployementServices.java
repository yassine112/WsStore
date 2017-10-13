package com.fly.WSannuaire.service;

import java.util.List;

import com.fly.WSannuaire.bean.DeployementBean;
import com.fly.WSannuaire.bean.ProjectBean;
import com.fly.WSannuaire.common.EntityManagerHelper;

/**
 * @author EL HALAOUI Yassine
 *
 */
public class DeployementServices extends EntityManagerHelper {

	private static DeployementServices instance;
	private List<DeployementBean> deployements;

	public DeployementServices() {
	}

	public static DeployementServices getInstance() {
		if (instance == null) {
			instance = new DeployementServices();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public List<DeployementBean> getDeployements(ProjectBean projectBean) {
		try {
			deployements = getEntityManager().createNamedQuery("getDeployements").setParameter("project", projectBean).getResultList();
		} finally {
		}
		return deployements;
	}

	public void addOrSave(DeployementBean deployementBean) {
		try {
			if (deployementBean.getIdDeployment() == null) {
				getEntityManager().persist(deployementBean);
			} else {
				// DeployementBean oldBean =
				// getEntityManager().find(DeployementBean.class,
				// deployementBean.getIdDeployment());
				// oldBean.copyProperties(deployementBean);
				// getEntityManager().persist(oldBean);

				getEntityManager().merge(deployementBean);
			}
			getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(DeployementBean deployementBean) {
		if (!getEntityManager().contains(deployementBean)) {
			deployementBean = getEntityManager().merge(deployementBean);
		}
		getEntityManager().remove(deployementBean);
		getEntityManager().getTransaction().commit();
	}
}
