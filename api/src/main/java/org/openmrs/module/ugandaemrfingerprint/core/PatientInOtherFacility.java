package org.openmrs.module.ugandaemrfingerprint.core;

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
    private Date dateOfBirth;
    private String gender;
    private List<PatientName> patientNames;
    private Facility healthCenter;

    public PatientInOtherFacility() {
    }

    public List<PatientName> getPatientNames() {
        return patientNames;
    }

    public void setPatientNames(List<PatientName> patientNames) {
        this.patientNames = patientNames;
    }

    /**
     * @param dateOfBirth
     * @param gender
     * @param names
     * @param healthCenter
     */


    public PatientInOtherFacility(String dateOfBirth, String gender, List<PatientName> names, Facility healthCenter) {
        this.dateOfBirth = getDateFromString(dateOfBirth, "yyyy-M-dd");
        this.gender = gender;
        this.patientNames = names;
        this.healthCenter = healthCenter;
    }


    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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

    private Date getDateFromString(String date, String dateFormat) {
        DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        Date finalDate = null;
        try {
            finalDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        return finalDate;
    }


}
