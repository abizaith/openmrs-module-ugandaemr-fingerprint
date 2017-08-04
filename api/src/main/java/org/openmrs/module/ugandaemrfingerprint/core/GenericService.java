package org.openmrs.module.ugandaemrfingerprint.core;

import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant.*;

/**
 * Created by lubwamasamuel on 30/05/2017.
 */
public class GenericService {
    public List<Facility> getOtherClientHealthCenters(List<Map> healthCenters) {

        List<Facility> otherHealthCenters = new ArrayList<Facility>();

        for (Map healthCenter : healthCenters) {
            Facility otherHealthCenter = new Facility();

            otherHealthCenter.setFacilityId(healthCenter.get("id").toString());
            otherHealthCenter.setName(healthCenter.get("name").toString());
            otherHealthCenters.add(otherHealthCenter);
        }
        return otherHealthCenters;
    }


    public PatientInOtherFacility getPatientInOtherFacility(Map client) {
        List<PatientName> patientNames = new ArrayList<PatientName>();
        patientNames = getPatientNames((List<Map>) client.get(PATIENT_NAMES));
        Facility facilities = getPatientFacility((Map) client.get(PATIENT_FACILITY_NAME));
        PatientInOtherFacility patientInOtherFacility = new PatientInOtherFacility(client.get("birthdate").toString(), client.get("gender").toString(), patientNames, facilities);
        return patientInOtherFacility;
    }


    public  List<Map> getMap(){

        return null;
    }

    public String getConcept(String s){
        ConceptService conceptService= Context.getConceptService();

        return conceptService.getConceptByUuid(s).getName().getName();
    }

    public List<PatientName> getPatientNames(List<Map> mapList) {
        List<PatientName> patientNames = new ArrayList<PatientName>();
        for (Map map : mapList) {
            PatientName patientName = new PatientName();
            patientName.setFamilyName(map.get(PATIENT_FAMILY_NAME).toString());
            if (map.get(PATIENT_MIDDLE_NAME) != null) {
                patientName.setMiddleName(map.get(PATIENT_MIDDLE_NAME).toString());
            }
            patientName.setGivenName(map.get(PATIENT_GIVEN_NAME).toString());
            patientNames.add(patientName);
        }
        return patientNames;
    }

    public Facility getPatientFacility(Map map) {

        Facility facility = new Facility();
        facility.setName(map.get(NAME).toString());
        facility.setFacilityId(map.get(UUID).toString());

        return facility;
    }
}
