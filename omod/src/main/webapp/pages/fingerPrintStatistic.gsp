<%
    ui.decorateWith("appui", "standardEmrPage", [title: ui.message("FingerPrint Statistics")])

    def htmlSafeId = { extension ->
        "${extension.id.replace(".", "-")}-${extension.id.replace(".", "-")}-extension"
    }

    def breadcrumbMiddle = breadcrumbOverride ?: '';
%>
<script type="text/javascript">

    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {
            label: "${ ui.escapeJs(ui.message("FingerPrint Statistics")) }",
        }
    ];
</script>
<style type="text/css">
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
</style>

<h3>Finger Print Statistics</h3>
<fieldset>
    <table>
        <thead>
        <tr>
            <th>Patients With Right Thumb</th>
            <th>Patients With Right Index</th>
            <th>Patients With FingerPrint</th>
            <th>Patients No FingerPrint</th>
            <th>Total Patients</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${rightThumb}</td>
            <td>${rightIndex}</td>
            <td>${fingerPrintTotalNo}</td>
            <td>${totalPatientsWithNoFPrint}</td>
            <td>${totalPatients}</td>
        </tr>
        </tbody>
    </table>
</fieldset>