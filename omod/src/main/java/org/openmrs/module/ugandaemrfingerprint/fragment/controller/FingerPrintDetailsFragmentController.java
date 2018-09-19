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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant.*;
import static org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant.LEFT_THUMB_NAME;

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

        Map map=new HashMap();

        fingerprints = ugandaEMRFingerprintService.getPatientFingerprint(patient);
        String finger1DateCreated = null;
        String finger2DateCreated = null;
        String finger1 = "";
        String finger2 = "";
        for (Fingerprint fingerprint : fingerprints) {
            switch (fingerprint.getFinger()) {
                case 1:
                    map.put(RIGHT_PINKY_NAME, fingerprint.getDateCreated().toString());
                    break;
                case 2:
                    map.put(RIGHT_RING_NAME, fingerprint.getDateCreated().toString());
                    break;
                case 3:
                    map.put(RIGHT_MIDDLE_NAME, fingerprint.getDateCreated().toString());
                    break;
                case 4:
                    map.put(RIGHT_INDEX_NAME, fingerprint.getDateCreated().toString());
                    break;
                case 5:
                    map.put(RIGHT_THUMB_NAME, fingerprint.getDateCreated().toString());
                    break;
                case 6:
                    map.put(LEFT_PINKY_NAME, fingerprint.getDateCreated().toString());
                    break;
                case 7:
                    map.put(LEFT_RING_NAME, fingerprint.getDateCreated().toString());
                    break;
                case 8:
                    map.put(LEFT_MIDDLE_NAME, fingerprint.getDateCreated().toString());
                    break;
                case 9:
                    map.put(LEFT_INDEX_NAME, fingerprint.getDateCreated().toString());
                    break;
                case 10:
                    map.put(LEFT_THUMB_NAME, fingerprint.getDateCreated().toString());
                    break;
                default: ;
                    break;
            }
        }




        model.addAttribute("fingerPrint", map);
        model.addAttribute("fingerPrintNo", fingerprints.size());
    }
}