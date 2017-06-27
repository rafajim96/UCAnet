package com.pdm.ucanet.fragmentClasses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.pdm.ucanet.R;
import com.pdm.ucanet.abstractEntities.Course;
import com.pdm.ucanet.concreteEntities.User;
import com.pdm.ucanet.resourceManagers.CourseCardLayoutAdapter;
import com.pdm.ucanet.resourceManagers.InformationAdapter;
import com.pdm.ucanet.resourceManagers.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Crash on 09/06/2017.
 */

public class ThreadFragment extends Fragment {
    private Spinner coursesSpinner;
    private EditText titleEdit;
    private EditText contentEdit;
    private ImageView image;
    private Button uploadImage;
    private Button saveThread;
    private ArrayAdapter<String> dataAdapter;
    InformationAdapter info = new InformationAdapter();
    private SessionManager sessionManager;
    private User loggedUser;
    String title, content;
    int courseId;
    final static int SELECT_PICTURE = 1;
    ImageView i1, i2;
    String imageC, imageN;
    Uri dataImg;
    Bitmap bmap;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_thread, container, false);
        bmap = null;

        coursesSpinner = (Spinner) view.findViewById(R.id.spinnerCourses);
        titleEdit = (EditText) view.findViewById(R.id.editTitle);
        contentEdit = (EditText) view.findViewById(R.id.editContent);
        uploadImage = (Button) view.findViewById(R.id.buttonUploadImage);
        saveThread = (Button) view.findViewById(R.id.buttonSaveThread);
        i1 = (ImageView) view.findViewById(R.id.imageThread);
        i2 = (ImageView) view.findViewById(R.id.imageThreadMain);
        sessionManager = new SessionManager(getContext());
        loggedUser = sessionManager.loadSession();

        dataAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                loggedUser.getCoursesNames());

        coursesSpinner.setAdapter(dataAdapter);

        //SETTING LISTENERS FOR BUTTONS
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UPLOADING IMAGE

                chooseImage();

            }
        });

        saveThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(titleEdit.getText().toString().equals("") || contentEdit.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Por favor rellene los campos", Toast.LENGTH_SHORT).show();
                }else{
                    //SAVE THREAD
                    title = titleEdit.getText().toString();
                    content = contentEdit.getText().toString();
                    for(Course c : loggedUser.getCourses()){
                        if(c.getCourseName().equals(coursesSpinner.getSelectedItem().toString())){
                            courseId = c.getIdCourse();
                            break;
                        }
                    }
                    try {
                        new insertThread().execute("go");
                        Toast.makeText(getContext(), "Post insertado correctamente", Toast.LENGTH_SHORT).show();

                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error al insertar post", Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });





        return view;
    }








    void chooseImage(){
        Intent i = new Intent();
        i2.setVisibility(View.VISIBLE);
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == SELECT_PICTURE){



                dataImg = data.getData();
                if(dataImg != null){
                    i1.setImageURI(dataImg);
                    i1.setDrawingCacheEnabled(true);

                   i2.setImageURI(dataImg);
                   i2.setDrawingCacheEnabled(true);

                    bmap = i2.getDrawingCache();
                    imageN = loggedUser.getUsername() + String.valueOf(System.currentTimeMillis() / 1000L) + ".png";



                }
            }
        }
        i2.setVisibility(View.INVISIBLE);
    }



    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }




    private class insertThread extends AsyncTask<String, String, String> {
        private ProgressDialog progDailog;



        @Override
        protected String doInBackground(String... params) {
            //CHECK IF THE DATA OF THE LOGIN IS CORRECT
            try {

                if (bmap != null) {


                    imageC = encodeToBase64(bmap, Bitmap.CompressFormat.PNG, 100);
                    Log.d("imageEncode", imageC);
                } else {
                    Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Log.d("error imagen post", e.getMessage().toString());

            }

            try {
                //de momento imagen es cero y null
                if(imageN == null || imageN.equals("")) {

                    info.insertThread(title, courseId, content, loggedUser.getUsername(), 0, null);
                }else{
                    info.insertThread(title, courseId, content, loggedUser.getUsername(), 1, imageN);
                    info.insertImage(imageN, imageC);

                }
                return "did";
            }catch(IOException e){

            }
            return "didnt";
        }

        @Override
        protected void onPostExecute(String result) {
            progDailog.dismiss();
            i1.setImageResource(0);
            i2.setImageResource(0);
            imageC=null;
            imageN=null;
            title=null;
            content=null;
            titleEdit.setText("");
            contentEdit.setText("");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(getContext());
            progDailog.setMessage("Insertando post...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {}

    }



}
