/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.ugandaemrfingerprint.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.User;
import org.openmrs.api.PatientService;
import org.openmrs.api.UserService;
import org.openmrs.module.ugandaemrfingerprint.Fingerprint;
import org.openmrs.module.ugandaemrfingerprint.api.db.UgandaEMRFingerprintDao;
import org.openmrs.module.ugandaemrfingerprint.api.impl.UgandaEMRFingerprintServiceImpl;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * This is a unit test, which verifies logic in UgandaEMRSyncService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class UgandaEMRFingerprintServiceTest {

    @InjectMocks
    UgandaEMRFingerprintServiceImpl basicModuleService;

    @Mock
    UgandaEMRFingerprintDao dao;

    @Mock
    PatientService patientService;

    @Mock
    UserService userService;

    @Before
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }

    /*@Test
    public void saveItem_shouldSetOwnerIfNotSet() {
        //Given
        String finger = "This is a test fingerprint";
        Fingerprint fingerprint = new Fingerprint();
        fingerprint.setFinger(1);
        fingerprint.setFingerprint(finger.getBytes());

        when(dao.saveFingerprint(fingerprint)).thenReturn(fingerprint);

        User user = new User();
        when(userService.getUser(1)).thenReturn(user);

        //When
        basicModuleService.saveFingerprint(fingerprint);

        //Then
        assertThat(fingerprint, hasProperty("finger", is(1)));
    }*/
}
