package org.openmrs.module.ugandaemrfingerprint.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.ugandaemrfingerprint.model.Fingerprint;
import org.openmrs.module.ugandaemrfingerprint.UgandaEMRFingerprintService;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lubwama Samuel on 26/01/2016.
 */
public class FingerPrintDetailsFragmentController {

    private static final Log log = LogFactory.getLog(FingerPrintDetailsFragmentController.class);

    public void controller(FragmentModel model, @RequestParam(value = "patientId", required = false) String patient, UiUtils ui) throws ParseException {

        SimpleObject obj = new SimpleObject();
        UgandaEMRFingerprintService ugandaEMRFingerprintService = (UgandaEMRFingerprintService) Context.getService(UgandaEMRFingerprintService.class);
        List<Fingerprint> fingerprints = new ArrayList<Fingerprint>();


        if (!ugandaEMRFingerprintService.isUUID(patient) && patient != "") {
            PatientService patientService = Context.getPatientService();
            patient = patientService.getPatient(Integer.parseInt(patient)).getUuid();
        }

        fingerprints = ugandaEMRFingerprintService.getPatientFingerprint(patient);
        String finger1DateCreated = null;
        String finger2DateCreated = null;
        String finger1 = "";
        String finger2 = "";
        for (Fingerprint fingerprint : fingerprints) {
            if (fingerprint.getFinger() == 6) {
                finger1DateCreated = fingerprint.getDateCreated().toString();
                finger1 = fingerprint.getFinger() + "";
            } else if (fingerprint.getFinger() == 5) {
                finger2DateCreated = fingerprint.getDateCreated().toString();
                finger2 = fingerprint.getFinger() + "";
            }
        }

        model.addAttribute("fingerPrint", fingerprints);
        model.addAttribute("finger1DateCreated", finger1DateCreated);
        model.addAttribute("finger2DateCreated", finger2DateCreated);
        model.addAttribute("finger1", finger1);
        model.addAttribute("finger2", finger2);
        model.addAttribute("fingerPrintNo", fingerprints.size());
    }
}