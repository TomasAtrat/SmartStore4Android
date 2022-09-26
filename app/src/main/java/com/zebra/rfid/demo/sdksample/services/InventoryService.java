package com.zebra.rfid.demo.sdksample.services;

import static com.zebra.rfid.demo.sdksample.utils.Constants.ADD_INVENTORY_DETAILS_SERVICE;
import static com.zebra.rfid.demo.sdksample.utils.Constants.API_URL;
import static com.zebra.rfid.demo.sdksample.utils.Constants.GET_INVENTORY_DETAILS_SERVICE;
import static com.zebra.rfid.demo.sdksample.utils.Constants.INVENTORY_SERVICE;
import static com.zebra.rfid.demo.sdksample.views.inventory.PerformInventoryActivity.counterByBarcode;
import static com.zebra.rfid.demo.sdksample.views.inventory.PerformInventoryActivity.adapter;
import static com.zebra.rfid.demo.sdksample.views.inventory.PerformInventoryActivity.details;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zebra.rfid.demo.sdksample.components.VolleyRequest;
import com.zebra.rfid.demo.sdksample.models.Inventory;
import com.zebra.rfid.demo.sdksample.models.InventoryDetail;
import com.zebra.rfid.demo.sdksample.models.InventoryProblem;
import com.zebra.rfid.demo.sdksample.utils.wrappers.InventoryData;
import com.zebra.rfid.demo.sdksample.utils.wrappers.ListOfInventories;
import com.zebra.rfid.demo.sdksample.views.MenuActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InventoryService {

    private static final String TAG = "InventoryService";
    private static final String QTY_EXCEEDS_MSG = "Cantidad leída excede esperado";
    private static final String QTY_LACKS_MSG = "Cantidad esperada faltante";

    private List<Inventory> inventories;
    private Context context;


    public InventoryService(Context context) {
        this.context = context;
    }

    public void setAvailableInventories(Spinner inventorySpinner) {
        final String mURL = API_URL + INVENTORY_SERVICE;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                mURL,
                null,
                response -> this.setAvailableInventories(response, inventorySpinner),
                error -> Log.e(TAG, error.getMessage()));

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

    public void setInventoryDetails(Long id, ListView detailsList) {
        final String mURL = String.format(API_URL + GET_INVENTORY_DETAILS_SERVICE, id);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                mURL,
                null,
                response -> this.setInventoryDetailsIntoList(response, detailsList),
                error -> Log.e(TAG, error.getMessage()));

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private void setInventoryDetailsIntoList(JSONObject response, ListView detailsList) {
        InventoryData inventoryData = new Gson().fromJson(response.toString(), InventoryData.class);

        details = new ArrayList<>(inventoryData.getDetails());

        counterByBarcode = convertDetailsListToHashMap(details);

        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, details);

        detailsList.setAdapter(adapter);
    }

    private HashMap<InventoryDetail, Integer> convertDetailsListToHashMap(List<InventoryDetail> details) {
        HashMap<InventoryDetail, Integer> hashMap = new HashMap<>();
        details.forEach(i -> hashMap.put(i, 0));
        return hashMap;
    }

    public void saveInventory(Inventory inventory, List<InventoryDetail> inventoryDetails) {
        try {
            List<InventoryProblem> problems = getInventoryProblemsIfAny(inventoryDetails);

            inventory.setActive(problems.size() > 0);

            InventoryData inventoryData = new InventoryData(inventory,
                    inventoryDetails,
                    problems);

            this.sendInventory(inventoryData);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private List<InventoryProblem> getInventoryProblemsIfAny(List<InventoryDetail> inventoryDetails) {
        List<InventoryProblem> problems = new ArrayList<>();

        inventoryDetails.forEach(detail -> {
            if (!detail.getReadQty().equals(detail.getSupposedQty()))
                problems.add(createInventoryProblem(detail));
        });

        return problems;
    }

    private InventoryProblem createInventoryProblem(InventoryDetail detail) {
        int difference = detail.getSupposedQty() - detail.getReadQty();

        return new InventoryProblem(detail.getBarcode().getProduct().getId(),
                Math.abs(difference),
                difference > 0 ? QTY_LACKS_MSG : QTY_EXCEEDS_MSG,
                detail);
    }

    private void sendInventory(InventoryData inventoryData) throws JSONException {
        final String mURL = API_URL + ADD_INVENTORY_DETAILS_SERVICE;

        Gson gson = new GsonBuilder()
                .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

        Log.d(TAG, "Gson: " + gson.toJson(inventoryData));

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                mURL,
                new JSONObject(gson.toJson(inventoryData)),
                this::onSendInventoryResponse,
                error -> {
                    Log.e(TAG, error.getMessage());
                    Toast.makeText(context, "Se produjo un error al envíar los datos al servidor", Toast.LENGTH_SHORT).show();
                });

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private void onSendInventoryResponse(JSONObject response) {
        Toast.makeText(context, "Datos guardadados correctamente", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, MenuActivity.class);
        context.startActivity(intent);
    }
}


