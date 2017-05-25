package org.openmrs.module.ugandaemrfingerprint.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
import org.openmrs.api.context.Context;
import org.openmrs.module.ugandaemrfingerprint.core.FingerPrintTemplate;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ssmusoke on 26/01/2016.
 */
public class FingerPrintDetailsFragmentController {

    private static final Log log = LogFactory.getLog(FingerPrintDetailsFragmentController.class);

    public void controller(FragmentModel model, @RequestParam(value = "patientId", required = false) String patient, UiUtils ui) throws ParseException {

        SimpleObject obj = new SimpleObject();

        Session session = Context.getRegisteredComponent("sessionFactory", SessionFactory.class).getCurrentSession();

        session.beginTransaction();

        String query = "SELECT * FROM fingerprint WHERE patient ='" + patient + "'";
        SQLQuery sqlQuery = session.createSQLQuery(query);

        List<String> columns = Arrays.asList("fingerprint_id", "finger", "fingerprint", "date_created");

        for (String column : columns) {
            sqlQuery.addScalar(column, StringType.INSTANCE);
        }
        List<Object[]> results = sqlQuery.list();
        List<FingerPrintTemplate> fingerPrintTemplates = new ArrayList<FingerPrintTemplate>();
        Iterator iterator = results.iterator();
        for (Object[] resultItem : results) {
            FingerPrintTemplate fingerPrintTemplate = new FingerPrintTemplate(resultItem[0].toString(), resultItem[1].toString(), resultItem[2].toString(), resultItem[3].toString());
            fingerPrintTemplate.getDateCreated();
            fingerPrintTemplates.add(fingerPrintTemplate);
        }


        model.addAttribute("fingerPrint", fingerPrintTemplates);
        model.addAttribute("fingerPrintNo", fingerPrintTemplates.size());
    }
}