package com.zebra.rfid.demo.sdksample.services;

import static com.zebra.rfid.demo.sdksample.utils.Constants.API_URL;
import static com.zebra.rfid.demo.sdksample.utils.Constants.RECEPTION_SERVICE;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.zebra.rfid.demo.sdksample.components.VolleyRequest;
import com.zebra.rfid.demo.sdksample.models.ReceptionList;
import com.zebra.rfid.demo.sdksample.utils.wrappers.ListOfReception;

import org.json.JSONObject;

import java.util.List;

public class ReceptionService {

    private static final String TAG = "InventoryService";
    private Context context;
    private List<ReceptionList> references;

    public ReceptionService(Context context){
        this.context = context;
    }

    public void setAvailableReferences(Spinner receptionSpinner) {
        final String mURL = API_URL + RECEPTION_SERVICE;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                mURL,
                null,
                response -> this.setAvailableReference(response, receptionSpinner),
                error -> Log.e(TAG, error.getMessage()));

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private void setAvailableReference(JSONObject jsonObject, Spinner referenceSpinner) {
        ListOfReception wrapper = new Gson().fromJson(jsonObject.toString(), ListOfReception.class);
        references = wrapper.getReferences();

        ArrayAdapter<ReceptionList> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item,
                references);

        referenceSpinner.setAdapter(adapter);

    }
}
