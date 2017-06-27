package com.pdm.ucanet.fragmentClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pdm.ucanet.R;
import com.pdm.ucanet.concreteEntities.User;
import com.pdm.ucanet.resourceManagers.InformationAdapter;
import com.pdm.ucanet.resourceManagers.SessionManager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
    private User loggedUser;
    private SessionManager sessionManager;
    ImageView img;
    final static int SELECT_PICTURE = 1;
    Uri dataImg;
    Bitmap bmap;
    private Button btn;
    String imageN, imageC;
    InformationAdapter info = new InformationAdapter();
    boolean f = false;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        sessionManager = new SessionManager(getContext());
        loggedUser = sessionManager.loadSession();



        bmap = null;

        TextView userText = (TextView) view.findViewById(R.id.nameText);
        userText.setText(loggedUser.getName());
        TextView careerText = (TextView) view.findViewById(R.id.textCareer);
        careerText.setText(loggedUser.getCareer());
        TextView idText = (TextView) view.findViewById(R.id.textId);
        idText.setText(loggedUser.getUsername());
        imageN = null;
        btn = (Button) view.findViewById(R.id.button);







        img = (ImageView) view.findViewById(R.id.imageView);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseImage();

            }
        });




        new GetPic().execute("go");



        return view;
    }


    void chooseImage(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_PICTURE){

                dataImg = data.getData();
                if(dataImg != null){
                    img.setImageURI(dataImg);
                    img.setDrawingCacheEnabled(true);




                    bmap = img.getDrawingCache();

                    imageN = loggedUser.getUsername()+".png";
                    try {
                        new ChangePic().execute("go");
                    }catch (Exception e){
                        Log.d("errorPP", e.getMessage().toString());
                    }

                }
            }
        }
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private class ChangePic extends AsyncTask<String, String, String> {
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

                    info.insertImage(imageN, imageC);



                return "did";
            }catch(IOException e){
                Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();

            }
            return "didnt";
        }

        @Override
        protected void onPostExecute(String result) {
            progDailog.dismiss();


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(getContext());
            progDailog.setMessage("Cambiando imagen...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {}

    }



    private class GetPic extends AsyncTask<String, String, String> {

        boolean c= false;


        @Override
        protected String doInBackground(String... params) {
            //CHECK IF THE DATA OF THE LOGIN IS CORRECT

            try {
                HttpURLConnection.setFollowRedirects(false);
                // note : you may also need
                //        HttpURLConnection.setInstanceFollowRedirects(false)
                HttpURLConnection con =
                        (HttpURLConnection) new URL("https://mapacheproject.xyz/UCAnet/resources/images/" + loggedUser.getUsername() + ".png").openConnection();
                con.setRequestMethod("HEAD");
                if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
                    f=true;
                    return "yes";

                }

            }
            catch (Exception e) {
                c = true;
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {

            if(c){
                Toast.makeText(getContext(), "Error al cargar imagen de perfil", Toast.LENGTH_SHORT).show();

                Log.d("cargarcargar", "no carga");
            }

            if(f){
                Picasso.with(getContext()).load("https://mapacheproject.xyz/UCAnet/resources/images/" + loggedUser.getUsername() + ".png").memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(img);

            }

        }

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected void onProgressUpdate(String... values) {}

    }


}
