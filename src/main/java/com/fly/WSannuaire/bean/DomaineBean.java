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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author EL HALAOUI Yassine
 *
 */
@Entity
@Table(name = "domaine")
@NamedQueries({ @NamedQuery(name = "getDomaines", query = "Select d from DomaineBean d where d.idProject = :idProject") })
public class DomaineBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DOMAINE")
	private Integer idDomaine;

	@Column(name = "NAME")
	private String name;
	@Column(name = "DOMAINE_URL")
	private String domaineUrl;
	@Column(name = "DESCRIPTION")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROJECT")
	private ProjectBean idProject;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idDomaine")
	private Set<WebServiceBean> webServices = new HashSet<WebServiceBean>();

	/** Constructor **/

	public DomaineBean() {

	}

	/** Getters and Setters **/

	public Integer getIdDomaine() {
		return idDomaine;
	}

	public void setIdDomaine(Integer idDomaine) {
		this.idDomaine = idDomaine;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDomaineUrl() {
		return domaineUrl;
	}

	public void setDomaineUrl(String domaineUrl) {
		this.domaineUrl = domaineUrl;
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

	public Set<WebServiceBean> getWebServices() {
		return webServices;
	}

	public void setWebServices(Set<WebServiceBean> webServices) {
		this.webServices = webServices;
	}

	public void copyProperties(DomaineBean domaineBean) {
		this.name = domaineBean.getName();
		this.description = domaineBean.getDescription();
		this.idProject = domaineBean.getIdProject();
	}

	public String searchString() {
		return name + " " + idProject.getName();
	}

	@Override
	public String toString() {
		return "DomaineBean [idDomaine=" + idDomaine + ", name=" + name + ", description=" + description + ", idProject=" + idProject + ", webServices=" + webServices + "]";
	}

}
