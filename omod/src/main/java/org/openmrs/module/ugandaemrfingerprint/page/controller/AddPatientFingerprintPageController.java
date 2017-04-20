package org.openmrs.module.ugandaemrfingerprint.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant;
import org.openmrs.module.ugandaemrfingerprint.remoteserver.FingerPrintGlobalProperties;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Marking a patient as dead
 */
public class AddPatientFingerprintPageController {
    protected final Log log = LogFactory.getLog(this.getClass());

    public void controller(UiSessionContext sessionContext, PageModel model) {
    }

    public void get(@SpringBean PageModel pageModel, @RequestParam(value = "breadcrumbOverride", required = false) String breadcrumbOverride, @RequestParam("patientId") String patientId) {
        FingerPrintGlobalProperties fingerPrintGlobalProperties = new FingerPrintGlobalProperties();
        Patient patient = new Patient();
        patient = Context.getPatientService().getPatientByUuid(patientId);
        pageModel.put("patientId", patient.getUuid());
        pageModel.put("breadcrumbOverride", breadcrumbOverride);
        pageModel.put("fingerSocketPrintIpAddress", fingerPrintGlobalProperties.getGlobalProperty(FingerPrintConstant.DEVICE_SOCKET_IP));
    }
}
