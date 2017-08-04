package org.openmrs.module.ugandaemrfingerprint.page.controller;

import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.ugandaemrfingerprint.UgandaEMRFingerprintService;
import org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant;
import org.openmrs.module.ugandaemrfingerprint.model.Fingerprint;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lubwamasamuel on 25/05/2017.
 */
public class FingerPrintStatisticPageController {

    public void controller(PageModel model, UiUtils ui, @RequestParam(value = "breadcrumbOverride", required = false) String breadcrumbOverride) throws ParseException {
        PatientService patientService = Context.getPatientService();
        UgandaEMRFingerprintService ugandaEMRFingerprintService = Context.getService(UgandaEMRFingerprintService.class);
        List<Fingerprint> fingerPrints = ugandaEMRFingerprintService.getPatientFingerprints();

        List<Fingerprint> rightIndex = new ArrayList<Fingerprint>();
        List<Fingerprint> rightThumb = new ArrayList<Fingerprint>();
        List<String> onlyPatientUUIDs = new ArrayList<String>();
        for (int i=0;i < fingerPrints.size();i++) {
            fingerPrints.get(i).getDateCreated();
            if (fingerPrints.get(i).getFinger() == FingerPrintConstant.RIGHT_INDEX) {
                rightIndex.add(fingerPrints.get(i));
            } else if (fingerPrints.get(i).getFinger() == FingerPrintConstant.RIGHT_THUMB) {
                rightThumb.add(fingerPrints.get(i));
            }
            if (!onlyPatientUUIDs.contains(fingerPrints.get(i).getPatient())) {
                onlyPatientUUIDs.add(fingerPrints.get(i).getPatient());
            }
        }

        model.addAttribute("fingerPrintTotalNo", onlyPatientUUIDs.size() + "");
        model.addAttribute("rightThumb", rightThumb.size());
        model.addAttribute("rightIndex", rightIndex.size());
        model.addAttribute("totalPatientsWithNoFPrint", (patientService.getAllPatients().size() - onlyPatientUUIDs.size()));
        model.addAttribute("totalPatients", patientService.getAllPatients().size());
        model.put("breadcrumbOverride", breadcrumbOverride);
    }
}
