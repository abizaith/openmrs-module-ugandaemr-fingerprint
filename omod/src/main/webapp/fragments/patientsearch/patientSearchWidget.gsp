<%
    config.require("afterSelectedUrl")
    def breadcrumbOverride = config.breadcrumbOverride ?: ""
    ui.includeCss("ugandaemrfingerprint", "patientsearch/patientSearchWidget.css")
    ui.includeCss("ugandaemrfingerprint", "patientsearch/fontcustom_findpatient_fingerprint.css")
    ui.includeJavascript("uicommons", "datatables/jquery.dataTables.min.js")
    ui.includeJavascript("ugandaemrfingerprint", "patientsearch/patientSearchWidget.js")
%>

<script type="text/javascript">


    function connect() {

    }

    jq(function () {
        jq('#testAjaxButton').click(function () {
            jq.getJSON('${ ui.actionLink("getFingerprint") }',
                function (data) {
                    console.log(data);
                })
                .error(function (xhr) {
                    console.log(xhr)
                })
        });
    });
</script>

<div id="calculationDiv">

    <button id="search" onclick="search();">Search Person</button>

    <p id="calResponse"></p>

    <div id="images"></div>
</div>