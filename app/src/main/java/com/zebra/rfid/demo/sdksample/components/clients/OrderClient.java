package com.zebra.rfid.demo.sdksample.components.clients;

import static com.zebra.rfid.demo.sdksample.utils.Constants.ADD_ORDER_SERVICE;
import static com.zebra.rfid.demo.sdksample.utils.Constants.API_URL;
import static com.zebra.rfid.demo.sdksample.utils.Constants.GET_ORDERS_TO_PREPARE_SERVICE;
import static com.zebra.rfid.demo.sdksample.utils.validation.CommonValidation.isStringNullOrEmpty;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zebra.rfid.demo.sdksample.components.VolleyRequest;
import com.zebra.rfid.demo.sdksample.models.Inventory;
import com.zebra.rfid.demo.sdksample.models.OrderInfo;
import com.zebra.rfid.demo.sdksample.utils.enums.ExpeditionTypeEnum;
import com.zebra.rfid.demo.sdksample.utils.wrappers.ListOfInventories;
import com.zebra.rfid.demo.sdksample.utils.wrappers.ListOfOrderWrapper;
import com.zebra.rfid.demo.sdksample.utils.wrappers.OrderWrapper;
import com.zebra.rfid.demo.sdksample.views.itemlocation.ItemSelectionActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderClient {
    private final String TAG = "OrderClient";
    private Context context;

    public OrderClient(Context context) {
        this.context = context;
    }

    public void addOrder(OrderWrapper orderWrapper) throws JSONException {

        String mURL = API_URL + ADD_ORDER_SERVICE;

        Gson gson = new GsonBuilder()
                .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                mURL,
                new JSONObject(gson.toJson(orderWrapper)),
                this::onSendOrderResponse,
                error -> {
                    Log.e(TAG, error.getMessage());
                    Toast.makeText(context, "Se produjo un error al envíar los datos al servidor", Toast.LENGTH_SHORT).show();
                });

        VolleyRequest.getInstance(context).addToRequestQueue(request);

    }

    private void onSendOrderResponse(JSONObject response) {
        Toast.makeText(context, "Datos guardadados correctamente", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, ItemSelectionActivity.class);
        context.startActivity(intent);
    }

    public void setAvailableOrders(Spinner orderSpinner) {
        final String mURL = API_URL + GET_ORDERS_TO_PREPARE_SERVICE;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                mURL,
                null,
                response -> this.setAvailableOrders(response, orderSpinner),
                error -> Log.e(TAG, error.getMessage()));

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private void setAvailableOrders(JSONObject response, Spinner orderSpinner) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ").create();
        try {
            ListOfOrderWrapper wrapper = gson.fromJson(response.toString(), ListOfOrderWrapper.class);

            ArrayAdapter<OrderInfo> adapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_spinner_dropdown_item,
                    wrapper.getOrders());

            orderSpinner.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
