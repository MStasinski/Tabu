package com.michal_stasinski.tabu.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by win8 on 27.06.2017.
 */

public class URLProcessing {
    public static String GetUrl(String url) throws Exception {
        URL serverAddress = null;
        HttpURLConnection connection = null;
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;

        try {
            serverAddress = new URL(url);
            // set up out communications stuff
            connection = null;
            // Set up the initial connection
            connection = (HttpURLConnection) serverAddress.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.connect();

            // read the result from the server
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();

            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            throw e;
            // Swallow
        } finally {
            // close the connection, set all objects
            // to null
            connection.disconnect();
            rd = null;
            sb = null;
            connection = null;
        }
    }
}
