package br.com.caronacerta.caronacerta.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class RequestUtil {
    private static final String SERVICE_URL = "http://caronacerta-rodrigoeg.rhcloud.com/";

    public static JSONObject postData(String service, String authToken) {
        return postData(service, new ArrayList<NameValuePair>(), authToken);
    }

    public static JSONObject postData(String service, List<NameValuePair> nameValuePairs) {
        return postData(service, nameValuePairs, "");
    }

    public static JSONObject postData(String service, List<NameValuePair> nameValuePairs, String authToken) {
        HttpPost httpPost = new HttpPost(SERVICE_URL + service);

        try {
            // Add your data
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        return doRequest(httpPost, authToken);
    }

    public static JSONObject getData(String service, String authToken) {
        return getData(service, new ArrayList<NameValuePair>(), authToken);
    }

    public static JSONObject getData(String service, List<NameValuePair> nameValuePairs) {
        return getData(service, nameValuePairs, "");
    }

    public static JSONObject getData(String service, List<NameValuePair> nameValuePairs, String authToken) {
        // Add your data
        String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");
        HttpGet httpGet = new HttpGet(SERVICE_URL + service + "?" + paramString);

        return doRequest(httpGet, authToken);
    }

    public static JSONObject putData(String service, String authToken) {
        return putData(service, new ArrayList<NameValuePair>(), authToken);
    }

    public static JSONObject putData(String service, List<NameValuePair> nameValuePairs) {
        return putData(service, nameValuePairs, "");
    }

    public static JSONObject putData(String service, List<NameValuePair> nameValuePairs, String authToken) {
        HttpPut httpPut = new HttpPut(SERVICE_URL + service);

        try {
            // Add your data
            httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        return doRequest(httpPut, authToken);
    }

    public static JSONObject deleteData(String service, String authToken) {
        return deleteData(service, new ArrayList<NameValuePair>(), authToken);
    }

    public static JSONObject deleteData(String service, List<NameValuePair> nameValuePairs) {
        return deleteData(service, nameValuePairs, null);
    }

    public static JSONObject deleteData(String service, List<NameValuePair> nameValuePairs, String authToken) {
        // Add your data
        String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");
        HttpDelete httpDelete = new HttpDelete(SERVICE_URL + service + "?" + paramString);

        return doRequest(httpDelete, authToken);
    }

    private static JSONObject doRequest(HttpRequestBase httpRequestBase, String authToken) {
        // Create a new HttpClient
        HttpClient httpclient = new DefaultHttpClient();
        try {
            httpRequestBase.setHeader("X-Auth-Token", authToken);

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httpRequestBase);

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
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
