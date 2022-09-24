package com.zebra.rfid.demo.sdksample.services;

import static com.zebra.rfid.demo.sdksample.utils.Constants.API_URL;
import static com.zebra.rfid.demo.sdksample.utils.Constants.GET_INVENTORY_DETAILS_SERVICE;
import static com.zebra.rfid.demo.sdksample.utils.Constants.INVENTORY_SERVICE;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.zebra.rfid.demo.sdksample.components.VolleyRequest;
import com.zebra.rfid.demo.sdksample.models.Inventory;
import com.zebra.rfid.demo.sdksample.models.InventoryDetail;
import com.zebra.rfid.demo.sdksample.utils.wrappers.InventoryData;
import com.zebra.rfid.demo.sdksample.utils.wrappers.ListOfInventories;

import org.json.JSONObject;

import java.util.List;


public class InventoryService {

    private Context context;
    private static String TAG = "InventoryService";
    private List<Inventory> inventories;


    public InventoryService(Context context) {
        this.context = context;
    }

    public void setAvailableInventories(Spinner inventorySpinner) {
        final String mURL = API_URL + INVENTORY_SERVICE;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                mURL,
                null,
                response -> this.setAvailableInventories(response, inventorySpinner),
                error-> Log.e(TAG, error.getMessage()));

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private void setAvailableInventories(JSONObject jsonObject, Spinner inventorySpinner) {
        ListOfInventories wrapper = new Gson().fromJson(jsonObject.toString(), ListOfInventories.class);
        inventories = wrapper.getInventories();

        ArrayAdapter<Inventory> adapter = new ArrayAdapter<Inventory>(context,
                android.R.layout.simple_spinner_dropdown_item,
                inventories);

        inventorySpinner.setAdapter(adapter);

    }

    public void setInventoryDetails(Long id, ListView detailsList){
        final String mURL = String.format(API_URL + GET_INVENTORY_DETAILS_SERVICE, id);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                mURL,
                null,
                response -> this.setInventoryDetailsIntoList(response, detailsList),
                error-> Log.e(TAG, error.getMessage()));

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private void setInventoryDetailsIntoList(JSONObject response, ListView detailsList) {
        InventoryData inventoryData = new Gson().fromJson(response.toString(), InventoryData.class);

        List<InventoryDetail> details = inventoryData.getDetails();

        ArrayAdapter<InventoryDetail> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, details);

        detailsList.setAdapter(adapter);
    }
}


