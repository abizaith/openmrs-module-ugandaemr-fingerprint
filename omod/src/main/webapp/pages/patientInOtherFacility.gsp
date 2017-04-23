<%
    ui.includeJavascript("ugandaemrfingerprint", "patientsearch/sockjs-0.3.4.js")
    ui.includeJavascript("ugandaemrfingerprint", "patientsearch/stomp.js")
    ui.decorateWith("appui", "standardEmrPage", [title: ui.message("Patients From Other Health Centers")])

    def htmlSafeId = { extension ->
        "${extension.id.replace(".", "-")}-${extension.id.replace(".", "-")}-extension"
    }

    def breadcrumbMiddle = breadcrumbOverride ?: '';
%>
<script type="text/javascript">

    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {
            label: "${ ui.escapeJs(ui.message("Find Patient From other Facility.")) }",
        }
    ];

    if (jQuery) {
        jq(document).ready(function () {

            if ("${patientId}" != "") {
                remoteSearch('fingerprint', "${patientId}");
            }

        });
    }

    function remoteSearch(search_type, search_params) {
        if (!search_params) {
            search_params = jq("#onlinesearch").val();
        }

        var searchQuery = '${searchString}'.replace('%s', search_params);

        if (search_type !== 'fingerprint') {
            searchQuery = '${nationalIdString}'.replace('%s', search_params);
        }

        jq("#status_message").html("");
        if (search_params != "") {
            if (navigator.onLine) {
                jq.post("${connectionProtocol+onlineIpAddress+queryURL}", {query: searchQuery},
                        function (response) {

                            if (response && response.data.patient !== null) {
                                displayData(response);
                                jq().toastmessage('showSuccessToast', "Patient Found");
                                jq("#patient_found").attr("display", "block");
                            }
                            else if (response.errors) {
                                jq().toastmessage('showErrorToast', "Internal Server Error");
                            }
                            else {
                                jq().toastmessage('showErrorToast', "Patient Not Found");
                                jq("#patient_found").attr("display", "none");
                            }
                        });
            } else {
                jq().toastmessage('showErrorToast', "No internet Connection");
            }
        }
        else {
            jq().toastmessage('showWarningToast', "No Search Parameters typed in the Search box");
        }
    }

    function displayData(response) {
        var patientNames = "" + response.data.patient.names[0].familyName + " " + response.data.patient.names[0].middleName + " " + response.data.patient.names[0].givenName;
        jq("#patientNames").html(patientNames);
        jq("#patientId").html(response.data.patient.uuid);
        jq("#age").html(response.data.patient.age);
        jq("#gender").html(patientNames);
        jq("#facilityName").html(response.data.patient.patientFacility.name);
        jq("#facilityId").html(response.data.patient.patientFacility.uuid);
        jq("#birthDate").html(response.data.patient.birthdate);
        jq("#gender").html(response.data.patient.gender);

        "${patientFound=true}";
        "${searched=true}";
        "${connectionStatus=""}";
    }


</script>
<style type="text/css">
#death-date-display {
    min-width: 35%;
}

span.field-error {
    padding: 1px 6px 1px 6px;
    margin-left: 4px;
    margin-right: 4px;
    vertical-align: middle;
    color: red;
}

img {
    width: 100px;
    height: auto;
}

.online_search_input_box {
    max-width: 94%;
    height: 34px;
}

.search_container {
    width: 65%;
}

.toast-position-top-right {
    position: fixed;
    top: 165px;
    right: 70px;
}

.dialog .dialog-header, .ngdialog.ngdialog-theme-default .ngdialog-content .dialog-header {
    background: #000;
}
</style>

<h3>Locate Patient From other Facility</h3>

<fieldset>
    <input type="hidden" name="fingerprint" id="fingerprint">

    <div class="scan-input left search_container">
        <input type="text" name="onlinesearch" id="onlinesearch"
               class="field-display ui-autocomplete-input left online_search_input_box"
               placeholder="Type National Id" size="100">
    </div>

    <div class="left"><input type="button" value="Search" onclick="remoteSearch()"></div>
</fieldset>

<div id="status_message"></div>
<% if (patientFound == true && searched == true) { %>

<div id="patient_found" class="dialog" style="width: 100%">
    <div class="dialog-header">
        <i class="icon-globe"></i>

        <h3>Patient Found:</h3>
    </div>

    <div class="dialog-content">

        <div>
            <h5>Patient Names:</h5>

            <div id="patientNames"></div>
        </div>

        <div>
            <h5>Sex:</h5>

            <div id="gender"></div>
        </div>

        <div>
            <h5>Date of Birth:</h5>

            <div id="birthDate"></div>
        </div>

        <div>
            <h5>Patient Id:</h5>

            <div id="patientId"></div>
        </div>

        <div>
            <h5>Facility Name:</h5>

            <div id="facilityName"></div>

            <h5>Facility Id:</h5>

            <div id="facilityId"></div>
        </div>
    </div>
</div>
<% } %>