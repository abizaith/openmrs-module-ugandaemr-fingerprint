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
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.ugandaemrfingerprint.api.UgandaEMRFingerprintService;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;

/**
 * Fragment controller for patient search widget; sets the min # of search characters based on global property,
 * and loads last viewed patients for current user if "showLastViewedPatients" fragment config param=true
 */
public class AddPatientFingerprintFragmentController implements Serializable{
    protected final Log log = LogFactory.getLog(this.getClass());

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

        SimpleObject obj = new SimpleObject();


        Session session = Context.getRegisteredComponent("sessionFactory", SessionFactory.class).getCurrentSession();

        session.beginTransaction();

        String query = "INSERT INTO fingerprint(patient,finger,fingerprint,date_created) VALUES (:patient,:finger,:fingerprint,:dateCreated)";
        SQLQuery sqlQuery = session.createSQLQuery(query);

        //        byte[] fingerprintByte = Base64.getDecoder().decode(fingerprint);

        Date dateCreated = new Date();

        sqlQuery.setParameter("patient", patient, StringType.INSTANCE);
        sqlQuery.setParameter("finger", finger, IntegerType.INSTANCE);
        sqlQuery.setParameter("fingerprint", fingerprint, TextType.INSTANCE);
        sqlQuery.setParameter("dateCreated", dateCreated, DateType.INSTANCE);

        sqlQuery.executeUpdate();

        session.getTransaction().commit();

        obj.put("message", "Patient fingerprint saved");

        return obj;
    }

    public SimpleObject deleteFingerprint(@RequestParam(value = "patient", required = false) String patient,
                                        UiUtils ui) {
        SimpleObject obj = new SimpleObject();

        Session session = Context.getRegisteredComponent("sessionFactory", SessionFactory.class).getCurrentSession();

        session.beginTransaction();

        String query = "DELETE FROM fingerprint WHERE patient ='"+patient+"'";
        SQLQuery sqlQuery = session.createSQLQuery(query);

        sqlQuery.executeUpdate();

        session.getTransaction().commit();

        obj.put("result", "Patient fingerprint Deleted");

        return obj;
    }
}
