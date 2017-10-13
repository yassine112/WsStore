package com.fly.WSannuaire.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author EL HALAOUI Yassine
 *
 */
@Entity
@Table(name = "user")
@NamedQueries({ 
	@NamedQuery(name = "getUsers", query = "SELECT u FROM UserBean u"),
	@NamedQuery(name = "getUserByLogin", query = "SELECT userBean FROM UserBean userBean WHERE userBean.login=:login and userBean.pass=:pass ") 
})
public class UserBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USER")
	private Integer idUser;

	@Column(name = "FNAME")
	private String fname;
	@Column(name = "LNAME")
	private String lname;
	@Column(name = "LOGIN")
	private String login;
	@Column(name = "PASS")
	private String pass;

	/** Constructor **/

	public UserBean() {

	}

	/** Getters and Setters **/

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String passw) {
		this.pass = passw;
	}

	public void copyProperties(UserBean newBean) {
		this.fname = newBean.getFname();
		this.lname = newBean.getLname();
		this.login = newBean.getLogin();
		this.pass = newBean.getPass();
	}

	@Override
	public String toString() {
		return fname + " " + lname + " " + login;
	}

}
