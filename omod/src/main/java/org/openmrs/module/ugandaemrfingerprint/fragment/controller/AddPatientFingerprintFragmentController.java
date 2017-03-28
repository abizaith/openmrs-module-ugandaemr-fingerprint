package org.openmrs.module.ugandaemrfingerprint.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.PatientService;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.ugandaemrfingerprint.Fingerprint;
import org.openmrs.module.ugandaemrfingerprint.api.UgandaEMRFingerprintService;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Fragment controller for patient search widget; sets the min # of search characters based on global property,
 * and loads last viewed patients for current user if "showLastViewedPatients" fragment config param=true
 */
public class AddPatientFingerprintFragmentController {
    protected final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    PatientService patientService;

    @Autowired
    UgandaEMRFingerprintService ugandaEMRFingerprintService;

    public void controller(FragmentModel model, UiSessionContext sessionContext,
                           HttpServletRequest request,
                           @SpringBean("adminService") AdministrationService administrationService,
                           @FragmentParam(value = "showLastViewedPatients", required = false) Boolean showLastViewedPatients,
                           @FragmentParam(value = "initialSearchFromParameter", required = false) String searchByParam) {

    }

    public SimpleObject saveFingerprint(@RequestParam(value = "patient", required = false) String patient,
                                        @RequestParam(value = "finger", required = false) Integer finger,
                                        @RequestParam(value = "fingerprint", required = false) String fingerprint,
                                        UiUtils ui) {

        Patient p = patientService.getPatientByUuid(patient);
        System.out.println(p);
        System.out.println(patient);
        System.out.println(finger);
        System.out.println(fingerprint);
        /*Fingerprint fingerprint1 = new Fingerprint();
        fingerprint1.setFinger(finger);
        fingerprint1.setFingerprint(fingerprint.getBytes());
        fingerprint1.setPatient(p);
        */

        //Fingerprint returned = ugandaEMRFingerprintService.saveFingerprint(fingerprint1);

        //        return SimpleObject.fromObject(returned, ui, null);
        return null;
    }
}
