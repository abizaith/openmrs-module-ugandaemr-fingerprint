<%
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

<h3>Locate Patient From other Facility</h3>

<form method="post">
    <fieldset style="min-width:100%">
        <div class="scan-input">
            <input type="text" name="onlinesearch" id="onlinesearch" class="field-display ui-autocomplete-input left"
                   placeholder="Type National Id" size="100">
        </div>

        <div class="left"><input type="submit" value="Search"></div>
    </fieldset>
</form>

<% if (searched == true && connectionStatus != "") { %>
<div class="toast-item-wrapper">
    <div class="toast-item toast-type-error">
        <div class="toast-item-image toast-item-image-error"></div>
        <div class="toast-item-close"></div>
        <p>${connectionStatus}</p>
    </div>
</div>


<% } %>

<% if (searched == true && patientFound == false) { %>
<div class="toast-item-wrapper">
    <div class="toast-item toast-type-error">
        <div class="toast-item-image toast-item-image-error"></div>
        <div class="toast-item-close"></div>
        <p>${patientNotFound}</p>
    </div>
</div>
<% } %>

<% if (patientFound == true && searched == true) { %>
<fieldset>
    <table>
        <thead>
        <td>Facility Name</td>
        <td>Facility Id</td>
        <td>Patient Id</td>
        <td>Patient Summary</td>
        </thead>
        <tbody>
        <td><% facilityName ?: "No Results" %></td>
        <td><% facilityId ?: "No Results" %></td>
        <td><% patientId ?: "No Results" %></td>
        <td><% patientSummary ?: "No Results" %></td>

        </tbody>
    </table>
</fieldset>
<% } %>