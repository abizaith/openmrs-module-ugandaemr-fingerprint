<%
    ui.decorateWith("appui", "standardEmrPage", [title: ui.message("aijar.markpatientdeceased.title")])

    def htmlSafeId = { extension ->
        "${extension.id.replace(".", "-")}-${extension.id.replace(".", "-")}-extension"
    }

    Calendar cal = Calendar.getInstance()
    def maxAgeYear = cal.get(Calendar.YEAR)
    def minAgeYear = maxAgeYear - patient.getAge()
    def breadcrumbMiddle = breadcrumbOverride ?: '';
%>
<script type="text/javascript">

    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {
            label: "${ ui.escapeJs(ui.message("aijar.markpatientdeceased.label")) }",
            link: '${ ui.urlBind("/" + contextPath + "coreapps/clinicianfacing/patient.page?patientId="+patientId, [ patientId: patient ] ) }'
        }
    ];
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
</style>
${ui.includeFragment("coreapps", "patientHeader", [patient: patient])}
<h3>${ui.message("aijar.markpatientdeceased.label")}</h3>

<form method="post" id="mark-patient-dead">
    <fieldset style="min-width: 40%">

        <p>
            <span>
                <input type="submit" value="Submit">
            </span>
        </p>
    </fieldset>
</form>