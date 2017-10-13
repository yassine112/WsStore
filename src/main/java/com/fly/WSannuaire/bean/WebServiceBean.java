package com.fly.WSannuaire.bean;

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
import javax.persistence.Table;

/**
 * @author EL HALAOUI Yassine
 *
 */
@Entity
@Table(name = "web_service")
@NamedQueries({ @NamedQuery(name = "getWebServices", query = "Select w from WebServiceBean w Where w.idDomaine = :domaine") })
public class WebServiceBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_WS")
	private Integer idWebService;

	@Column(name = "NAME")
	private String name;

	@Column(name = "URL_DIRECT")
	private String urlDirect;

	@Column(name = "URL_BROKER")
	private String urlBroker;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "METHODE")
	private String methode;

	@Column(name = "MSG_ENTREE")
	private String msgEntree;

	@Column(name = "MSG_RETOUR")
	private String msgRetour;

	@Column(name = "DEVLOPPER")
	private String developper;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DOMAINE")
	private DomaineBean idDomaine;

	/** Constructor **/

	public WebServiceBean() {

	}

	/** Getters and Setters **/

	public Integer getIdWebService() {
		return idWebService;
	}

	public void setIdWebService(Integer idWebService) {
		this.idWebService = idWebService;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlDirect() {
		return urlDirect;
	}

	public void setUrlDirect(String urlDirect) {
		this.urlDirect = urlDirect;
	}

	public String getUrlBroker() {
		return urlBroker;
	}

	public void setUrlBroker(String urlBroker) {
		this.urlBroker = urlBroker;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMethode() {
		return methode;
	}

	public void setMethode(String methode) {
		this.methode = methode;
	}

	public String getMsgEntree() {
		return msgEntree;
	}

	public void setMsgEntree(String msgEntree) {
		this.msgEntree = msgEntree;
	}

	public String getMsgRetour() {
		return msgRetour;
	}

	public void setMsgRetour(String msgRetour) {
		this.msgRetour = msgRetour;
	}

	public String getDevelopper() {
		return developper;
	}

	public void setDevelopper(String developper) {
		this.developper = developper;
	}

	public DomaineBean getIdDomaine() {
		return idDomaine;
	}

	public void setIdDomaine(DomaineBean idDomaine) {
		this.idDomaine = idDomaine;
	}

	public void copyProperties(WebServiceBean webServiceBean) {
		this.name = webServiceBean.getName();
		this.urlBroker = webServiceBean.getUrlBroker();
		this.urlDirect = webServiceBean.getUrlDirect(); 
		this.description = webServiceBean.getDescription();
		this.methode = webServiceBean.getMethode();
		this.msgEntree = webServiceBean.getMsgEntree();
		this.msgRetour = webServiceBean.getMsgRetour();
		this.developper = webServiceBean.getDevelopper();
		this.idDomaine = webServiceBean.getIdDomaine();
	}

	public String searchString() {
		return name + " " + methode + " " + developper;
	}

}
