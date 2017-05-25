package org.openmrs.module.ugandaemrfingerprint.core;

import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;

import java.io.Serializable;


/**
 * Created by lubwamasamuel on 24/05/2017.
 */
public class FingerPrintTemplate implements Serializable{
    String fingerprintId;
    String finger;
    String fingerPrint;
    String dateCreated;

    public FingerPrintTemplate() {
    }

    /**
     *
     * @param fingerprintId
     * @param finger
     * @param fingerPrint
     * @param dateCreated
     */
    public FingerPrintTemplate(String fingerprintId, String finger, String fingerPrint, String dateCreated) {
        this.fingerprintId = fingerprintId;
        this.finger = finger;
        this.fingerPrint = fingerPrint;
        this.dateCreated = dateCreated;
    }

    private Patient getPatient(String patientId) {
        PatientService patientService = Context.getPatientService();
        return patientService.getPatientByUuid(patientId);
    }

    public String getFingerprintId() {
        return fingerprintId;
    }

    public void setFingerprintId(String fingerprintId) {
        this.fingerprintId = fingerprintId;
    }

    public String getFinger() {
        return finger;
    }

    public void setFinger(String finger) {
        this.finger = finger;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
