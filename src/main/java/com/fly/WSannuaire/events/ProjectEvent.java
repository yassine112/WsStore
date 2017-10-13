package com.fly.WSannuaire.events;

import com.fly.WSannuaire.bean.ProjectBean;

public class ProjectEvent {
	public ProjectBean projectBean = new ProjectBean();

	public ProjectEvent(ProjectBean projetBean) {
		this.projectBean = projetBean;
	}
}
