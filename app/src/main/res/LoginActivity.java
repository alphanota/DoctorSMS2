package com.parlanto.sms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    /**
     * Hold references to these views
     */

    EditText usernameField;
    EditText passwordField;
    EditText urlField;
    ProgressDialog progressDialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = (EditText) findViewById(R.id.username_field);
        passwordField = (EditText) findViewById(R.id.password_field);
        urlField = (EditText) findViewById(R.id.url_field);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /**
     * TODO: Avoid handling password as string. use char[] instead
     **/
    public void onLogin(View v) {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String url = urlField.getText().toString();
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Attempting Login...");
        progressDialog.show();

        LoginTask loginTask = new LoginTask();
        loginTask.execute(username, password, url);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.south.doctorsms/http/host/path")
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
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.south.doctorsms/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    private class LoginTask extends AsyncTask<String, Integer, Long> {
        
        protected Long doInBackground(String... fields) {

            long responseCode = -51278;

            String username = fields[0];
            String password = fields[1];
            String urlName = fields[2];
            URL url;
            try {
                url = new URL(urlName);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                //connection.setChunkedStreamingMode(0);

                connection.setRequestMethod("POST");
                // no space in param args
                String urlParameters = "un=" + username + "&pw=" + password;

                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();


                responseCode = connection.getResponseCode();
                BufferedReader in;
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                line = in.readLine();

                responseCode = Long.parseLong(line);

            } catch (MalformedURLException malUrl) {
                malUrl.printStackTrace();
                return 1L;

            } catch (IOException e) {

            } finally {

            }


            return responseCode;
        }


        @Override
        protected void onPostExecute(Long result) {

            if (result > 0) {
               //Intent intent = new Intent(this, MainActivity.class);
                //startActivity(intent);
            }

            if (progressDialog.isShowing()) {
                //progressDialog.hide();
                progressDialog.setMessage(" " + result.toString());
            }

            openUserView();
        }


    }



    private void openUserView(){
        Intent launcherIntent = new Intent(this,MainActivity.class);

        Bundle dataBundle = new Bundle();
        dataBundle.putString("USERNAME","John");

        launcherIntent.putExtras(dataBundle);

        startActivity(launcherIntent);


    }


}
