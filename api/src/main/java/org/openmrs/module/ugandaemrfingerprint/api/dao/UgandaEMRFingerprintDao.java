/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.ugandaemrfingerprint.api.dao;

import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.ugandaemrfingerprint.Fingerprint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("ugandaemrfingerprint.UgandaEMRFingerprintDao")
public class UgandaEMRFingerprintDao {

    @Autowired
    DbSessionFactory sessionFactory;

    private DbSession getSession() {
        return sessionFactory.getCurrentSession();
    }

    public Fingerprint saveFingerprint(Fingerprint fingerprint) {
        getSession().saveOrUpdate(fingerprint);
        return fingerprint;
    }

    public Fingerprint getFingerprintByUuid(String uuid) {
        return (Fingerprint) getSession().createCriteria(Fingerprint.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }
}
