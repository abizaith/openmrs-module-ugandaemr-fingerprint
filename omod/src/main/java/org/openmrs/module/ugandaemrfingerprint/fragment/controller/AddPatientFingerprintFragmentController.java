package org.openmrs.module.ugandaemrfingerprint.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.AdministrationService;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.ugandaemrfingerprint.api.UgandaEMRFingerprintService;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Fragment controller for patient search widget; sets the min # of search characters based on global property,
 * and loads last viewed patients for current user if "showLastViewedPatients" fragment config param=true
 */
public class AddPatientFingerprintFragmentController {
    protected final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    UgandaEMRFingerprintService ugandaEMRFingerprintService;

    public void controller(FragmentModel model, UiSessionContext sessionContext,
                           HttpServletRequest request,
                           @SpringBean("adminService") AdministrationService administrationService,
                           @FragmentParam(value = "showLastViewedPatients", required = false) Boolean showLastViewedPatients,
                           @FragmentParam(value = "initialSearchFromParameter", required = false) String searchByParam) {

    }

    /*public SimpleObject saveFingerprint(@RequestParam(value = "patient", required = false) String patient,
                                        @RequestParam(value = "finger", required = false) Integer finger,
                                        @RequestParam(value = "fingerprint", required = false) String fingerprint,
                                        UiUtils ui) {

        SimpleObject obj = new SimpleObject();

        ConceptComplex conceptComplex = Context.getConceptService().getConceptComplex(163149);

        try {

            Person p = Context.getPersonService().getPersonByUuid(patient);

            Obs obs = new Obs(p, conceptComplex, new Date(), Context.getLocationService().getDefaultLocation());

            ComplexData complexData = new ComplexData(String.valueOf(finger),fingerprint.getBytes());

            obs.setComplexData(complexData);

            Context.getObsService().saveObs(obs, null);

            UgandaEMRFingerprintService service = Context.getService(UgandaEMRFingerprintService.class);

            Fingerprint fingerprint1 = new Fingerprint();
            fingerprint1.setFinger(finger);
            fingerprint1.setFingerprint(fingerprint.getBytes());
            fingerprint1.setPatient(p);

            System.out.println(fingerprint1);
            Fingerprint returned = service.saveFingerprint(fingerprint1);

            obj.put("fingerprint",returned.getFingerprint());
            obj.put("finger",returned.getFinger());
            obj.put("patient",returned.getPatient().getUuid());

        } catch (APIException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }*/
}
