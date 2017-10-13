package com.fly.WSannuaire.service;

import java.util.List;

import javax.persistence.NoResultException;

import com.fly.WSannuaire.bean.UserBean;
import com.fly.WSannuaire.common.EntityManagerHelper;

/**
 * @author EL HALAOUI Yassine
 *
 */

public class UserServices extends EntityManagerHelper {

	private static UserServices instance;
	private List<UserBean> users;

	public UserServices() {
		super();
	}

	public static UserServices getInstance() {
		if (instance == null) {
			instance = new UserServices();
		}
		return instance;
	}

	/**
	 * Get All Users From DataBase
	 */
	@SuppressWarnings("unchecked")
	public List<UserBean> getUsers() {
		try {
			users = getEntityManager().createNamedQuery("getUsers").getResultList();
		} finally {

		}
		return users;
	}

	/**
	 * If The User Exists We Edit it If The User Not Exists We Add A New One
	 * 
	 * @param User
	 */
	public void addOrSave(UserBean User) {
		try {

			if (User.getIdUser() == null) {
				getEntityManager().persist(User);
			} else {
//				UserBean oldBean = getEntityManager().find(UserBean.class, User.getIdUser());
//				oldBean.copyProperties(User);
//				getEntityManager().persist(oldBean);
				
				getEntityManager().merge(User);
			}
			getEntityManager().getTransaction().commit();

		} finally {

		}

	}

	/**
	 * @param login
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UserBean getUsersByLogin(String login, String pass) {
		try {
			List<UserBean> user = getEntityManager().createNamedQuery("getUserByLogin").setParameter("login", login).setParameter("pass", pass).getResultList();
			return user.get(0);
		} catch (NoResultException e) {

		}

		return null;
	}

	/**
	 * Remove User
	 * 
	 * @param User
	 */
	public void remove(UserBean User) {
		if (!getEntityManager().contains(User)) {
			User = getEntityManager().merge(User);
		}
		getEntityManager().remove(User);
		getEntityManager().getTransaction().commit();
	}

}