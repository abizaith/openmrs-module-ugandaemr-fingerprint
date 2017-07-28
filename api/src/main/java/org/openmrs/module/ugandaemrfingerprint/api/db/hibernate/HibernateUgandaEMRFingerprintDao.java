/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 * <p>
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * <p>
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.ugandaemrfingerprint.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.module.ugandaemrfingerprint.api.db.UgandaEMRFingerprintDao;
import org.openmrs.module.ugandaemrfingerprint.core.FingerPrintTemplate;
import org.openmrs.module.ugandaemrfingerprint.model.Fingerprint;

import java.lang.reflect.Method;
import java.util.*;

/**
 * It is a default implementation of  {@link UgandaEMRFingerprintDao}.
 */
public class HibernateUgandaEMRFingerprintDao implements UgandaEMRFingerprintDao {
    protected final Log log = LogFactory.getLog(this.getClass());

    private SessionFactory sessionFactory;

    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public HibernateUgandaEMRFingerprintDao() {
    }

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    public List<Fingerprint> getPatientFingerPrint(String patientId) {
        Query sqlQuery = getCurrentSession().createQuery("from Fingerprint where patient='"+patientId+"'");

        sqlQuery.list();
        List<Fingerprint> fingerprintArrayList = sqlQuery.list();

        return fingerprintArrayList;
    }

    @Override
    public List<Fingerprint> getPatientFingerPrints() {
        Query sqlQuery = getCurrentSession().createQuery("from Fingerprint");

        sqlQuery.list();
        List<Fingerprint> fingerprintArrayList = sqlQuery.list();
        return fingerprintArrayList;
    }

    @Override
    public void savePatientFingerprint(Fingerprint fingerprint) {
        getCurrentSession().save(fingerprint);
    }

    @Override
    public void deletePatientFingerPrint(String patientId) {
        Query query = getCurrentSession().createQuery(
                "delete from Fingerprint where patient ='"+patientId+"'");
        query.executeUpdate();
    }


    private Session getCurrentSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (NoSuchMethodError ex) {
            try {
                Method method = sessionFactory.getClass().getMethod("getCurrentSession", null);
                return (org.hibernate.Session) method.invoke(sessionFactory, null);
            } catch (Exception e) {
                log.error("Failed to get the hibernate session", e);
            }
        }

        return null;
    }

}