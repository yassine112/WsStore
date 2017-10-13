package com.fly.WSannuaire.service;

import java.util.List;

import com.fly.WSannuaire.bean.DomaineBean;
import com.fly.WSannuaire.bean.WebServiceBean;
import com.fly.WSannuaire.common.EntityManagerHelper;

public class WebServiceServices extends EntityManagerHelper {

	private static WebServiceServices instance;
	private List<WebServiceBean> webServices;

	public static WebServiceServices getInstance() {
		if (instance == null) {
			instance = new WebServiceServices();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public List<WebServiceBean> getWebServices(DomaineBean domaineBean) {
		try {
			webServices = getEntityManager().createNamedQuery("getWebServices").setParameter("domaine", domaineBean).getResultList();
		} finally {
			// TODO: handle finally clause
		}
		return webServices;
	}


	public void addOrSave(WebServiceBean webService) {
		try {
			if (webService.getIdWebService() == null) {
				getEntityManager().persist(webService);
			} else {
//				WebServiceBean oldBean = getEntityManager().find(WebServiceBean.class, webService.getIdWebService());
//				oldBean.copyProperties(webService);
//				getEntityManager().persist(oldBean);
				
				getEntityManager().merge(webService);
			}
			getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void remove(WebServiceBean webService) {
		if (!getEntityManager().contains(webService)) {
			webService = getEntityManager().merge(webService);
		}
		getEntityManager().remove(webService);
		getEntityManager().getTransaction().commit();
	}

}
