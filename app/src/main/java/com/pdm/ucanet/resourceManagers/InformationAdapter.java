package com.pdm.ucanet.resourceManagers;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.pdm.ucanet.abstractEntities.Course;
import com.pdm.ucanet.abstractEntities.Post;
import com.pdm.ucanet.abstractEntities.Thread;
import com.pdm.ucanet.concreteEntities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
    private final String threadFile = "https://mapacheproject.xyz/UCAnet/code/loadThreads.php";
    private final String postFile = "https://mapacheproject.xyz/UCAnet/code/loadPosts.php";
    private final String inserPostFile = "https://mapacheproject.xyz/UCAnet/code/insertPost.php";
    private final int timeout = 5000;

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
           //JSONObject objN = obj1.getJSONObject("courseNumber");
           //change what JSON returns (remove number)


           Log.d("log1", obj1.getString("userInfo"));

           if(name.equals(objUserInfo.getString("userId"))){

               Log.d("login", obj1.getString("userInfo"));

               logged = pass.equals(objUserInfo.get("password"));
               if(logged){
                   ArrayList<Course> courses = new ArrayList<>();
                   JSONArray c = obj1.getJSONArray("courses");
                   for(int i = 0; i < c.length(); i++){
                       //courses.add(new Course(i, c.getJSONObject(i).getString("courses")));
                       courses.add(new Course(c.getJSONObject(i).getInt("courseId"), c.getJSONObject(i).getString("courses")));
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

   public ArrayList<Thread> loadThreads(int courseId) throws  IOException{
       URL url = new URL(threadFile);
       Map<String,Object> params = new LinkedHashMap<>();
       params.put("courseId", courseId);


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
        Log.d("threadsGetter", response);
       try{
           JSONArray threadInfoArray = new JSONArray(response);
           ArrayList<Thread> threads = new ArrayList<>();
           for(int i = 0; i< threadInfoArray.length(); i++){
               threads.add(new Thread(threadInfoArray.getJSONObject(i).getInt("threadId"), threadInfoArray.getJSONObject(i).getString("title")));
           }
           return threads;

       }catch(JSONException e){
           Log.d("threadsGetter", e.getMessage());
       }
        return new ArrayList<>();
   }

   public ArrayList<Post> loadPosts(int threadId) throws IOException{
       URL url = new URL(postFile);
       Map<String,Object> params = new LinkedHashMap<>();
       params.put("threadId", threadId);


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
       Log.d("postsLoad", response);
       try{
           JSONArray postsJSONArray = new JSONArray(response);
           ArrayList<Post> posts = new ArrayList<>();
           for(int i = 0; i< postsJSONArray.length(); i++){
               posts.add(new Post(postsJSONArray.getJSONObject(i).getInt("postId"),
                       postsJSONArray.getJSONObject(i).getString("content"),
                       postsJSONArray.getJSONObject(i).getString("creation"),
                       postsJSONArray.getJSONObject(i).getString("userId"),
                       postsJSONArray.getJSONObject(i).getInt("image")==1,
                       postsJSONArray.getJSONObject(i).getString("op")!=null,
                       postsJSONArray.getJSONObject(i).getString("uName"),
                       postsJSONArray.getJSONObject(i).getString("imageName")));
           }

           return posts;

       }catch(JSONException e){
           Log.d("postLoad", e.getMessage());
       }
       return new ArrayList<>();
   }

   public boolean insertPost(int threadId, String content, String userId, int image, String imageName) throws IOException{
       URL url = new URL(inserPostFile);
       Map<String,Object> params = new LinkedHashMap<>();
       params.put("threadId", threadId);
       params.put("content", content);
       params.put("userId", userId);
       params.put("image", image);
       params.put("imageName", imageName);
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
       Log.d("postsLoad", response);
       try{
           JSONObject obj = new JSONObject(response);
           if(obj.getInt("status")==1){
               return true;
           }
       }
       catch (Exception e){
           Log.d("errorJsonPost", e.getMessage().toString());
       }

       return false;
   }


}


