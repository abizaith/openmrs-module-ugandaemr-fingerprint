package org.openmrs.module.ugandaemrfingerprint.core;

import org.openmrs.Obs;
import org.openmrs.module.ugandaemrfingerprint.model.PatientOb;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by lubwamasamuel on 30/05/2017.
 */
public class PatientInOtherFacility implements Serializable {
    private String dateOfBirth;
    private String gender;
    private List<PatientName> patientNames;
    private Facility healthCenter;

    private List<PatientOb> obsSummaryPageList;

    private List<PatientOb> obsLastEncounterPageList;

    public PatientInOtherFacility() {
    }

    public List<PatientName> getPatientNames() {
        return patientNames;
    }

    public void setPatientNames(List<PatientName> patientNames) {
        this.patientNames = patientNames;
    }

    public List<PatientOb> getObsSummaryPageList() {
        return obsSummaryPageList;
    }

    public void setObsSummaryPageList(List<PatientOb> obsSummaryPageList) {
        this.obsSummaryPageList = obsSummaryPageList;
    }

    public List<PatientOb> getObsLastEncounterPageList() {
        return obsLastEncounterPageList;
    }

    public void setObsLastEncounterPageList(List<PatientOb> obsLastEncounterPageList) {
        this.obsLastEncounterPageList = obsLastEncounterPageList;
    }

    /**
     * @param dateOfBirth
     * @param gender
     * @param names
     * @param healthCenter
     */
    public PatientInOtherFacility(String dateOfBirth, String gender, List<PatientName> names, Facility healthCenter, List<PatientOb> obsSummaryPageList, List<PatientOb> obsLastEncounterPageList) {
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.patientNames = names;
        this.healthCenter = healthCenter;
        this.obsSummaryPageList = obsSummaryPageList;
        this.obsLastEncounterPageList = obsLastEncounterPageList;
    }


    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Facility getHealthCenter() {
        return healthCenter;
    }

    public void setHealthCenter(Facility healthCenter) {
        this.healthCenter = healthCenter;
    }

    private String getDateFromString(String date, String dateFormat) {
        DateFormat format = new SimpleDateFormat(dateFormat);
        String finalDate = null;
        try {
            finalDate = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(date);
        return finalDate;
    }


}
