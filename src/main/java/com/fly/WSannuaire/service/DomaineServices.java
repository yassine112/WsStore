package com.fly.WSannuaire.service;

import java.util.List;

import com.fly.WSannuaire.bean.DomaineBean;
import com.fly.WSannuaire.bean.ProjectBean;
import com.fly.WSannuaire.common.EntityManagerHelper;

public class DomaineServices extends EntityManagerHelper {

	private static DomaineServices instance;
	private List<DomaineBean> domaines;

	private DomaineServices() {
	}

	public static DomaineServices getInstance() {
		if (instance == null) {
			instance = new DomaineServices();
		}
		return instance;
	}

	@SuppressWarnings({ "unchecked" })
	public List<DomaineBean> getDomaines(ProjectBean projectBean) {
		try {
			domaines = getEntityManager().createNamedQuery("getDomaines").setParameter("idProject", projectBean).getResultList();
		} finally {

		}
		return domaines;
	}

	public void addOrSave(DomaineBean domaine) {
		try {
			if (domaine.getIdDomaine() == null) {
				getEntityManager().persist(domaine);
			} else {
				// DomaineBean oldBean =
				// getEntityManager().find(DomaineBean.class,
				// domaine.getIdDomaine());
				// oldBean.copyProperties(domaine);
				// getEntityManager().persist(oldBean);

				getEntityManager().merge(domaine);
			}
			getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void delete(DomaineBean domaine) throws Exception {
		if (!getEntityManager().contains(domaine)) {
			domaine = getEntityManager().merge(domaine);
		}
		getEntityManager().remove(domaine);
		getEntityManager().getTransaction().commit();
	}
}
