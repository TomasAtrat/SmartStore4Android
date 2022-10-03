package com.zebra.rfid.demo.sdksample.services;

import static com.zebra.rfid.demo.sdksample.utils.Constants.API_URL;
import static com.zebra.rfid.demo.sdksample.utils.Constants.GET_EXPEDITION_TYPES_SERVICE;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.zebra.rfid.demo.sdksample.components.VolleyRequest;
import com.zebra.rfid.demo.sdksample.models.ExpeditionType;
import com.zebra.rfid.demo.sdksample.utils.wrappers.ListOfExpeditionType;

import org.json.JSONObject;

import java.util.List;

public class ExpeditionService {

    private final String TAG = "ExpeditionService";

    private Context context;

    public ExpeditionService(Context context) {
        this.context = context;
    }

    public void populateExpeditionTypeSpinner(Spinner expeditionSpinner){
        final String mURL = API_URL + GET_EXPEDITION_TYPES_SERVICE;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                mURL,
                null,
                response -> this.populateExpeditionTypeSpinner(response, expeditionSpinner),
                error -> Log.e(TAG, error.getMessage()));

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private void populateExpeditionTypeSpinner(JSONObject response, Spinner expeditionSpinner) {
        ListOfExpeditionType wrapper = new Gson().fromJson(response.toString(), ListOfExpeditionType.class);
        List<ExpeditionType> expeditionTypes = wrapper.getExpeditionTypeList();

        ArrayAdapter<ExpeditionType> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item,
                expeditionTypes);

        expeditionSpinner.setAdapter(adapter);
    }

}
