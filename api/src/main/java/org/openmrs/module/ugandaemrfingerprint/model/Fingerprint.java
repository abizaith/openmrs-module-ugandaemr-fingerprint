package org.openmrs.module.ugandaemrfingerprint.model;

import java.sql.Blob;
import org.openmrs.Auditable;
import org.openmrs.BaseOpenmrsMetadata;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lubwamasamuel on 25/07/2017.
 */
public class Fingerprint extends BaseOpenmrsMetadata implements Auditable,Serializable{
    private int fingerprintId;

    private String patient;

    private int finger;

    private Blob fingerprint;

    private Date dateCreated;

    public int getFingerprintId() {
        return fingerprintId;
    }

    public Fingerprint() {
    }

    public Fingerprint(int fingerprintId, String patient, int finger, Date dateCreated) {
        this.fingerprintId = getId();
        this.patient = patient;
        this.finger = finger;
        this.fingerprint = getFingerprint();
        this.dateCreated = dateCreated;
    }

    public void setFingerprintId(int fingerprintId) {
        this.fingerprintId = fingerprintId;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public int getFinger() {
        return finger;
    }

    public void setFinger(int finger) {
        this.finger = finger;
    }

    public Blob getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(Blob fingerprint) {
        this.fingerprint = fingerprint;
    }

    @Override
    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override

    public Integer getId() {
        return fingerprintId;
    }

    @Override
    public void setId(Integer integer) {
    }
}
