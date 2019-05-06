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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.GregorianCalendar;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class CustomAdapter extends BaseAdapter implements OnClickListener {
          
         /*********** Declare Used Variables *********/
         private Activity activity;
         private ArrayList data;
         private static LayoutInflater inflater=null;
         public Resources res;
         ListModel tempValues=null;
         int i=0;
         String mqtt;
          
         /*************  CustomAdapter Constructor *****************/
         public CustomAdapter(Activity a, ArrayList d,Resources resTitleal) {
              
                /********** Take passed values **********/
                 activity = a;
                 data=d;
                 res = resTitleal;
              
                 /***********  Layout inflator to call external xml layout () ***********/
                  inflater = ( LayoutInflater )activity.
                                              getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              
         }
      
         /******** What is the size of Passed Arraylist Size ************/
         public int getCount() {
              
             if(data.size()<=0)
                 return 1;
             return data.size();
         }
      
         public Object getItem(int position) {
             return position;
         }
      
         public long getItemId(int position) {
             return position;
         }
          
         /********* Create a holder Class to contain inflated xml file elements *********/
         public static class ViewHolder{
              
             public TextView text;
             public TextView text1;
             public TextView textWide;
			public TextView text2;
			public TextView text3;
			public TextView text4;
			public ImageView img;
			public Button btn;
			public RelativeLayout rel;
      
         }
      
         /****** Depends upon data size called for each row , Create each ListView row *****/
         public View getView(int position, View convertView, ViewGroup parent) {
              
             View vi = convertView;
             ViewHolder holder;
              
             if(convertView==null){
                  
                 /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
                 vi = inflater.inflate(R.layout.list_item, null);
                  
                 /****** View Holder Object to contain tabitem.xml file elements ******/
 
                 holder = new ViewHolder();
                 holder.text = (TextView) vi.findViewById(R.id.title);
                 holder.text1=(TextView)vi.findViewById(R.id.desc);
                 holder.text2=(TextView)vi.findViewById(R.id.date);
                 holder.text3=(TextView)vi.findViewById(R.id.stime);
                 holder.text4=(TextView)vi.findViewById(R.id.etime);
                 holder.img=(ImageView)vi.findViewById(R.id.image);                 
                 holder.btn=(Button)vi.findViewById(R.id.add_button);
                 holder.rel=(RelativeLayout)vi.findViewById(R.id.rel);
                 
                  
                /************  Set holder with LayoutInflater ************/
                 vi.setTag( holder );
             }
             else 
                 holder=(ViewHolder)vi.getTag();
              
             if(data.size()<=0)
             {
                 holder.text.setText("No Data");
                  
             }
             else
             {
                 /***** Get each Model object from Arraylist ********/
                 tempValues = null;
                 tempValues = ( ListModel ) data.get( position );
                 Log.d("TempValue", tempValues.getDesc() + " " + tempValues.getDate());
                  
//                 /************  Set Model values in Holder elements ***********/
//
//                 Log.d("List Color",tempValues.getColor() );
                 /*int num_color;
                 if(tempValues.getDate().equals("Metal")) 
                	 num_color = Color.parseColor("#0D4663");
                 else if (tempValues.getDate().equals("Plastic")) 
					 num_color = Color.parseColor("#2179A3");
			     else if (tempValues.getDate().equals("Paper"))
                	 num_color = Color.parseColor("#3096C0");
			     else  
                	 num_color = Color.parseColor("#8AD3E9");
//			     else if (tempValues.getColor().equals("Cyan"))
//                	 num_color = Color.parseColor("#3399FF");
//			     else if (tempValues.getColor().equals("Dark Gray"))
//                	 num_color = Color.parseColor("#63635B");
//			     else if (tempValues.getColor().equals("Green"))
//                	 num_color = Color.parseColor("#339966");
//                 else if (tempValues.getColor().equals("Yellow"))
//                	 num_color = Color.parseColor("#FFFF66");
//                 else
//                 {
//                	 num_color = Color.TRANSPARENT;
//                	 holder.text.setTextColor(Color.BLACK);
//                	 holder.text1.setTextColor(Color.BLACK);
//                	 holder.text2.setTextColor(Color.BLACK);
//                	 holder.text3.setTextColor(Color.BLACK);
//                 }
//                 
                 if(tempValues.getStartTime().equalsIgnoreCase("battery"))
                	 holder.img.setImageResource(R.drawable.batteryicon);
                 else {
                	 if (tempValues.getDesc().contains("Bin"))
                		 holder.img.setImageResource(R.drawable.binicon);
                	 else {
                		 if (tempValues.getDate().contains("Clean"))
                			 holder.img.setImageResource(R.drawable.cleanicon);
                	  else if (tempValues.getDate().contains("Paper"))
            			 holder.img.setImageResource(R.drawable.wifiicon);
            	 	  else if (tempValues.getDate().contains("Issue"))
            			 holder.img.setImageResource(R.drawable.repairicon);
            	 	  else if (tempValues.getDate().contains("Coffee"))
            			 holder.img.setImageResource(R.drawable.breakouticon);
            	 	  else 
            			 holder.img.setImageResource(R.drawable.dispensericon);
                	 }	                  	 
                 }*/
//                 
                 
                 Log.d("Name", position + "its here" + tempValues.getTitle() +" " + tempValues.getDesc());
                  holder.text.setText( tempValues.getTitle());
                  holder.text1.setText( tempValues.getDesc());
                  holder.text2.setText( tempValues.getDate());
                  holder.text3.setText( tempValues.getStartTime());
                  holder.text4.setText( tempValues.getEndTime());
                  holder.btn.setOnClickListener(new OnItemClickListener( position ));

                  /* This is for downloading an image

                  new DownloadImageTask(holder.img)
                  .execute(tempValues.getImage()); */

                  /* Setting a background

                  holder.rel.setBackgroundColor(num_color);

                  if((tempValues.getResolved() != null) && !tempValues.getDesc().contains("Bin")) {
                	  
                	  Log.d("Adapter", "I am not null " + tempValues.getResolved() + " " + tempValues.getMAC());
                	  //holder.btn.setVisibility(View.VISIBLE);
                	  holder.btn.setText("Resolved");
                	  //holder.btn.setOnClickListener(new OnItemClickListener( position ));
                	  holder.btn.setBackgroundResource(R.drawable.selector2);
                  }
                  else {
                	  holder.btn.setText("");
                	  holder.btn.setBackgroundColor(num_color);
                  }*/
                   
                  /******** Set Item Click Listner for LayoutInflater for each row *******/
 
                 
             }
             return vi;
         }
         
         public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        	    ImageView bmImage;

        	    public DownloadImageTask(ImageView bmImage) {
        	        this.bmImage = bmImage;
        	    }

        	    protected Bitmap doInBackground(String... urls) {
        	        String urldisplay = urls[0];
        	        Bitmap mIcon11 = null;
        	        try {
        	            InputStream in = new java.net.URL(urldisplay).openStream();
        	            mIcon11 = BitmapFactory.decodeStream(in);
        	        } catch (Exception e) {
        	            Log.e("Error", e.getMessage());
        	            e.printStackTrace();
        	        }
        	        return mIcon11;
        	    }

        	    protected void onPostExecute(Bitmap result) {
        	        bmImage.setImageBitmap(result);
        	    }
        	}
          
         @Override
         public void onClick(View v) {
                 Log.v("CustomAdapter", "=====Row button clicked=====");
         }
          
         /********* Called when button clicked in ListView ************/
         private class OnItemClickListener  implements OnClickListener{           
             private int mPosition;
              
             OnItemClickListener(int position){
                 mPosition = position;
                 Log.d("Notif", "onclick at position " + mPosition);
                 EventListActivity sct = (EventListActivity) activity;
  
               /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/
                
                tempValues = ( ListModel ) data.get( mPosition );

             }
              
             @Override
             public void onClick(View arg0) {
 
        
            	 Log.d("Notif", "onclick");
               EventListActivity sct = (EventListActivity) activity;
 
              /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/
               
               tempValues = ( ListModel ) data.get( mPosition );
                 sct.onItemClick(tempValues.getTitle(),tempValues.getDesc(),tempValues.getDate(),
                         tempValues.getStartTime());
                 //mqtt = "{\"id\":"+tempValues.getMAC()+","+tempValues.getResolved()+"}";
                 //GetNotifList getNotifList = new GetNotifList();
                 //getNotifList.execute();

                 Intent intent = new Intent(Intent.ACTION_INSERT);
                 intent.setType("vnd.android.cursor.item/event");
                 intent.putExtra(Events.TITLE, tempValues.getTitle());
                 intent.putExtra(Events.EVENT_LOCATION, "Home suit home");
                 intent.putExtra(Events.DESCRIPTION, tempValues.getDesc());

                 // Setting dates
                 GregorianCalendar calDate = null;
                 Date date = null;
                 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                     DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                     try {
                         date = df.parse(tempValues.getDate());
                     } catch (ParseException e) {
                         e.printStackTrace();
                     }
                 }

                 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                     //calDate = new GregorianCalendar(2019, 10, 02);
                     calDate = new GregorianCalendar();
                     calDate.setTime(date);
                 }
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                     intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                             calDate.getTimeInMillis());
                 }
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                     intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                             calDate.getTimeInMillis());
                 }

                 // make it a full day event
                 intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

                 // make it a recurring Event
                 intent.putExtra(Events.RRULE, "FREQ=WEEKLY;COUNT=11;WKST=SU;BYDAY=TU,TH");

                 // Making it private and shown as busy
                 intent.putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PRIVATE);
                 intent.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);

                 intent.setData(CalendarContract.Events.CONTENT_URI);
                 sct.startActivity(intent);
