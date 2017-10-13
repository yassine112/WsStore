package com.fly.WSannuaire.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author EL HALAOUI Yassine
 *
 */
@Entity
@Table(name = "server")
@NamedQueries({ @NamedQuery(name = "getServers", query = "SELECT s FROM ServerBean s"),
		@NamedQuery(name = "getServersByType", query = "SELECT s FROM ServerBean s Where s.type = :type OR s.type = NULL") })
public class ServerBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SERVER")
	private Integer idServer;

	@Column(name = "IP_ADRESS")
	private String ipAdress;
	@Column(name = "NAME")
	private String name;
	@Column(name = "SYS_EXP")
	private String sysExploitation;
	@Column(name = "LOCALISATION", nullable = true)
	private String localisation;
	@Column(name = "TYPE", nullable = true)
	private String type;
	@Column(name = "REMARQUE", nullable = true)
	private String remarque;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idServer")
	private Set<DeployementBean> deployements = new HashSet<DeployementBean>();

	/** Constructor **/
	public ServerBean() {
		super();
	}

	/** Getters & Setters **/
	public Integer getIdServer() {
		return idServer;
	}

	public void setIdServer(Integer idServer) {
		this.idServer = idServer;
	}

	public String getIpAdress() {
		return ipAdress;
	}

	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}

	public String getName() {
		return name;
	}

	public void setName(String nom) {
		this.name = nom;
	}

	public String getSysExploitation() {
		return sysExploitation;
	}

	public void setSysExploitation(String sysExploitation) {
		this.sysExploitation = sysExploitation;
	}

	public String getLocalisation() {
		return localisation;
	}

	public void setLocalisation(String localisation) {
		this.localisation = localisation;
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
		return deployements;
	}

	public void setDeployements(Set<DeployementBean> deployements) {
		this.deployements = deployements;
	}

	public void copyProperties(ServerBean sb) {
		this.ipAdress = sb.getIpAdress();
		this.name = sb.getName();
		this.sysExploitation = sb.getSysExploitation();
		this.localisation = sb.getLocalisation();
		this.type = sb.getType();
		this.remarque = sb.getRemarque();
	}

	@Override
	public String toString() {
		return name;
	}

	public String stringSearch() {
		return ipAdress + " " + name + " " + sysExploitation + " " + localisation + " " + remarque;
	}
}