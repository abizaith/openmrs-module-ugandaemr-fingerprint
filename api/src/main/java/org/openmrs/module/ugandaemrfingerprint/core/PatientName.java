package org.openmrs.module.ugandaemrfingerprint.core;

import java.io.Serializable;

/**
 * Created by lubwamasamuel on 01/06/2017.
 */
public class PatientName implements Serializable{
    private String familyName;
    private String givenName;
    private String middleName;

    public PatientName() {
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public PatientName(String familyName, String givenName, String middleName) {
        this.familyName = familyName;
        this.givenName = givenName;
        this.middleName = middleName;
    }
}
