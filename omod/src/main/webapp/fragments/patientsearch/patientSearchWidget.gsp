<%
    config.require("afterSelectedUrl")
    def breadcrumbOverride = config.breadcrumbOverride ?: ""

    ui.includeCss("uicommons", "datatables/dataTables_jui.css")
    ui.includeCss("ugandaemrfingerprint", "patientsearch/patientSearchWidget.css")
    ui.includeCss("ugandaemrfingerprint", "patientsearch/fontcustom_findpatient_fingerprint.css")
    ui.includeJavascript("uicommons", "datatables/jquery.dataTables.min.js")
    ui.includeJavascript("ugandaemrfingerprint", "patientsearch/patientSearchWidget.js")
    ui.includeJavascript("ugandaemrfingerprint", "patientsearch/patientFingerPrintSearch.js")
    ui.includeJavascript("uicommons", "moment-with-locales.min.js")

%>
<script type="text/javascript">

    var lastViewedPatients = [];
    <%  if (showLastViewedPatients && !doInitialSearch) {
            lastViewedPatients.each { it -> %>
    lastViewedPatients.push({
        uuid: "${ it.uuid }",
        name: "${ it.personName ? ui.escapeJs(ui.format(it.personName)) : '' }",
        gender: "${ it.gender }",
        age: "${ it.age ?: '' }",
        birthdate: "${ it.birthdate ? dateFormatter.format(it.birthdate) : '' }",
        birthdateEstimated: ${ it.birthdateEstimated },
        identifier: "${ it.patientIdentifier ? ui.escapeJs(it.patientIdentifier.identifier) : '' }"
    });
    <%      }
        }%>
    function handlePatientRowSelection() {
        this.handle = function (row) {
            var uuid = row.uuid;
            location.href = '/' + OPENMRS_CONTEXT_PATH + emr.applyContextModel('${ ui.escapeJs(config.afterSelectedUrl) }', {
                        patientId: uuid,
                        breadcrumbOverride: '${ ui.escapeJs(breadcrumbOverride) }'
                    });
        }
    }
    var handlePatientRowSelection = new handlePatientRowSelection();
    jq(function () {
        var widgetConfig = {
            initialPatients: lastViewedPatients,
            doInitialSearch: ${ doInitialSearch ? "\"" + ui.escapeJs(doInitialSearch) + "\"" : "null" },
            minSearchCharacters: ${ config.minSearchCharacters ?: 3 },
            afterSelectedUrl: '${ ui.escapeJs(config.afterSelectedUrl) }',
            breadcrumbOverride: '${ ui.escapeJs(breadcrumbOverride) }',
            searchDelayShort: ${ searchDelayShort },
            searchDelayLong: ${ searchDelayLong },
            handleRowSelection: ${ config.rowSelectionHandler ?: "handlePatientRowSelection" },
            dateFormat: '${ dateFormatJS }',
            locale: '${ locale }',
            defaultLocale: '${ defaultLocale }',

            messages: {
                info: '${ ui.message("coreapps.search.info") }',
                first: '${ ui.message("coreapps.search.first") }',
                previous: '${ ui.message("coreapps.search.previous") }',
                next: '${ ui.message("coreapps.search.next") }',
                last: '${ ui.message("coreapps.search.last") }',
                noMatchesFound: 'No matching Patient found in this hospital. Click to start remote search',
                noData: 'Patient file not found in this hospital. Click to start remote search',
                recent: '${ ui.message("coreapps.search.label.recent") }',
                searchError: '${ ui.message("coreapps.search.error") }',
                identifierColHeader: '${ ui.message("coreapps.search.identifier") }',
                nameColHeader: '${ ui.message("coreapps.search.name") }',
                genderColHeader: '${ ui.message("coreapps.gender") }',
                ageColHeader: '${ ui.message("coreapps.age") }',
                birthdateColHeader: '${ ui.message("coreapps.birthdate") }'
            }
        };

        new PatientSearchWidget(widgetConfig);
    });


</script>
<script type="text/javascript">
    var myVar;
    function myFunction() {
        document.getElementById("loader").className = "load";
        myVar = setTimeout(showPage, 5000);
    }

    function showPage() {
        document.getElementById("loader").style.display = "none";
        document.getElementById("myDiv").style.display = "block";

    }

    function writeToHiddenFingerprintSearchTextbox(fingerPrintSample) {

        var fingerPrintTextbox = document.getElementById('fingerPrintSearchTextbox');
        fingerPrintTextbox.value = fingerPrintSample;

        alert("Fingerprint sample received...");


        jq = jQuery;
        jq(function () {
            jq('#testAjaxButton').click(function () {
                var fingerprintSample = jq("#fingerPrintSearchTextbox").val();

                jq.post('${ ui.actionLink("searchForPatientByFingerPrint") }', {
                    returnFormat: 'json',
                    patientIdentifierId: fingerprintSample
                }, function (data) {
                    if (data != null) {
                        //alert('..Mukyala Nkuggwa...');
                        window.location = "../../coreapps/clinicianfacing/patient.page?patientId=" + data.uuid;

                    } else {
                        alert('Patient not found by ugandaemrfingerprint');
                    }

                }, 'json')
                        .error(function (xhr) {
                            fragmentActionError(xhr, "Failed to find match");
                        })
            });
        });

        jq('#testAjaxButton').trigger("click");


    }





</script>

<form method="get" id="patient-search-form" onsubmit="return false">
    <input type="text" id="patient-search" placeholder="${ui.message("coreapps.findPatient.search.placeholder")}"
           autocomplete="off" <% if (doInitialSearch) { %>value="${doInitialSearch}" <% } %>/>
    <i id="patient-search-clear-button" class="small icon-remove-sign"></i>
    <i id="patient-search-finger-print-button" class="small icon-fingerprint"></i>
    <a href="${ui.urlBind("/" + contextPath +"ugandaemrfingerprint/patientInOtherFacility.page")}"><i title="Search Patient Online" class="small icon-globe">Search For Patient Online</i></a>
</form>

<div id="patient-search-finger-print" style="display:none;">
    <br>
    <textarea rows="4" cols="50" id="fingerPrintSearchTextbox" name="fingerPrintSearchTextbox"
              style="display:none;"></textarea>
    <Button id="testAjaxButton" style="display:none;"></Button>

    <p>Search functionality</p>

    <applet width="600" height="300" archive="finger-print-applet.jar"
            code="org.openmrs.module.ugandaemrfingerprint.applet.PatientSearchApplet.class" codebase="/openmrs"
            name="fingerApplet">
        <param value="16" name="patientId"></param>
        <param value="false" name="codebase_lookup"></param>
    </applet>
</div>

<div id="patient-search-results"></div>

<div id="remote-search">
    <Button id="remoteSearchButton" onClick="myFunction()">click here to start remote search</Button>

    <div id="loader"></div>

    <div style="display:none;" id="myDiv" class="animate-bottom">
        <h2>Server Response!</h2>

        <p>patient not found</p>
    </div>
</div>
