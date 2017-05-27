package com.pdm.ucanet;

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

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private TextView message;
    private Button login;


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

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < 1; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            if (params[0].equals("admin") && params[1].equals("123")){
                return "Loggin in";

            }else{
                return "Invalid data";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.output);
            //txt.setText("Executed"); // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you

            message = (TextView) findViewById(R.id.messageText);

            if (result.equals("Loggin in")){
                message.setText(result);
                try {
                    Thread.sleep(1000);
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
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }


}
