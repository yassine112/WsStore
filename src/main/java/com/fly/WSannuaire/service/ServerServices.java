package com.fly.WSannuaire.service;

import java.util.List;

import com.fly.WSannuaire.bean.ServerBean;
import com.fly.WSannuaire.common.EntityManagerHelper;

public class ServerServices extends EntityManagerHelper {
	private static ServerServices instance;
	private List<ServerBean> servers;

	public ServerServices() {
		super();
	}

	public static ServerServices getInstance() {
		if (instance == null) {
			instance = new ServerServices();
		}
		return instance;
	}

	/**
	 * Get All Servers From Servers
	 */
	@SuppressWarnings("unchecked")
	public List<ServerBean> getservers() {
		try {
			servers = getEntityManager().createNamedQuery("getServers").getResultList();
		} finally {

		}
		return servers;
	}

	/**
	 * get Servers By Type
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ServerBean> getServersByType(String type) {
		try {
			servers = getEntityManager().createNamedQuery("getServersByType").setParameter("type", type).getResultList();
		} finally {

		}
		return servers;
	}

	/**
	 * If The Server Exists We Edit it If The Server Not Exists We Add A New One
	 * 
	 * @param Server
	 */
	public void addOrSave(ServerBean server) {
		try {

			if (server.getIdServer() == null) {
				getEntityManager().persist(server);
			} else {
//				ServerBean oldBean = getEntityManager().find(ServerBean.class, server.getIdServer());
//				oldBean.copyProperties(server);
//				getEntityManager().persist(oldBean);
				
				getEntityManager().merge(server);
			}
			getEntityManager().getTransaction().commit();
		} finally {

		}

	}

	/**
	 * Remove Server
	 * 
	 * @param Server
	 */
	public void remove(ServerBean server) {
		if (!getEntityManager().contains(server)) {
			server = getEntityManager().merge(server);
		}
		getEntityManager().remove(server);
		getEntityManager().getTransaction().commit();
	}

}
