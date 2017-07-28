package org.openmrs.module.ugandaemrfingerprint.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TextType;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.ugandaemrfingerprint.UgandaEMRFingerprintService;
import org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant;
import org.openmrs.module.ugandaemrfingerprint.model.Fingerprint;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

/**
 * Fragment controller for patient search widget; sets the min # of search characters based on global property,
 * and loads last viewed patients for current user if "showLastViewedPatients" fragment config param=true
 */
public class AddPatientFingerprintFragmentController implements Serializable {

    protected final Log log = LogFactory.getLog(this.getClass());

    public void controller(FragmentModel model, UiSessionContext sessionContext,
                           HttpServletRequest request,
                           @SpringBean("adminService") AdministrationService administrationService, @RequestParam(value = "patientId", required = false) String patient,
                           @FragmentParam(value = "showLastViewedPatients", required = false) Boolean showLastViewedPatients,
                           @FragmentParam(value = "initialSearchFromParameter", required = false) String searchByParam) {
        UgandaEMRFingerprintService ugandaEMRFingerprintService = Context.getService(UgandaEMRFingerprintService.class);

        if (!ugandaEMRFingerprintService.isUUID(patient)) {
            PatientService patientService = Context.getPatientService();
            patient = patientService.getPatient(Integer.parseInt(patient)).getUuid();
        }

        List<Fingerprint> fingerprint1 = ugandaEMRFingerprintService.getPatientFingerprint(patient);


        boolean rightThumbFingerEnrolled = false;
        boolean rightIndexFingerEnrolled = false;
        for (Fingerprint fingerprint2 : fingerprint1) {
            if (fingerprint2.getFinger() == FingerPrintConstant.RIGHT_THUMB) {
                rightThumbFingerEnrolled = true;
            }

            if (fingerprint2.getFinger() == FingerPrintConstant.RIGHT_INDEX) {
                rightIndexFingerEnrolled = true;
            }
        }

        model.addAttribute("noOFFingerprint", fingerprint1.size());
        model.addAttribute("rightThumbFingerEnrolled", rightThumbFingerEnrolled);
        model.addAttribute("rightIndexFingerEnrolled", rightIndexFingerEnrolled);
    }

    public SimpleObject saveFingerprint(FragmentModel model, @RequestParam(value = "patient", required = false) String patient,
                                        @RequestParam(value = "finger", required = false) Integer finger,
                                        @RequestParam(value = "fingerprint", required = false) Blob fingerprint,
                                        UiUtils ui) {


        SimpleObject obj = new SimpleObject();
        UgandaEMRFingerprintService ugandaEMRFingerprintService = Context.getService(UgandaEMRFingerprintService.class);

        Date dateCreated = new Date();

        Fingerprint fingerprint1 = new Fingerprint();
        fingerprint1.setFinger(finger);
        fingerprint1.setPatient(patient);
        fingerprint1.setFingerprint(fingerprint);
        fingerprint1.setDateCreated(dateCreated);

        ugandaEMRFingerprintService.savePatientFingerprint(fingerprint1);


        obj.put("message", "Patient fingerprint saved");

        return obj;
    }

    public SimpleObject deleteFingerprint(@RequestParam(value = "patient", required = false) String patient,
                                          UiUtils ui) {
        SimpleObject obj = new SimpleObject();
        UgandaEMRFingerprintService ugandaEMRFingerprintService = Context.getService(UgandaEMRFingerprintService.class);
        ugandaEMRFingerprintService.deletePatientFingerPrint(patient);
        obj.put("result", "Patient fingerprint Deleted");
        return obj;
    }
}
