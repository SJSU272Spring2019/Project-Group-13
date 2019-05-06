package com.calendared;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class EventListActivity extends Activity{

    CustomAdapter adapter;
    ListView list;
    ArrayList<ListModel> anyList;
    static Context context;
    boolean doing = true;
    String[] popValues;
    String ltn,typ,sbtyp,altp,alsn,res,mac;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_eventlist);

        anyList = new ArrayList<ListModel>();
        context = this;

        GetEventList getEventList = new GetEventList();
        getEventList.execute();


    }

    public void onItemClick(String name,String image,String desc,String dept){
//		 Intent in = new Intent(NotifActivity.this,
//             DetailActivity.class);
//     // sending pid to next activity
//     in.putExtra(CommonUtilities.TAG_NAME, name);
//     in.putExtra(CommonUtilities.TAG_IMG, image);
//     in.putExtra(CommonUtilities.TAG_DESC, desc);
//     in.putExtra(CommonUtilities.TAG_DEPT, dept);
//     in.putExtra(TAG_RTG, rating);
//     in.putExtra(TAG_STR, seater);
//     in.putExtra(TAG_AC, ac);
//     in.putExtra(TAG_LTN, location);

        // starting new activity and expecting some response back
        //    startActivityForResult(in, 100);
    }

    @Override
    public void onBackPressed() {
        /*Log.d("CDA", "onBackPressed Called");
        Intent in = new Intent(NotifActivity.this,
                MainActivity.class);
        startActivity(in);*/
    }

    class GetEventList extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog = new ProgressDialog(context);
        JSONObject response;

        public GetEventList(){

            dialog.setMessage("Fetching Events");
            dialog.show();
        }


        @Override
        protected Boolean doInBackground(String... urls) {


            TrustManager[] trustManager = new TrustManager[] {new TrustEverythingTrustManager()};

            // Let us create the factory where we can set some parameters for the connection
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustManager, new java.security.SecureRandom());
            } catch (NoSuchAlgorithmException e) {
                // do nothing
            }catch (KeyManagementException e) {
                // do nothing
            }

            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            try {

                /*URL url = new URL("https://"+CommonUtilities.server+"/api/3.0/scripts/js/execute/facilitiesView?auth="+CommonUtilities.Auth);

                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

                //  con.setSSLSocketFactory(context.getSocketFactory());
                con.setHostnameVerifier(new VerifyEverythingHostnameVerifier());
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Desc", "application/json");
                con.setRequestProperty("Accept", "application/json");

                //     con.setRequestProperty("Cookie", "auth="+Common.Auth);

                //    con.setRequestProperty("auth", Common.Auth);

                //  String authString = "auth:"+Common.Auth;

                //    String authString = "admin:admin";
                //        JSONObject creds = new JSONObject();
                //        creds.put("auth",Common.Auth);

                //   String base64Auth = Base64.encodeToString(authString.getBytes(), Base64.DEFAULT);
                //      con.setRequestProperty("Authorization", creds.toString());

	     /*           Authenticator.setDefault(new Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication("admin","admin".toCharArray());
	                    }
	                });
	*/
                //con.setRequestMethod("POST");

                JSONArray apis_list = new JSONArray();
                apis_list.put("getUserAlerts");

                JSONObject parameter = new JSONObject();
                parameter.put("q", apis_list.toString());
                //       parameter.put("auth", Common.Auth);

                /*
                con.connect();

                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(parameter.toString());
                wr.flush();
                InputStream input = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                Log.d("doInBackground(Resp)", result.toString());
                response = new JSONObject(result.toString());*/
                try {
                    JSONObject events = new JSONObject();
                    JSONArray eventsArray = new JSONArray();
                    JSONObject event1 = new JSONObject();
                    JSONObject event2 = new JSONObject();
                    try {
                        event1.put("title", "Birthday");
                        event1.put("desc", "Friend's brithday");
                        event1.put("date", "04/30/2019");
                        event1.put("stime", "18:00");
                        event1.put("etime", "20:00");

                        event2.put("title", "Tech Talk");
                        event2.put("desc", "Virtual Reality");
                        event2.put("date", "04/26/2019");
                        event2.put("stime", "12:00");
                        event2.put("etime", "14:00");

                        eventsArray.put(event1);
                        eventsArray.put(event2);

                        events.put("Events", eventsArray);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //results = response.getJSONObject("result");
                    CommonUtilities.notifs = events.getJSONArray("Events");
                    doing = false;
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                /*switch (con.getResponseCode()) {
                    case HttpURLConnection.HTTP_OK:
                        Log.d("HTTP Response", "OK");
                        con.disconnect();
                        return true;
                    case HttpURLConnection.HTTP_UNAUTHORIZED:
                        Log.d("HTTP Response", "Unauthorized");
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                        return false;
                    case HttpURLConnection.HTTP_INTERNAL_ERROR:
                        Log.d("HTTP Response", "Internal Error ");
                        return false;
                    default:
                        Log.d("HTTP Response", "Unknown Error ");
                        return false; // abort
                }*/

            } catch (JSONException e){
                e.printStackTrace();
                return false;
            }

            return true;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Boolean result) {

            populate_list();
            if(dialog.isShowing())
                dialog.cancel();

        }
    }

    public void populate_list() {

        try {
            boolean empty = false;
            int i;
            JSONObject c;
            JSONArray obj;
            obj = CommonUtilities.notifs;

            Log.d("Notif", "JSON processed" + CommonUtilities.notifs.toString());
            Log.d("Notif", "JSON processed" + obj.length());
            //Log.d("Notif", CommonUtilities.notifs.toString());

            // looping through All Products
            if (!empty)
                for (i = 0; i < obj.length(); i++) {
                    Log.d("Events", "Scanning");

                    c = CommonUtilities.notifs.getJSONObject(i);

                    //Log.d("Notif value of c " + i, c.toString());
                    //if(c.getString("fvAttr."+popValues[j]+".alertState").equals("true"))	{


                    ltn = c.getString("title");
                    //int pos = ltn.indexOf("/");
                    //int pos1 = ltn.indexOf("/",pos+1);
                    //ltn = (String) ltn.subSequence(ltn.indexOf("/",pos), ltn.indexOf("/",pos1+1));

                    typ = c.getString("desc");
                    sbtyp = c.getString("date");
                    altp = c.getString("stime");
                    alsn = c.getString("etime");
                    res = null;
                                /*if((c.getString("fvAttr."+popValues[j]+".resetValue")).equals(null))
                                {
                                    res = null;
                                    Log.d("Notif","Res is nulllllllllllllllllllllllllll");
                                }
                                else {
                                    res = c.getString("fvAttr."+popValues[j]+".resetValue");
                                    if(!(res.equals("null"))) {
                                        Log.d("Notif", i + " I am a " + popValues[j] + " and I am " + res);
                                        res = "\""+popValues[j]+"\":\""+res+"\"";
                                        Log.d("Notif", i +" I am a " + popValues[j] + " and I am " + res);
                                    }
                                    else {
                                        Log.d("Notif", i + " I am a " + popValues[j] + " and I am " + res);
                                        res = null;
                                        Log.d("Notif", i + " I am a " + popValues[j] + " and I am " + res);
                                    }
                                }*/
                                //mac = c.getString("mac");

                    /****** Function to set data in ArrayList *************/

                    final ListModel sched = new ListModel();

                    /******* Firstly take data in model object ******/
                    sched.setTitle(ltn);
                    sched.setDesc(typ);
                    sched.setDate(sbtyp);
                    sched.setStartTime(altp);
                    sched.setEndTime(alsn);


                    Log.d("Event"+i, ltn + " " + typ + " " + sbtyp + " " + altp + " " + res);

                    /******** Take Model Object in ArrayList **********/
                    anyList.add( sched );
                           // }
                }


        } catch (JSONException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }

        Resources res =getResources();
        Log.d("Hello", "I'm here scanning entries : " + anyList.size());
        list = ( ListView )findViewById( android.R.id.list );  // List defined in XML ( See Below )

        for(int i = 0; i < anyList.size(); i++) {
            Log.d("Notif", anyList.get(i).getTitle() + " " + anyList.get(i).getDesc());
        }
        /**************** Create Custom Adapter *********/
        adapter=new CustomAdapter( EventListActivity.this, anyList, res );
        list.setAdapter( adapter );

    }

}
