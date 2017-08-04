/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.ugandaemrfingerprint.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.ugandaemrfingerprint.UgandaEMRFingerprintService;
import org.openmrs.module.ugandaemrfingerprint.api.db.UgandaEMRFingerprintDao;
import org.openmrs.module.ugandaemrfingerprint.model.Fingerprint;

import java.util.List;
import java.util.UUID;

public class UgandaEMRFingerprintServiceImpl extends BaseOpenmrsService implements UgandaEMRFingerprintService {


    private UgandaEMRFingerprintDao dao;

    public UgandaEMRFingerprintServiceImpl() {
    }

    private UgandaEMRFingerprintDao getUgandaEMRFingerprintDAO() {
        return dao;
    }

    public void setUgandaEMRFingerprintDAO(UgandaEMRFingerprintDao dao) {
        this.dao = dao;
    }

    private Log log = LogFactory.getLog(this.getClass());

    public void setDao(UgandaEMRFingerprintDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Fingerprint> getPatientFingerprint(String patientId) {
        return dao.getPatientFingerPrint(patientId);
    }

    @Override
    public List<Fingerprint> getPatientFingerprints() {
        return dao.getPatientFingerPrints();
    }

    @Override
    public void deletePatientFingerPrint(String patientId) {
        dao.deletePatientFingerPrint(patientId);
    }

    @Override
    public void savePatientFingerprint(Fingerprint fingerprint) {
        dao.savePatientFingerprint(fingerprint);
    }

    @Override
    public boolean isUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
