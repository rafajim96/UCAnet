package com.pdm.ucanet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.pdm.ucanet.concreteEntities.User;
import com.pdm.ucanet.resourceManagers.InformationAdapter;
import com.pdm.ucanet.resourceManagers.SessionManager;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Crash on 15/06/2017.
 */

public class PostActivity extends AppCompatActivity {
    private String threadName;
    private int threadId;
    private SessionManager sessionManager;
    private User loggedUser;
    private TextView textThreadName, imgName;
    private ImageView imgView;
    private Button savePostButton, loadImageButton;
    private InformationAdapter info = new InformationAdapter();
    private EditText content;
    final static int SELECT_PICTURE = 1;
    private String contentS;
    private String imageC, imageN;
    private Uri dataImg;
    private Bitmap bmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        imageN=null;
        imageC=null;
        threadName = getIntent().getStringExtra("THREAD_NAME");
        threadId = getIntent().getIntExtra("threadId", 0);
        sessionManager = new SessionManager(this);
        loggedUser = sessionManager.loadSession();
        textThreadName = (TextView) findViewById(R.id.textThreadName);
        savePostButton = (Button) findViewById(R.id.buttonSavePost);
        imgView = (ImageView) findViewById(R.id.imageView3);
        textThreadName.setText(threadName);
        imgName = (TextView) findViewById(R.id.textView5);
        loadImageButton = (Button) findViewById(R.id.buttonUploadPostImage);
        bmap = null;

        //SETTING LISTENERS FOR BUTTONS

        savePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = (EditText) findViewById(R.id.editText);
                contentS = content.getText().toString();

                if(content.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Por favor rellene los campos", Toast.LENGTH_SHORT).show();

                }else{
                    try {
                        new insert().execute("go");
                        Toast.makeText(PostActivity.this, "Post insertado correctamente", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(PostActivity.this, "Error al insertar post", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        loadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }});

    }

    void chooseImage(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
        imgView.setVisibility(View.VISIBLE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_PICTURE){
                ImageView img3 = (ImageView) findViewById(R.id.imageView3CP);
                dataImg = data.getData();
                if(dataImg != null){





                    imgView.setImageURI(dataImg);
                    imgView.setDrawingCacheEnabled(true);

                    img3.setImageURI(dataImg);
                    img3.setDrawingCacheEnabled(true);

                    bmap = img3.getDrawingCache();
                    String name = String.valueOf(System.currentTimeMillis()/1000L);
                    imgName.setText(loggedUser.getUsername() + name + ".png");
                    imageN = loggedUser.getUsername() + name + ".png";



                }
            }
        }
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat,
                                        int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private class insert extends AsyncTask<String, String, String> {
        private ProgressDialog progDailog;



        @Override
        protected String doInBackground(String... params) {
            //CHECK IF THE DATA OF THE LOGIN IS CORRECT
            try {
                if (bmap != null) {


                    imageC = encodeToBase64(bmap, Bitmap.CompressFormat.PNG, 100);
                    Log.d("imageEncode", imageC);
                } else {
                    Toast.makeText(PostActivity.this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Log.d("error imagen post", e.getMessage().toString());
            }
            try {
                //de momento imagen es cero y null
                if(imageN == null || imageN.equals("")){
                    info.insertPost(threadId, contentS, loggedUser.getUsername(),
                            0, null);

                }else{
                    info.insertPost(threadId, contentS, loggedUser.getUsername(),
                            1, imageN);
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
            finish();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(PostActivity.this);
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


