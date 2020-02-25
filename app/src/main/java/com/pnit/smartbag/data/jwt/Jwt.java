package com.pnit.smartbag.data.jwt;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pnit.smartbag.AppController;

public class Jwt {

    private String urlJsonObj = "";
    private static String TAG;
    final JSONObject obj;
    String responseText;

    public Jwt(String classSimpleName, JSONObject object){
        this.TAG = classSimpleName;
        obj = object;
    }

    public void makeJsonRequest() {
        String json_req = "json_req";
        String url = "";
        //String in values.strings?

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            responseText = response.getString("name");
                            //TODO: Configure Response from JSON Structure
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        AppController.getInstance().addToRequestQueue(req);
    }

    public String getResponseText(){
        return responseText;
    }
}
