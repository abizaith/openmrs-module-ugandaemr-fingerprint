package org.openmrs.module.ugandaemrfingerprint.page.controller.findpatient;

import org.openmrs.Encounter;
import org.openmrs.api.EncounterService;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.coreapps.helper.BreadcrumbHelper;
import org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant;
import org.openmrs.module.ugandaemrfingerprint.remoteserver.FingerPrintGlobalProperties;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class FindPatientPageController {
    /**
     * This page is built to be shared across multiple apps. To use it, you must pass an "app"
     * request parameter, which must be the id of an existing app that is an instance of
     * coreapps.template.findPatient
     *
     * @param model
     * @param app
     * @param sessionContext
     */
    public void get(PageModel model, @RequestParam("app") AppDescriptor app, UiSessionContext sessionContext, UiUtils ui) {
        FingerPrintGlobalProperties fingerPrintGlobalProperties = new FingerPrintGlobalProperties();
        model.addAttribute("afterSelectedUrl", app.getConfig().get("afterSelectedUrl").getTextValue());
        model.addAttribute("heading", app.getConfig().get("heading").getTextValue());
        model.addAttribute("label", app.getConfig().get("label").getTextValue());
        model.addAttribute("showLastViewedPatients", app.getConfig().get("showLastViewedPatients").getBooleanValue());
        model.put("fingerSocketPrintIpAddress", fingerPrintGlobalProperties.getGlobalProperty(FingerPrintConstant.DEVICE_SOCKET_IP));
        BreadcrumbHelper.addBreadcrumbsIfDefinedInApp(app, model, ui);
    }

}
