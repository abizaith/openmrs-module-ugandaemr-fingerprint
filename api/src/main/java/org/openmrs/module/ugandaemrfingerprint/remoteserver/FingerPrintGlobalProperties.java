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
		if (getGlobalProperty(GP_DEVICE_SOCKET_IP) == null) {
			setGlobalProperty(GP_DEVICE_SOCKET_IP, GP_DEVICE_SOCKET_IP_PLACE_HOLDER);
			log.info("Default Server IP is Set");
		}

		/**
		 * Enable disable online search
		 */
		if (getGlobalProperty(GP_ONLINE_SEARCH_ENABLE_DISABLE) == null) {
			setGlobalProperty(GP_ONLINE_SEARCH_ENABLE_DISABLE, GP_ONLINE_SEARCH_ENABLE_DISABLE_PLACE_HOLDER);
			log.info("online saarch enable disable is Set");
		}

		/**
		 * Enable disable showing patient clinic summary when searching online search
		 */
		if (getGlobalProperty(GP_SHOW_PATIENT_CLINIC_SUMMARY) == null) {
			setGlobalProperty(GP_SHOW_PATIENT_CLINIC_SUMMARY, GP_SHOW_PATIENT_CLINIC_SUMMARY_PLACE_HOLDER);
			log.info("showing patient clinic summary enable disable is Set");
		}

        /**
         * Enable disable showing patient last treatment encounter when searching online search
         */
        if (getGlobalProperty(GP_SHOW_PATIENT_LAST_TREATMENT_ENCOUNTER) == null) {
            setGlobalProperty(GP_SHOW_PATIENT_LAST_TREATMENT_ENCOUNTER, GP_SHOW_PATIENT_LAST_TREATMENT_ENCOUNTER_PLACE_HOLDER);
            log.info("showing patient last treatment encounter enable disable is Set");
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
