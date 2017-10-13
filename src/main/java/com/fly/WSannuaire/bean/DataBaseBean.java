package com.fly.WSannuaire.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "data_base")
@NamedQueries({
	@NamedQuery(name="getDataBases", query="Select d from DataBaseBean d"),
	@NamedQuery(name="getDatabasesByDeployement", query="select d from DataBaseBean d JOIN d.Deployements dp WHERE dp = :dep ")
})
public class DataBaseBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DB")
	private Integer idDataBase;

	@Column(name = "URL")
	private String url;
	@Column(name = "NAME")
	private String name;
	@Column(name = "LOGIN")
	private String login;
	@Column(name = "PASS")
	private String pass;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "REMARQUE")
	private String remarque;

	@ManyToMany
	@JoinTable(name = "deployement_data_base",
		joinColumns = @JoinColumn(name = "DataBaseBean_ID_DB"),
		inverseJoinColumns = @JoinColumn(name = "DeployementBean_ID_DEPLOYEMENT"))
	private Set<DeployementBean> Deployements = new HashSet<DeployementBean>();

	/** Constructor **/
	public DataBaseBean() {

	}

	/** Getters and Setters **/
	public Integer getIdDataBase() {
		return idDataBase;
	}

	public void setIdDataBase(Integer idDataBase) {
		this.idDataBase = idDataBase;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

	public Set<DeployementBean> getDeployements() {
		return Deployements;
	}

	public void setDeployements(Set<DeployementBean> deployements) {
		Deployements = deployements;
	}

	public void copyProperties(DataBaseBean dataBaseBean) {
		this.url = dataBaseBean.getUrl();
		this.name = dataBaseBean.getName();
		this.login = dataBaseBean.getLogin();
		this.pass = dataBaseBean.getPass();
		this.type = dataBaseBean.getType();
		this.remarque = dataBaseBean.getRemarque();
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public String stringSearch() {
		return url + " " + name + " " + login + " " + remarque;
	}
}
