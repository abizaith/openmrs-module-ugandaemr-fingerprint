package org.openmrs.module.ugandaemrfingerprint.remoteserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;

import static org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant.*;

/**
 * Created by lubwamasamuel on 10/11/2016.
 */
public class FingerPrintGlobalProperties {
	
	public FingerPrintGlobalProperties() {
	}
	
	protected Log log = LogFactory.getLog(getClass());
	
	public void setFingerPrintGlobalProperties() {
		/**
		 * Setting Device Socket IP Address
		 */
		if (getGlobalProperty(DEVICE_SOCKET_IP) == null) {
			setGlobalProperty(DEVICE_SOCKET_IP, DEVICE_SOCKET_IP_PLACE_HOLDER);
			log.info("Default Server IP is Set");
		}
	}
	
	public GlobalProperty setGlobalProperty(String property, String propertyValue) {
		GlobalProperty globalProperty = new GlobalProperty();
		
		globalProperty.setProperty(property);
		globalProperty.setPropertyValue(propertyValue);
		
		return Context.getAdministrationService().saveGlobalProperty(globalProperty);
	}
	
	public String getGlobalProperty(String property) {
		return Context.getAdministrationService().getGlobalProperty(property);
	}
	
}
