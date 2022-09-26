package com.zebra.rfid.demo.sdksample.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.zebra.rfid.demo.sdksample.components.VolleyRequest;
import com.zebra.rfid.demo.sdksample.models.UserInfo;
import com.zebra.rfid.demo.sdksample.utils.Constants;
import com.zebra.rfid.demo.sdksample.views.MenuActivity;

import org.json.JSONObject;

public class AuthService {

    public static UserInfo userInfo;
    private static String TAG = "AuthService";
    private Context context;

    public AuthService(Context context) {
        this.context = context;
    }

    public void login(String username, String password) {

        String url = Constants.API_URL + String.format(Constants.AUTH_SERVICE, username, password);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, this::setUserAndGoToMenu, this::notifyBadCredentials);

        VolleyRequest.getInstance(context).addToRequestQueue(request);

    }

    private void setUserAndGoToMenu(JSONObject user) {
        String json = user.toString();
        Log.i(TAG, json);
        userInfo = new Gson().fromJson(json, UserInfo.class);
        Intent intent = new Intent(context, MenuActivity.class);
        context.startActivity(intent);
    }

    private void notifyBadCredentials(VolleyError volleyError) {
        Log.e(TAG, volleyError.getMessage());
        Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
    }
}
