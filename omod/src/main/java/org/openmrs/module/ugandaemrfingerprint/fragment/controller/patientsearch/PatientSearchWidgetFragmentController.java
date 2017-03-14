package org.openmrs.module.ugandaemrfingerprint.fragment.controller.patientsearch;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch._impl.DPFPSampleFactoryImpl;
import com.digitalpersona.onetouch._impl.DPFPTemplateFactoryImpl;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.coreapps.CoreAppsConstants;
import org.openmrs.module.emrapi.utils.GeneralUtils;
import org.openmrs.module.ugandaemrfingerprint.core.Commons;
import org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant;
import org.openmrs.module.ugandaemrfingerprint.remoteserver.FingerPrintHttpURLConnection;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiFrameworkConstants;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Fragment controller for patient search widget; sets the min # of search characters based on global property,
 * and loads last viewed patients for current user if "showLastViewedPatients" fragment config param=true
 */
public class PatientSearchWidgetFragmentController {
	protected final Log log = LogFactory.getLog(this.getClass());


	private DPFPTemplate template;


	public void controller(FragmentModel model, UiSessionContext sessionContext,
						   HttpServletRequest request,
						   @SpringBean("adminService") AdministrationService administrationService,
						   @FragmentParam(value = "showLastViewedPatients", required = false) Boolean showLastViewedPatients,
						   @FragmentParam(value = "initialSearchFromParameter", required = false) String searchByParam) {

		showLastViewedPatients = showLastViewedPatients != null ? showLastViewedPatients : false;

		model.addAttribute("minSearchCharacters",
				administrationService.getGlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_MIN_SEARCH_CHARACTERS, "1"));

		model.addAttribute("searchDelayShort",
				administrationService.getGlobalProperty(CoreAppsConstants.GP_SEARCH_DELAY_SHORT, "300"));

		model.addAttribute("searchDelayLong",
				administrationService.getGlobalProperty(CoreAppsConstants.GP_SEARCH_DELAY_LONG, "1000"));

		model.addAttribute("dateFormatJS", "DD MMM YYYY");  // TODO really should be driven by global property, but currently we only have a property for the java date format
		model.addAttribute("locale", Context.getLocale().getLanguage());
		model.addAttribute("defaultLocale", new Locale(administrationService.getGlobalProperty((OpenmrsConstants.GLOBAL_PROPERTY_DEFAULT_LOCALE), "en")).getLanguage());
		model.addAttribute("dateFormatter", new SimpleDateFormat(administrationService.getGlobalProperty(UiFrameworkConstants.GP_FORMATTER_DATE_FORMAT),
				Context.getLocale()));
		model.addAttribute("showLastViewedPatients", showLastViewedPatients);

		String doInitialSearch = null;

		if (searchByParam != null && StringUtils.isNotEmpty(request.getParameter(searchByParam))) {
			doInitialSearch = request.getParameter(searchByParam);
		}
		model.addAttribute("doInitialSearch", doInitialSearch);

