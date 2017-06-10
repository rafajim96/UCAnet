package com.pdm.ucanet.resourceManagers;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.pdm.ucanet.abstractEntities.Course;
import com.pdm.ucanet.concreteEntities.User;

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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by frank101m on 5/16/17.
 */

public class InformationAdapter {

    private final String loginFile = "https://mapacheproject.xyz/UCAnet/code/userLogin.php";
    private final int timeout = 5000;


   /* public boolean login(String name, String pass) throws IOException {
        boolean logged = false;
        URL url = new URL(loginFile);
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("user", name);


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
        String response = sb.toString()+"test";

        try{
            JSONObject obj1 = new JSONObject(response);
            JSONObject obj = obj1.getJSONObject("result");
            Log.d("log1", obj1.getString("result"));

            if(name.equals(obj.getString("userId"))){


                logged = pass.equals(obj.get("password"));
            }
            else{
            }
        }catch (JSONException e){
            Log.d("log1", e.getMessage());
        }

        return logged;
    }*/
   public User login(String name, String pass) throws IOException {
       boolean logged = false;
       URL url = new URL(loginFile);
       Map<String,Object> params = new LinkedHashMap<>();
       params.put("user", name);


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
           JSONObject objUserInfo = obj1.getJSONObject("userInfo");
           JSONObject objFaculty = obj1.getJSONObject("facultyInfo");
           JSONObject objCareer = obj1.getJSONObject("careerInfo");
           JSONObject objN = obj1.getJSONObject("courseNumber");


           Log.d("log1", obj1.getString("userInfo"));

           if(name.equals(objUserInfo.getString("userId"))){

               Log.d("login", obj1.getString("userInfo"));

               logged = pass.equals(objUserInfo.get("password"));
               if(logged){
                    ArrayList<Course> courses = new ArrayList<>();
                   /*switch (objN.getInt("courseNumber")){
                       case 1:
                           courses.add(new Course(0, obj1.getJSONObject()))
                           break;
                       case 2:
                           break;
                       case 3:
                           break;
                       case 4:
                           break;
                       case 5:
                           break;
                       case 6:
                           break;
                       case 7:
                           break;
                   }*/

                   JSONArray c = obj1.getJSONArray("courses");
                   for(int i = 0; i < c.length(); i++){
                       courses.add(new Course(i, c.getJSONObject(i).getString("courses")));
                   }
                   User u = new User(objUserInfo.getString("uName"), objCareer.getString("career"), objFaculty.getString("faculty"), objUserInfo.getString("userId"), courses, (objUserInfo.getString("photo")!=null));
                   return u;
               }
           }
           else{
           }
       }catch (JSONException e){
           Log.d("log1", e.getMessage());
       }

       return null;
   }

}


