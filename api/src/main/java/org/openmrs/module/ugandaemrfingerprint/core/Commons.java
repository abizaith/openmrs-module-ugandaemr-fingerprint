package org.openmrs.module.ugandaemrfingerprint.core;

import org.openmrs.api.context.Context;

/**
 * Created by lubwamasamuel on 23/02/2017.
 */
public class Commons {

    public Commons() {
    }

    public String getGlobalProperty(String property) {
        return Context.getAdministrationService().getGlobalProperty(property);
    }

    public String getRequestString(String returnTemplete, String params) {

        return returnTemplete.replace("searchParams", params);
    }
}
