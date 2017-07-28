<style type="text/css">
img {
    width: 100px;
    height: auto;
}
</style>

<div class="info-section patientsummary">
    <div class="info-header">
        <i class="icon-hand-up"></i>

        <h3>${ui.message("Fingerprint Details".toUpperCase())}</h3>
    </div>

    <div id="images" class="info-body">
        <% if (fingerPrintNo <= 0) { %>
        <div>
            <strong>${ui.message("No Fingerprint Captured.")}</strong>
        </div>
        <%
            } else {
                    if (finger2 == "5") {
        %>
        <div>
            <strong>Right Thumb:</strong>Enrolled On ${Date.parse("yyyy-M-d H:m:s",dateCreated)}
        </div>
        <%

            } else if (finger1 == "6") {
        %>
        <div>
            <strong>Right Index:</strong>Enrolled On ${Date.parse("yyyy-M-d H:m:s",dateCreated)}
        </div>
        <%

        %>

        <%
                }
            }
        %>
    </div>
</div>