//               sct.onItemClick(tempValues.getName(),tempValues.getImage(),tempValues.getDesc(),
//            		   tempValues.getDesc());
               //mqtt = "{\"id\":\""+tempValues.getMAC()+"\","+tempValues.getResolved()+"}";
              // GetNotifList getNotifList = new GetNotifList();
               //getNotifList.execute();
               
               }               
         }
         /*
         class GetNotifList extends AsyncTask<String, Void, Boolean> {

 	        ProgressDialog dialog = new ProgressDialog(EventListActivity.context);
 	        JSONObject response;

 	       public GetNotifList(){

 	            dialog.setMessage("Fetching pending Notificaitons");
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

 	                URL url = new URL("https://"+CommonUtilities.server+"/api/3.0/scripts/js/execute/facilitiesView?auth="+CommonUtilities.Auth);

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
 	                /*con.setRequestMethod("POST");

 	                JSONArray apis_list = new JSONArray();
 	                apis_list.put("mqttCall");

 	                JSONObject parameter = new JSONObject();
 	                parameter.put("q", apis_list.toString());
 	                parameter.put("mqttCall", mqtt);

 	                con.connect();
 	                Log.d("Resolved", mqtt);

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
 	    	        response = new JSONObject(result.toString());    
 	    	        try {
 	    	        	JSONObject results;
 	    				results = response.getJSONObject("result");
 	    				CommonUtilities.notifs = results.getJSONObject("getUserAlerts");
 	    			} catch (JSONException e) {
 	    				// TODO Auto-generated catch block
 	    				e.printStackTrace();
 	    			}

 	    	        
 	    	        switch (con.getResponseCode()) {
 	                case HttpURLConnection.HTTP_OK:
 	                	Log.d("HTTP Response", "OK");
 	                	con.disconnect();
 	                    return true;
 	    			case HttpURLConnection.HTTP_UNAUTHORIZED:
 	                	Log.d("HTTP Response", "Unauthorized");
 	                    return false;
 	    			case HttpURLConnection.HTTP_INTERNAL_ERROR:
 	                	Log.d("HTTP Response", "Internal Error ");
 	                	return false;
 	    			default:
 	                	Log.d("HTTP Response", "Unknown Error ");
 	                    return false; // abort
 	            }
 	    	        
 	    	    } catch (JSONException e){                                                          
 	    	    	e.printStackTrace();   
 	    	    	return false;
 	    	    } catch (IOException e) {                                                           
 	    	    	e.printStackTrace();
 	    	    	return false;
 	    	    } 

 	        }

 	        // onPostExecute displays the results of the AsyncTask.
 	        @Override
 	        protected void onPostExecute(Boolean result) {
 	        	
 	        	Activity act = (Activity) EventListActivity.context;
 	        	Intent intent = act.getIntent();
 	        	act.finish();
 	        	act.startActivity(intent);
 	        	
 	            if(dialog.isShowing())
 	                dialog.cancel();

 	        }
 	    }*/
}