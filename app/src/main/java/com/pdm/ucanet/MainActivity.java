package com.pdm.ucanet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pdm.ucanet.resourceManagers.InformationAdapter;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private TextView message;
    private Button login;
    private InformationAdapter inf = new InformationAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        username = (EditText) findViewById(R.id.editTextUser);
        password = (EditText) findViewById(R.id.editTextPassword);
        message = (TextView) findViewById(R.id.messageText);
        login = (Button) findViewById(R.id.buttonLogin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SENDING LOGIN VALUES TO ASYNCTASK TO BE VERIFIED
                message.setText("");
                new LongOperation().execute(username.getText().toString(), password.getText().toString());
            }
        });
    }

    private class LongOperation extends AsyncTask<String, String, String> {
        ProgressDialog progDailog = new ProgressDialog(MainActivity.this);

        @Override
        protected String doInBackground(String... params) {
            //CHECK IF THE DATA OF THE LOGIN IS CORRECT
            for (int i = 0; i < 1; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            try {
                if(inf.login(params[0], params[1])){
                    return "Loggin in";
                }
                else{
                    return "Invalid data";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Invalid data";
        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.output);
            //txt.setText("Executed"); // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            message = (TextView) findViewById(R.id.messageText);
            super.onPostExecute(result);
            progDailog.dismiss();
            if (result.equals("Loggin in")){
                message.setText(result);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }

                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                finish();  //Kill the activity from which you will go to next activity
                startActivity(i);
            }else{

                message.setText(result);
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog.setMessage("Validating...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {}
    }


}
