package com.fly.WSannuaire.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author EL HALAOUI Yassine
 *
 */
@Entity
@Table(name = "deployement")
@NamedQueries({
	@NamedQuery(name="getDeployements", query="Select d from DeployementBean d where d.idProject =:project")
})
public class DeployementBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DEPLOYEMENT")
	private Integer idDeployment;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROJECT")
	private ProjectBean idProject;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SERVER")
	private ServerBean idServer;

	@ManyToMany
	private Set<DataBaseBean> DataBases = new HashSet<DataBaseBean>();

	/** Constructor **/

	public DeployementBean() {

	}

	/** Getters and Setters **/

	public Integer getIdDeployment() {
		return idDeployment;
	}

	public void setIdDeployment(Integer idDeployment) {
		this.idDeployment = idDeployment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProjectBean getIdProject() {
		return idProject;
	}

	public void setIdProject(ProjectBean idProject) {
		this.idProject = idProject;
	}

	public ServerBean getIdServer() {
		return idServer;
	}

	public void setIdServer(ServerBean idServer) {
		this.idServer = idServer;
	}

	public Set<DataBaseBean> getDataBases() {
		return DataBases;
	}

	public void setDataBases(Set<DataBaseBean> dataBases) {
		DataBases = dataBases;
	}

	public void copyProperties(DeployementBean deployementBean) {
		this.description = deployementBean.getDescription();
		this.idProject = deployementBean.getIdProject();
		this.idServer = deployementBean.getIdServer();
	}

	public String searchString() {
		return name + " " + idProject.getName();
	}
}
