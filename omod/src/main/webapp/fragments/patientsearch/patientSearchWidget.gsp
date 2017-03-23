<%
    config.require("afterSelectedUrl")
    def breadcrumbOverride = config.breadcrumbOverride ?: ""
    ui.includeCss("ugandaemrfingerprint", "patientsearch/patientSearchWidget.css")
    ui.includeCss("ugandaemrfingerprint", "patientsearch/fontcustom_findpatient_fingerprint.css")
    ui.includeJavascript("ugandaemrfingerprint", "patientsearch/sockjs-0.3.4.js")
    ui.includeJavascript("ugandaemrfingerprint", "patientsearch/stomp.js")
    ui.includeJavascript("uicommons", "datatables/jquery.dataTables.min.js")
    ui.includeJavascript("ugandaemrfingerprint", "patientsearch/patientSearchWidget.js")
%>

<script type="text/javascript">

    var stompClient = null;

    var socket = new SockJS('http://localhost:8084/complete/add');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/showResult', function (calResult) {
            showResult(JSON.parse(calResult.body));
        });
    });

    function connect() {

    }

    function sendNum(finger) {
        document.getElementById('calResponse').innerHTML = "";
        document.getElementById('images').innerHTML = "";
        stompClient.send("/calcApp/add", {}, JSON.stringify({
            patientId: '14', // Replace this with patientId
            patientUUID: '9948484848', // Replace this with patient uuid
            'finger': finger
        }));
    }

    function search(finger) {
        document.getElementById('calResponse').innerHTML = "";
        document.getElementById('images').innerHTML = "";
        stompClient.send("/calcApp/search", {});
    }

    function showResult(message) {
        var response = document.getElementById('calResponse');
        var imageDiv = document.getElementById('images');
        if (message.type === "image") {
            var imageTag = document.createElement('img');
            imageTag.src = "data:image/png;base64," + message.result;
            imageDiv.appendChild(imageTag);
        } else {
            response.innerHTML = message.result;
        }
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
    <button id="thumb" onclick="sendNum(5);">Scan Right Thumb</button>
    <button id="index" onclick="sendNum(6);">Scan Right Index</button>
    <button id="search" onclick="search();">Search Person</button>

    <p id="calResponse"></p>

    <div id="images"></div>
</div>