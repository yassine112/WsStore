package com.fly.WSannuaire.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @author EL HALAOUI Yassine
 *
 * class implements <b>filter</b>
 * Ouvrir et commiter chaque transaction avec la base de donnée
 */
@WebFilter(filterName = "EntityManagerInterceptor", urlPatterns = { "/*" }, initParams = {
		@WebInitParam(name = "mood", value = "awake") })
public class EntityManagerInterceptor implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig fc) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		try {
			EntityManagerHelper.beginTransaction();
			chain.doFilter(req, res);
//			EntityManagerHelper.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (EntityManagerHelper.getEntityManager() != null && EntityManagerHelper.getEntityManager().isOpen()
					&& EntityManagerHelper.getEntityManager().getTransaction().isActive()) {
				EntityManagerHelper.rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
	}
}
