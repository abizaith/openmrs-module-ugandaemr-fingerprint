package org.openmrs.module.ugandaemrfingerprint.core;

/**
 * Created by lubwamasamuel on 23/02/2017.
 */
public class FingerPrintConstant {
    public static String FINGER_PRINT_ATTRIBUTE_NAME = "FingerPrint";
    public static String FINGER_PRINT_ATTRIBUTE_DESCRIPTION = "This attribute is the type for person attributed which stores the ugandaemrfingerprint template";
    public static String FINGER_PRINT_ATTRIBUTE_UUID = "a41339f9-5014-45f4-91d6-bab84c6c62f1";


    public static final String CONNECTION_SERVER_IP_GLOBALPROPERTY = "ugandaemrsync.serverIP";

    public static final String CONNECTION_PROTOCOL = "http://";

    public static final int CONNECTION_SUCCESS = 200;

    public static final String XML_CONTENT_TYPE = "application/xml";

    public static final String NORMAL_CONTENT_TYPE = "application/x-wwww-form-urlencoded";

    public static final String JSON_CONTENT_TYPE = "application/json";

    public static final String SEARCH_URL = "/api/query";

    public static final String FACILITY_NAME = "facility.name";
    public static final String FACILITY_ID = "facility.id";
    public static final String PATIENT_ID = "patient.id";
    public static final String PATIENT_SUMMARY = "patient.summary";

    public static final String SEARCH_PARAMS_ATTRIBUTE = "{patient(patients:[\"%s\"]){uuid birthdate gender dead patientFacility{ uuid name} names{ familyName middleName givenName voided} summaryPage{ obs{ uuid encounterDate encounterType valueCoded valueText valueNumeric valueDatetime valueDrug valueBoolean valueComplex voided}} mostRecentEncounter{obs{ uuid encounterDate encounterType valueCoded valueText valueNumeric valueDatetime valueDrug valueBoolean valueComplex voided}}}}";
    public static final String PATIENT_ONLINE_ID = "identifiers";
    public static final String PATIENT_NOT_FOUND = "Patient Not Found";
    public static final String CONNECTION_TEST_IP = "google.com/";
    public static final String DEVICE_SOCKET_IP = "ugandaemrfingerprint.socketIPAddress";
    public static final String DEVICE_SOCKET_IP_PLACE_HOLDER = "/complete";

    public static String getAttributeSearch(String searchType, String attributeType, String attributeValue) {
        return String.format("query=patient(%s:{t:\"%s\",v:\"%s\"}){uuid}", searchType, attributeType, attributeValue);
    }

    public static String getFingerprintSearch(String fingerprint) {
        return String.format("query=patient(fingerprint:%s){uuid}", fingerprint);
    }
}
