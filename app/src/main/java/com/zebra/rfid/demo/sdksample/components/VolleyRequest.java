package com.zebra.rfid.demo.sdksample.components;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRequest {
    private static VolleyRequest volleyRequest;
    private RequestQueue requestQueue;
    private static Context ctx;

    private VolleyRequest(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleyRequest getInstance(Context context) {
        if (volleyRequest == null) {
            volleyRequest = new VolleyRequest(context);
        }
        return volleyRequest;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
