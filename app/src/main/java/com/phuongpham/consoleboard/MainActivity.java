package com.phuongpham.consoleboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ProgressDialog pDialog;
    private static final String SEVRER_ADDRESS = "http://localhost/db_upload.php";
   // JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton left = (ImageButton) findViewById(R.id.left);
        ImageButton right = (ImageButton) findViewById(R.id.right);

        left.setOnClickListener(this);
        right.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left:
                new UploadMyCommand("left").execute();
                Toast.makeText(MainActivity.this,
                        "left is clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right:
                new UploadMyCommand("right").execute();
                Toast.makeText(MainActivity.this,
                        "right is clicked!", Toast.LENGTH_SHORT).show();
                break;
        }

    }
/*
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.phuongpham.consoleboard/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.phuongpham.consoleboard/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
*/
    private class UploadMyCommand extends AsyncTask<Void, Void, Void> {

        String move;

        public UploadMyCommand (String move){
            this.move = move;
        }

        @Override protected void onPreExecute() {
            super.onPreExecute();
           /* pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Sending part to the database..."); //Set the message for the loading window
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show(); //Place the loading message on the screen*/
            Toast.makeText(MainActivity.this,
                    "button is clicked!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("Description",move));

            //JSONObject json = jsonParser.makeHttpRequest("RaspberryPi_IP/db_create.php", "POST", dataToSend);

          //  HttpParams httpRequestParams = getHttpRequestParams();
            //Toast.makeText(getApplicationContext(), "Connected to server", Toast.LENGTH_SHORT).show();

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://192.168.1.92/db_upload.php");

            try {

              //  post.setEntity(new UrlEncodedFormEntity(dataToSend));
               // httpclient.execute(post);

                post.setEntity(new UrlEncodedFormEntity(dataToSend, HTTP.UTF_8));

                HttpResponse response = httpclient.execute(post);
                finish();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "DB UPLOADED", Toast.LENGTH_SHORT).show();
               // pDialog.dismiss(); // Close the loading window when ready
        }

 /*   private HttpParams getHttpRequestParams () {
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000*30);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000*30);
        return httpRequestParams;
    }*/
    }

}