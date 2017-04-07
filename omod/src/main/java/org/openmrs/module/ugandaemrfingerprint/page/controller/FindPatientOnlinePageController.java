package org.openmrs.module.ugandaemrfingerprint.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.ugandaemrfingerprint.core.Commons;
import org.openmrs.module.ugandaemrfingerprint.remoteserver.FingerPrintHttpURLConnection;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant.*;

/**
 * Created by lubwamasamuel on 24/02/2017.
 */
public class FindPatientOnlinePageController {

    protected final Log log = LogFactory.getLog(this.getClass());

    public void controller(UiSessionContext sessionContext, PageModel model) {
    }

    public void get(@SpringBean PageModel pageModel, @RequestParam(value = "breadcrumbOverride", required = false) String breadcrumbOverride,@RequestParam(value = "patientId", required = false) String patientId) {
        pageModel.put("searched", false);
        pageModel.put("patientId", patientId);

    }
}
