package com.calendared;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.support.constraint.Constraints.TAG;
import static com.calendared.CommonUtilities.android_client_id;

public class SigninActivity extends AppCompatActivity {

    private SignInButton googleSignInButton;
    private GoogleSignInClient googleSignInClient;
    public static String TAG_ACTIVITY = "Signin Activity";
    public String authCode = "4/QgH7U8TgNXkwBQ-PhQbgCRvu";
    public  static JSONObject jsonObject = new JSONObject();
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        SigninActivity.context = getApplicationContext();

        googleSignInButton = findViewById(R.id.sign_in_button);
        // .requestIdToken(getResources().getString(R.string.android_client_id))
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope("https://www.googleapis.com/auth/gmail.readonly"))
                .requestServerAuthCode(android_client_id)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            Log.d(TAG_ACTIVITY, "User has already signed in");
            HTTPAsyncTask sendAuthCode = new HTTPAsyncTask();
            sendAuthCode.execute();
            Log.d(TAG_ACTIVITY, "Done with asynctask");
            //Intent intent = new Intent(SigninActivity.this, EventListActivity.class);
            //startActivity(intent);
        }

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG_ACTIVITY, "Clicked button, Signing in");
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
                Log.d(TAG_ACTIVITY, "Done with onClick");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG_ACTIVITY, "Result is " + resultCode);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        authCode = account.getServerAuthCode();

                        if(authCode == null) {
                            Log.d(TAG_ACTIVITY, "Token is null");
                        }
                        Log.d(TAG_ACTIVITY, "Token is " + authCode);

                        try {
                            jsonObject.put("authcode", authCode);
                            Log.d(TAG_ACTIVITY, "Setting JSON content " + jsonObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        HTTPAsyncTask sendAuthCode = new HTTPAsyncTask();
                        sendAuthCode.execute();

                        Intent intent = new Intent(SigninActivity.this, EventListActivity.class);
                        startActivity(intent);

                    /*
                     Write to the logic send this id token to server using HTTPS
                     */
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
    }

    private static void setPostRequestContent(HttpURLConnection conn) {

        Log.d(TAG_ACTIVITY, "Setting JSON content " + jsonObject.toString());
        OutputStream os = null;
        try {
            os = conn.getOutputStream();
            Log.d(TAG_ACTIVITY, "Setting JSON content 1");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            Log.d(TAG_ACTIVITY, "Setting JSON content 2");
            writer.write(jsonObject.toString().replaceAll("\\\\",""));
            Log.i(TAG_ACTIVITY, jsonObject.toString());
            writer.flush();
            writer.close();
            os.close();
        } catch (IOException e) {
            Log.d(TAG_ACTIVITY, "write error");
            e.printStackTrace();
        }
    }


    private static String HttpPost(String myUrl) throws IOException, JSONException {
        String result = "";

        URL url = new URL(myUrl);

        // 1. create HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        Log.d(TAG_ACTIVITY, "Set request parameters");

        // 3. add JSON content to POST request body
        setPostRequestContent(conn);
        Log.d(TAG_ACTIVITY, "Set JSON content");

        // 4. make POST request to the given URL
        conn.connect();
        Log.d(TAG_ACTIVITY, "Connect");

        Log.d(TAG_ACTIVITY, conn.getResponseMessage());

        // 5. return response message
        return conn.getResponseMessage()+ "";

    }

    private String convertInputStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public class HTTPAsyncTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = new ProgressDialog(SigninActivity.this);

        public HTTPAsyncTask(){

            dialog.setMessage("Scanning your emails ...");
            dialog.show();
            Log.d(TAG_ACTIVITY, "Sending data to " );
        }
        @Override
        protected String doInBackground(String... urls) {
            Log.d(TAG_ACTIVITY, "Inside do in background");
            // params comes from the execute() call: params[0] is the url.
            //try {
            Log.d(TAG_ACTIVITY, "Inside try");
            try {
                    Log.d(TAG_ACTIVITY, "Inside do in background");
                    return HttpPost("http://ec2-54-214-226-113.us-west-2.compute.amazonaws.com:5000/");
            } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";

            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
                /*Log.d(TAG_ACTIVITY, "Setting url");
                URL url = new URL("http://ec2-34-221-77-242.us-west-2.compute.amazonaws.com:5000/");
                Log.d(TAG_ACTIVITY, "Sending data to " + url);

                // Create the urlConnection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                urlConnection.setRequestProperty("Content-Type", "application/json");

                urlConnection.setRequestMethod("POST");


                // OPTIONAL - Sets an authorization header
                //urlConnection.setRequestProperty("Authorization", "someAuthString");

                // Send the post body
                //if (this.postData != null) {
                Log.d(TAG_ACTIVITY, "Sending data to " + url);
                OutputStream os = urlConnection.getOutputStream();
                Log.d(TAG_ACTIVITY, "Writing output stream 0");
                OutputStreamWriter writer = new OutputStreamWriter(os);
                Log.d(TAG_ACTIVITY, "Writing output stream");
                writer.write("Hello");
                Log.d(TAG_ACTIVITY, "Writing output stream 1");
                writer.flush();
                //}

                int statusCode = urlConnection.getResponseCode();

                if (statusCode == 200) {

                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    String response = convertInputStreamToString(inputStream);

                    // From here you can convert the string to JSON with whatever JSON parser you like to use
                    // After converting the string to JSON, I call my custom callback. You can follow this process too, or you can implement the onPostExecute(Result) method
                } else {
                    // Status code is not 200
                    // Do something to handle the error
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG_ACTIVITY, e.getMessage());
            }
            return null;*/
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG_ACTIVITY, "Request done");
            dialog.cancel();

            Intent intent = new Intent(SigninActivity.this, EventListActivity.class);
            startActivity(intent);
        }
    }
}
