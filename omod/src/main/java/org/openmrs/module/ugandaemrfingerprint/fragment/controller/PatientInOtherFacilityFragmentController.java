package org.openmrs.module.ugandaemrfingerprint.fragment.controller;

import com.google.gson.JsonObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.ugandaemrfingerprint.core.FingerPrintTemplate;
import org.openmrs.module.ugandaemrfingerprint.core.GenericService;
import org.openmrs.module.ugandaemrfingerprint.core.PatientInOtherFacility;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Lubwama Samuel on 26/01/2016.
 */
public class PatientInOtherFacilityFragmentController {


    private static final Log log = LogFactory.getLog(PatientInOtherFacilityFragmentController.class);


    public void controller(FragmentModel model, UiSessionContext sessionContext,
                           HttpServletRequest request,
                           @SpringBean("adminService") AdministrationService administrationService,
                           @FragmentParam(value = "showLastViewedPatients", required = false) Boolean showLastViewedPatients,
                           @FragmentParam(value = "initialSearchFromParameter", required = false) String searchByParam) {
    }

    public SimpleObject mapPatientInOtherFacilities(@RequestParam(value = "patient", required = false) String o) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = null;
        SimpleObject simpleObject = new SimpleObject();
        try {
            String patient = o;
            map = objectMapper.readValue(o.toString(), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GenericService genericService = new GenericService();
        PatientInOtherFacility patientInOtherFacility = genericService.getPatientInOtherFacility(map);
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(patientInOtherFacility);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jsonString != "") {
            Map<String, Object> map1 = objectMapper.convertValue(patientInOtherFacility, Map.class);
            simpleObject.putAll(map1);
        }

        return simpleObject;
    }
}