		if (showLastViewedPatients) {
			List<Patient> patients = GeneralUtils.getLastViewedPatients(sessionContext.getCurrentUser());
			model.addAttribute("lastViewedPatients", patients);

		}

	}

	public SimpleObject searchForPatientByFingerPrint(
			@RequestParam(value = "patientIdentifierId", required = false) String fingerPrintInBase64,
			@SpringBean("patientService") PatientService service,
			UiUtils ui) {
		String fingerPrintPersonAttributeTypeUUID = FingerPrintConstant.FINGER_PRINT_ATTRIBUTE_UUID;
		Patient searchedPatient = new Patient();
		SimpleObject simplePatientObject = null;
		String fingerPrintSampleInBase64 = fingerPrintInBase64;
		String uuid = "null";
		System.out.println("Server reached");
		try {

			Context.openSession();
			Context.authenticate("admin", "Admin123");
			//search patient attribute: leftIndexFingerPrint
			List<Patient> patients = Context.getPatientService().getAllPatients();

			ObjectMapper mapper = new ObjectMapper();

			Map resultsOnlin = null;

			boolean patientfound = false;
			if (fingerPrintSampleInBase64 != null) {


				for (Patient patientInstance : patients) {

					searchedPatient = patientInstance;

					List<PersonAttribute> personAttributes = patientInstance.getActiveAttributes();

					Context.getPatientService().getPatient(personAttributes.get(1).getPerson().getPersonId());

					for (PersonAttribute personAttribute : personAttributes) {

						if (personAttribute.getAttributeType().getUuid().equalsIgnoreCase(fingerPrintPersonAttributeTypeUUID)) {
							System.out.println("Person attributeType passed...");

							//test if the base64 generated matches our stored base64 text
							if (personAttribute.getValue() != null) {
								System.out.println("....1...");
								this.generateDPFPTemplate(personAttribute.getValue());
								System.out.println("....2...");

								if (this.matchFound(this.generateDPFPSample(fingerPrintSampleInBase64))) {
									System.out.println("....3...");
									//System.out.println("Patient UUID: "+patientInstance.getUuid());
									System.out.println("Fingerprint match successfully carried out.............................................");
									System.out.println("Patient UUID: " + patientInstance.getUuid());
									System.out.println("Second Person name: " + patientInstance.getPerson().getPersonName().getUuid());

									uuid = patientInstance.getPerson().getPersonName().getUuid();
									System.out.println("Person UUID: " + uuid);
									searchedPatient = patientInstance;
									patientfound = true;
									break;
								} else {
									searchedPatient = null;
									patientfound = false;
								}
							}

						}
					}

					//end person attribute loop


					if (uuid != "null") {
						break;
					}
				}
			}//end patient loop

			if (!patientfound) {
				FingerPrintHttpURLConnection fingerPrintHttpURLConnection = new FingerPrintHttpURLConnection();
				Commons commons = new Commons();
				resultsOnlin = fingerPrintHttpURLConnection.sendPostBy(FingerPrintConstant.SEARCH_URL, commons.getRequestString(FingerPrintConstant.SEARCH_PARAMS, fingerPrintSampleInBase64));
			}

			if (!resultsOnlin.isEmpty()) {
				String pageName = "clientInOtherFacility?patientId=" + resultsOnlin.get(FingerPrintConstant.PATIENT_ID) + " & patientSummary=" + resultsOnlin.get(FingerPrintConstant.PATIENT_SUMMARY) + " & facilityName=" + resultsOnlin.get(FingerPrintConstant.FACILITY_NAME) + " & facilityId=" + resultsOnlin.get(FingerPrintConstant.FACILITY_ID);
				ui.pageLink("coreapps", pageName);
				String[] properties = {"uuid"};
				simplePatientObject = SimpleObject.fromObject(searchedPatient, ui, properties);
			}
		} catch (Exception e) {
			log.error(e);
			e.getStackTrace();
		} finally {
			Context.closeSession();
		}

		String pageName = "patient.page?patientId=" + uuid;
		ui.pageLink("coreapps", pageName);
		String[] properties = {"uuid"};
		if (simplePatientObject != null) {
			simplePatientObject = SimpleObject.fromObject(searchedPatient, ui, properties);
		}


		return simplePatientObject;
	}

	private boolean matchFound(DPFPSample sample) {
		DPFPVerification verifier = DPFPGlobal.getVerificationFactory().createVerification();
		// Process the sample and create a feature set for the enrollment purpose.
		DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

		// Check quality of the sample and start verification if it's good
		if (features != null) {
			// Compare the feature set with our template
			DPFPVerificationResult result = verifier.verify(features, getTemplate());

			if (result.isVerified())
				return true;
			else
				return false;
		}
		return false;


	}

	protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose) {
		DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		try {
			return extractor.createFeatureSet(sample, purpose);
		} catch (DPFPImageQualityException e) {
			return null;
		}
	}

	private DPFPSample generateDPFPSample(String receivedBase64String) {
		try {

			byte[] receivedBytes = Base64.getDecoder().decode(receivedBase64String);
			System.out.println("....byte[] conversion...");

			DPFPSampleFactoryImpl sampleFactoryImpInstance = new DPFPSampleFactoryImpl();
			DPFPSample sampleInstance = sampleFactoryImpInstance.createSample(receivedBytes);

			System.out.println("....Sample generation ..done.....");
			return sampleInstance;
		} catch (Exception e) {
			System.out.println("....Sample generation. failed.....: " + e.getMessage());
			return null;
		}

		//DPFPSample sampleInstance = new DPFPSample();
	}

	private void generateDPFPTemplate(String storedBase64String) {
		try {
            /*byte[] templateInBase64Array = Base64.getDecoder().decode(storedBase64String);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(templateInBase64Array);
	    	ObjectInputStream objectInputStreamInstance = new ObjectInputStream(byteArrayInputStream);
	    	DPFPTemplate templateInstance = (DPFPTemplate)objectInputStreamInstance.readObject();
	    	this.setTemplate(templateInstance);*/

			byte[] templateInBase64Array = Base64.getDecoder().decode(storedBase64String);
			DPFPTemplateFactoryImpl templateFactory = new DPFPTemplateFactoryImpl();
			DPFPTemplate templateInstance = templateFactory.createTemplate(templateInBase64Array);
			System.out.println("....Template generation ..done.....");

			this.setTemplate(templateInstance);
		} catch (Exception e) {
			System.out.println("....Template generation. failed.....: " + e.getMessage());

		}

	}

	public DPFPTemplate getTemplate() {
		return template;
	}

	public void setTemplate(DPFPTemplate template) {
		this.template = template;
	}


	public SimpleObject searchForPatientByFingerPrintBak(
			@RequestParam(value = "datakey", required = false) String fingerPrintInBase64,
			@SpringBean("patientService") PatientService service,
			UiUtils ui) {
		//String fingerPrintPersonAttributeTypeUUID = "0fe8824e-f9f8-42fa-a919-4d2dcd00a5da";
		Patient searchedPatient = new Patient();


		String uuid = "null";
		System.out.println("Server reached");


		try {
			Context.openSession();
			Context.authenticate("admin", "Admin123");


			//search patient attribute: leftIndexFingerPrint
			List<Patient> patients = Context.getPatientService().getAllPatients();
			System.out.println("Number of Patients: " + patients.size());

			if (fingerPrintInBase64 != null) {


				for (Patient patientInstance : patients) {
                /*int lastIndex = patients.size() - 1 ;
                for(int index = lastIndex; index > -1; index--){

        			Patient patientInstance = patients.get(index)*/
					;
					searchedPatient = patientInstance;
                    /*List<PersonAttribute> personAttributes = patientInstance.getActiveAttributes();

        			for(PersonAttribute personAttribute: personAttributes){
        				System.out.println("Person Attribute: "+personAttribute.getValue());

        					System.out.println("Person Attribute Type UUID: "+personAttribute.getAttributeType().getUuid());
        					if(personAttribute.getAttributeType().getUuid().equalsIgnoreCase(fingerPrintPersonAttributeTypeUUID)){
            					System.out.println("Person attributeType passed...");
            					//test if the base64 generated matches our stored base64 text
            					if(personAttribute.getValue() != null ){
            						System.out.println("Patient UUID: "+patientInstance.getUuid());
            						uuid = patientInstance.getUuid();
            						searchedPatient = patientInstance;
            						break;
            					}
            				}

        			}//end person attribute loop
        			 */
					if (uuid != "null") {
						break;
					}
				}//end patient loop
			}
		} catch (Exception e) {
			System.out.println(".......exception..................................................................");
			e.getStackTrace();
		} finally {

			Context.closeSession();
		}

		String pageName = "patient.page?patientId=" + uuid;
		ui.pageLink("coreapps", pageName);
		String[] properties = {"uuid"};
		SimpleObject simplePatientObject = SimpleObject.fromObject(searchedPatient, ui, properties);


		return simplePatientObject;
	}

}
