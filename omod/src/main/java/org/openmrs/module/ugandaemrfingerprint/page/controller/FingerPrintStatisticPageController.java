package org.openmrs.module.ugandaemrfingerprint.page.controller;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant;
import org.openmrs.module.ugandaemrfingerprint.core.FingerPrintTemplate;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lubwamasamuel on 25/05/2017.
 */
public class FingerPrintStatisticPageController {

    public void controller(PageModel model, UiUtils ui, @RequestParam(value = "breadcrumbOverride", required = false) String breadcrumbOverride) throws ParseException {

        SimpleObject obj = new SimpleObject();
        PatientService patientService = Context.getPatientService();

        Session session = Context.getRegisteredComponent("sessionFactory", SessionFactory.class).getCurrentSession();

        session.beginTransaction();

        String query = "SELECT * FROM fingerprint";
        SQLQuery sqlQuery = session.createSQLQuery(query);

        List<String> columns = Arrays.asList("fingerprint_id", "finger", "fingerprint", "date_created", "patient");

        for (String column : columns) {
            sqlQuery.addScalar(column, StringType.INSTANCE);
        }
        List<Object[]> results = sqlQuery.list();
        List<FingerPrintTemplate> fingerPrintTemplates = new ArrayList<FingerPrintTemplate>();
        List<FingerPrintTemplate> rightIndex = new ArrayList<FingerPrintTemplate>();
        List<FingerPrintTemplate> rightThumb = new ArrayList<FingerPrintTemplate>();
        List<String> onlyPatientUUIDs = new ArrayList<String>();
        for (Object[] resultItem : results) {
            FingerPrintTemplate fingerPrintTemplate = new FingerPrintTemplate(resultItem[0].toString(), resultItem[1].toString(), resultItem[2].toString(), resultItem[3].toString());
            fingerPrintTemplate.getDateCreated();
            if (fingerPrintTemplate.getFinger().contentEquals(FingerPrintConstant.RIGHT_INDEX)) {
                rightIndex.add(fingerPrintTemplate);
            } else if (fingerPrintTemplate.getFinger().contentEquals(FingerPrintConstant.RIGHT_THUMB)) {
                rightThumb.add(fingerPrintTemplate);
            }
            if (!onlyPatientUUIDs.contains(resultItem[4].toString())) {
                onlyPatientUUIDs.add(resultItem[4].toString());
            }
        }

        model.addAttribute("fingerPrintTotalNo", onlyPatientUUIDs.size()+"");
        model.addAttribute("rightThumb", rightThumb.size());
        model.addAttribute("rightIndex", rightIndex.size());
        model.addAttribute("totalPatientsWithNoFPrint", (patientService.getAllPatients().size()-onlyPatientUUIDs.size()));
        model.addAttribute("totalPatients", patientService.getAllPatients().size());
        model.put("breadcrumbOverride", breadcrumbOverride);

    }
}
