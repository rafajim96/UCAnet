package com.pdm.ucanet.resourceManagers;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by frank101m on 5/16/17.
 */

public class InformationAdapter {

    private final String loginFile = "https://mapacheproject.xyz/UCAnet/code/login.php";
    private final int timeout = 5000;


    public boolean login(String name, String pass) throws IOException {
        boolean logged = false;
        URL url = new URL(loginFile);
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("uName", name);


        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(timeout);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;)
            sb.append((char)c);
        String response = sb.toString();

        try{
            JSONObject obj1 = new JSONObject(response);
            JSONObject obj = obj1.getJSONObject("result");
            if(name.equals(obj.getString("uName"))){

                logged = pass.equals(obj.get("password"));
            }
            else{
            }
        }catch (JSONException e){

        }

        return logged;
    }


}
