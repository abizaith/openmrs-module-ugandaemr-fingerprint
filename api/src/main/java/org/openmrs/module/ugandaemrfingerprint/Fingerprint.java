/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.ugandaemrfingerprint;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Patient;

import javax.persistence.*;

/**
 * Please note that a corresponding table schema must be created in liquibase.xml.
 */
@Entity(name = "ugandaemrfingerprint.Fingerprint")
@Table(name = "ugandaemrfingerprint_fingerprint")
public class Fingerprint extends BaseOpenmrsData {

    @Id
    @GeneratedValue
    @Column(name = "ugandaemrfingerprint_fingerprint_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient")
    private Patient patient;

    @Basic
    @Column(name = "finger")
    private Integer finger;

    @Column(name = "fingerprint", columnDefinition = "blob")
    private byte[] fingerprint;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUuid() {
        return super.getUuid();
    }

    @Override
    public void setUuid(String uuid) {
        super.setUuid(uuid);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Integer getFinger() {
        return finger;
    }

    public void setFinger(Integer finger) {
        this.finger = finger;
    }

    public byte[] getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(byte[] fingerprint) {
        this.fingerprint = fingerprint;
    }
}
