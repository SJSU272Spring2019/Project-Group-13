package com.calendared;

import org.json.JSONArray;
import org.json.JSONObject;

public class CommonUtilities {
	static final String SENDER_ID = "65738629691"; 
	public static int SERVERPORT = 4445, t_counter = 0;
	public static boolean device_found = false, refresh = false; //INDICATES IF MESSAGE IS FROM WEB SERVER(TRUE) OR UDP(FALSE)
	static String Auth, loc, subtype, device_type, label, server = "localhost";
	//static final String SERVER_URL_AUTH = "https://"+server+"/api/3.0/login";
	static final String SERVER_URL_FACILITIES = "http://"+server+"/getJSON";
	static JSONObject sensors, locations;
	static JSONArray notifs;
	static String SENT_TOKEN_TO_SERVER;
	static String REGISTRATION_COMPLETE;
	static String[] TOPICS = {"global"};
	static String android_client_id = "515619392132-eik3ukaie7ok9ohh44s2bfb4439u2phm.apps.googleusercontent.com";
}