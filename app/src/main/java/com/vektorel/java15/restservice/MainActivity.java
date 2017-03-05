package com.vektorel.java15.restservice;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button);

        tv = (TextView) findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Service ser = new Service();

                ser.execute("http://10.0.6.100:8080/Person/rest/person/1");
            }
        });
    }


    private class Service extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                connection.connect();

                int res = connection.getResponseCode();

                if(res ==HttpURLConnection.HTTP_OK){

                    BufferedReader buf = new BufferedReader(
                            new InputStreamReader
                                    (connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();

                    String satir;

                    while ((satir = buf.readLine()) != null){
                        sb.append(satir + "\n");
                    }

                    return sb.toString();

                }

                return null;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject obj =null;
            try {
                obj = new JSONObject(s);
                tv.setText(obj.get("1").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
