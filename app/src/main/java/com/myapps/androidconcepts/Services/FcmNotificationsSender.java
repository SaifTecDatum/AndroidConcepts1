package com.myapps.androidconcepts.Services;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.myapps.androidconcepts.Helpers.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FcmNotificationsSender {
    private static final String TAG = "FcmNotificationsSender";
    String userFcmToken;     //deviceToken
    String title;
    String body;
    Context context;
    Activity activity;
    private RequestQueue requestQueue;

    public FcmNotificationsSender(String userFcmToken, String title, String body, Context context, Activity activity) {
        this.userFcmToken = userFcmToken;
        this.title = title;
        this.body = body;
        this.context = context;
        this.activity = activity;
    }

    public void sendNotification() {
        requestQueue = Volley.newRequestQueue(activity);
        JSONObject mainObject = new JSONObject();
        try {
            mainObject.put("to", userFcmToken);
            JSONObject notifyObject = new JSONObject();
            notifyObject.put("title", title);
            notifyObject.put("body", body);
            notifyObject.put("icon", "app_logo");

            mainObject.put("notification", notifyObject);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.fcmPostUrl_volley, mainObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(activity, "Notification Successfully Sent in Ammijaan's Mobile.", Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //if notification doesn't trigger in device then need to check it's deviceToken
                    //in firebase bcoz it'll change as per firebaseRules.
                    Toast.makeText(activity, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("Content-Type", "application/json");
                    map.put("authorization", "key= " + Constants.fcmServerKey);

                    return map;
                }
            };

            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}