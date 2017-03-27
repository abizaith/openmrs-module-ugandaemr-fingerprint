package org.openmrs.module.ugandaemrfingerprint.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.ugandaemrfingerprint.core.Commons;
import org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant;
import org.openmrs.module.ugandaemrfingerprint.remoteserver.FingerPrintHttpURLConnection;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant.CONNECTION_SUCCESS;

/**
 * Marking a patient as dead
 */
public class PatientInOtherFacilityPageController {
    protected final Log log = LogFactory.getLog(this.getClass());

    public void controller(UiSessionContext sessionContext, PageModel model) {
    }

    public void get(@SpringBean PageModel pageModel, @RequestParam(value = "breadcrumbOverride", required = false) String breadcrumbOverride) {
        pageModel.put("breadcrumbOverride", breadcrumbOverride);
        pageModel.put("facilityName", "");
        pageModel.put("facilityId", "");
        pageModel.put("patientId", "");
        pageModel.put("patientSummary", "");
        pageModel.put("patientFound", false);
        pageModel.put("patientNotFound", "");
        pageModel.put("connectionFailed", "");
        pageModel.put("searched", false);
    }


    public void post(@SpringBean PageModel pageModel, @RequestParam(value = "breadcrumbOverride", required = false) String breadcrumbOverride, @RequestParam(value = "onlinesearch", required = false) String onlineSearch, UiUtils
            uiUtils) {
        FingerPrintHttpURLConnection fingerPrintHttpURLConnection = new FingerPrintHttpURLConnection();
        Map resultsOnlin = null;
        boolean patientFound = false;
        String patientNotFound = "";
        String connectionStatus = "";
        boolean searched = false;

        try {
            int success = fingerPrintHttpURLConnection.getCheckConnection("google.com");

            if (success == CONNECTION_SUCCESS) {
                Commons commons = new Commons();
                resultsOnlin = fingerPrintHttpURLConnection.sendPostBy(FingerPrintConstant.SEARCH_URL, commons.getRequestString(FingerPrintConstant.SEARCH_PARAMS, onlineSearch));

                if (resultsOnlin != null) {
                    pageModel.put("facilityName", resultsOnlin.get("facilityName"));
                    pageModel.put("facilityId", resultsOnlin.get("facilityId"));
                    pageModel.put("patientId", resultsOnlin.get("patientId"));
                    pageModel.put("patientSummary", resultsOnlin.get("patientSummary"));
                    patientFound = true;

                } else {
                    patientNotFound = "Patient is not Found while using search phrase " + onlineSearch;
                }

            } else {
                connectionStatus = "Connection to Server was not successful. Check internet connection";
            }
            searched = true;

        } catch (Exception e) {
            log.error(e);
            connectionStatus = "Connection to Server was not successful. Check internet connection";
            searched = true;
        }
        pageModel.put("breadcrumbOverride", breadcrumbOverride);
        pageModel.put("patientFound", patientFound);
        pageModel.put("connectionStatus", connectionStatus);
        pageModel.put("searched", searched);
        pageModel.put("patientNotFound", patientNotFound);
    }
}
