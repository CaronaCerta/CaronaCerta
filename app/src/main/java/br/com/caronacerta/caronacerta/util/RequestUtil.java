package br.com.caronacerta.caronacerta.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class RequestUtil {
    private static final String SERVICE_URL = "http://caronacerta-rodrigoeg.rhcloud.com/";

    public static JSONObject postData(String service, List<NameValuePair> nameValuePairs) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(SERVICE_URL + service);

        try {
            // Add your data
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,  "UTF-8"));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            Reader in = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            char[] buf = new char[1000];
            int l = 0;
            while (l >= 0) {
                builder.append(buf, 0, l);
                l = in.read(buf);
            }
            JSONObject jsonObject = new JSONObject(builder.toString());

            return jsonObject;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
