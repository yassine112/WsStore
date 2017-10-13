package com.fly.WSannuaire.service;

import java.util.List;

import com.fly.WSannuaire.bean.ProjectBean;
import com.fly.WSannuaire.common.EntityManagerHelper;

public class ProjectServices extends EntityManagerHelper {
	private static ProjectServices instance;
	private List<ProjectBean> projets;

	private ProjectServices() {
	}

	public static ProjectServices getInstance() {
		if (instance == null) {
			instance = new ProjectServices();
		}
		return instance;
	}

	/**
	 * Get all projects
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectBean> getProjets() {
		try {
			projets = getEntityManager().createNamedQuery("getProjects").getResultList();
		} finally {

		}
		return projets;
	}

	/**
	 * If the project exsits we edit it , if the project don't excists we add a
	 * new one
	 * 
	 * @param projetBean
	 */
	public void addOrSave(ProjectBean projectBean) throws Exception {
		try {
			if (projectBean.getIdProject() == null) {
				getEntityManager().persist(projectBean);
			} else {
				// ProjectBean oldBean = getEntityManager().find(ProjectBean.class,projectBean.getIdProject());
				// oldBean.copyProperties(projectBean);
				// getEntityManager().persist(oldBean);

				getEntityManager().merge(projectBean);
			}
			getEntityManager().getTransaction().commit();
		} finally {

		}
	}

	/**
	 * Remove projetBean
	 * 
	 * @param projetBean
	 */
	public void remove(ProjectBean projetBean) throws Exception {
		if (!getEntityManager().contains(projetBean)) {
			projetBean = getEntityManager().merge(projetBean);
		}
		getEntityManager().remove(projetBean);
		getEntityManager().getTransaction().commit();
	}

}
