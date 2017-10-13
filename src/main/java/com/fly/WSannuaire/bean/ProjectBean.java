package com.fly.WSannuaire.bean;

import java.util.Set;
import java.util.HashSet;

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
@Table(name = "project")
@NamedQueries({
	@NamedQuery(name="getProjects", query="SELECT p from ProjectBean p")
})
public class ProjectBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PROJECT")
	private Integer idProject;

	@Column(name = "NAME")
	private String name;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "CVS_NAME")
	private String cvsName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idProject")
	private Set<DomaineBean> domaines = new HashSet<DomaineBean>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idProject")
	private Set<DeployementBean> Deployements = new HashSet<DeployementBean>();

	/** Constructor **/

	public ProjectBean() {

	}

	/** Getters and Setters **/

	public Integer getIdProject() {
		return idProject;
	}

	public void setIdProject(Integer idProject) {
		this.idProject = idProject;
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

	public String getCvsName() {
		return cvsName;
	}

	public void setCvsName(String cvsName) {
		this.cvsName = cvsName;
	}

	public Set<DomaineBean> getDomaines() {
		return domaines;
	}

	public void setDomaines(Set<DomaineBean> domaines) {
		this.domaines = domaines;
	}

	public Set<DeployementBean> getDeployements() {
		return Deployements;
	}

	public void setDeployements(Set<DeployementBean> deployements) {
		Deployements = deployements;
	}

	public void copyProperties(ProjectBean projectBean) {
		this.name = projectBean.getName();
		this.description = projectBean.getDescription();
		this.cvsName = projectBean.getCvsName();
	}

	public String searchString() {
		return name + " " + description + " " + cvsName;
	}
	
	@Override
	public String toString(){
		return name;
	}
}
