package org.openmrs.module.ugandaemrfingerprint.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.util.JSONPObject;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant;
import org.openmrs.module.ugandaemrfingerprint.core.GenericService;
import org.openmrs.module.ugandaemrfingerprint.core.PatientInOtherFacility;
import org.openmrs.module.ugandaemrfingerprint.remoteserver.FingerPrintGlobalProperties;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant.*;

/**
 * Marking a patient as dead
 */
public class PatientInOtherFacilityPageController {
    protected final Log log = LogFactory.getLog(this.getClass());

    public void controller(UiSessionContext sessionContext, PageModel model) {
    }

    public void get(@SpringBean PageModel pageModel, @RequestParam(value = "breadcrumbOverride", required = false) String breadcrumbOverride, @RequestParam(value = "patientId", required = false) String patientId) {
        FingerPrintGlobalProperties fingerPrintGlobalProperties = new FingerPrintGlobalProperties();
        pageModel.put("familyName", "");
        pageModel.put("middleName", "");
        pageModel.put("givenName", "");
        pageModel.put("birthdate", false);
        pageModel.put("gender", false);
        pageModel.put("dead", false);
        pageModel.put("summaryPage", false);
        pageModel.put("mostRecentEncounter", false);
        pageModel.put("facilityName", "");
        pageModel.put("facilityId", "");
        pageModel.put("patientId", patientId);
        pageModel.put("patientSummary", "");
        pageModel.put("patientFound", false);
        pageModel.put("patientNotFound", "");
        pageModel.put("connectionFailed", "");
        pageModel.put("searched", false);
        pageModel.put("breadcrumbOverride", breadcrumbOverride);

        pageModel.put("onlineIpAddress", fingerPrintGlobalProperties.getGlobalProperty(FingerPrintConstant.CONNECTION_SERVER_IP_GLOBALPROPERTY));
        pageModel.put("queryURL", FingerPrintConstant.SEARCH_URL);
        pageModel.put("searchString", PATIENT_UUID_SEARCH_STRING);
        pageModel.put("nationalIdString", PATIENT_NATIONAL_ID_SEARCH_STRING);
        pageModel.put("connectionProtocol", CONNECTION_PROTOCOL);
    }

    public List<Obs> getOnlineEncounterPage(String s) {


        return null;
    }

    private Concept getConcept(int conceptId) {
        ConceptService conceptService = Context.getConceptService();
        return conceptService.getConcept(conceptId);
    }
}
