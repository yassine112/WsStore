package com.fly.WSannuaire.common;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class GetProp {
	public static String getProperty(final String paramId, final String propertyFileName) {
		try {
			if (paramId == null) {
				return null;
			}
			return ResourceBundle.getBundle(propertyFileName).getString(paramId);
		} catch (MissingResourceException mre) {
		} catch (Exception e) {
		}
		return null;
	}
}