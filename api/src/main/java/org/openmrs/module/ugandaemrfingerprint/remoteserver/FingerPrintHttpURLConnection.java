package org.openmrs.module.ugandaemrfingerprint.remoteserver;

/**
 * Created by lubwamasamuel on 11/10/16.
 */

import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.module.ugandaemrfingerprint.core.Commons;
import org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class FingerPrintHttpURLConnection {

    public FingerPrintHttpURLConnection() {
    }

    private final String USER_AGENT = "Mozilla/5.0";

    // HTTP GET request
    public HttpURLConnection sendGet(String content, String protocol) throws Exception {

        URL obj = new URL(protocol + content);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        return con;

    }

    public int getCheckConnection(String url) throws Exception {
        return sendGet(url, FingerPrintConstant.CONNECTION_PROTOCOL).getResponseCode();
    }

    public StringBuffer getResponseString(BufferedReader bufferedReader) throws IOException {
        String inputLine;

        StringBuffer response = new StringBuffer();

        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        return response;
    }

    // HTTP POST request

    public Map sendPostBy(String contentType, String content, String facilityId, String url) throws Exception {

        try {
            URL url1 = new URL(url);
            URLConnection con = url1.openConnection();

            // specify that we will send output and accept input
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setConnectTimeout(20000); // long timeout, but not infinite
            con.setReadTimeout(20000);
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);

            // tell the servlets server what we are sending
            con.setRequestProperty("Content-Type", contentType);
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Accept", "application/json");
            if (facilityId != null) {

                con.setRequestProperty("Ugandaemr-Sync-Facility-Id", facilityId);

                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(content);
                writer.flush();
                writer.close();

                // reading the response
                InputStreamReader reader = new InputStreamReader(con.getInputStream());
                StringBuilder buf = new StringBuilder();
                char[] cbuf = new char[2048];
                int num;
                while (-1 != (num = reader.read(cbuf))) {
                    buf.append(cbuf, 0, num);
                }
                String result = buf.toString();

                ObjectMapper mapper = new ObjectMapper();
                Map map = mapper.readValue(result, Map.class);
                return map;
            }
        } catch (Throwable t) {
            t.printStackTrace(System.out);
        }
        return null;
    }

    public Map sendPostBy(String url, String data) throws Exception {
        String contentTypeJSON = FingerPrintConstant.NORMAL_CONTENT_TYPE;

        Commons commons = new Commons();

        String serverIP = commons.getGlobalProperty(FingerPrintConstant.CONNECTION_SERVER_IP_GLOBALPROPERTY);
        String facilityId = commons.getGlobalProperty(FingerPrintConstant.CONNECTION_SERVER_IP_GLOBALPROPERTY);

        String facilityURL = FingerPrintConstant.CONNECTION_PROTOCOL + serverIP + "/" + url;

        return sendPostBy(contentTypeJSON, data, facilityId, facilityURL);
    }

}
