package com.zebra.rfid.demo.sdksample.components.clients;

import static com.zebra.rfid.demo.sdksample.utils.Constants.ADD_PREPARATION_SERVICE;
import static com.zebra.rfid.demo.sdksample.utils.Constants.API_URL;
import static com.zebra.rfid.demo.sdksample.utils.Constants.GET_PREPARATION_SERVICE;
import static com.zebra.rfid.demo.sdksample.utils.Constants.GET_TOP10_EPC_BARCODE_SERVICE;
import static com.zebra.rfid.demo.sdksample.views.preparation.PreparationActivity.listOfTenEpc;
import static com.zebra.rfid.demo.sdksample.views.preparation.PreparationActivity.preparationDetails;
import static com.zebra.rfid.demo.sdksample.views.preparation.PreparationActivity.productDescriptionTxt;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zebra.rfid.demo.sdksample.components.VolleyRequest;
import com.zebra.rfid.demo.sdksample.models.Barcode;
import com.zebra.rfid.demo.sdksample.services.AuthService;
import com.zebra.rfid.demo.sdksample.utils.wrappers.ListOfEpc;
import com.zebra.rfid.demo.sdksample.utils.wrappers.PreparationWrapper;
import com.zebra.rfid.demo.sdksample.views.MenuActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class PreparationClient {
    private final String TAG = "PreparationClient";

    private Context context;

    public PreparationClient(Context context) {
        this.context = context;
    }


    public void fetchPreparationDetailsAsync(Long orderId) {
        final String mURL = String.format(API_URL + GET_PREPARATION_SERVICE, orderId, AuthService.userInfo.getId());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                mURL,
                null,
                this::setPreparationDetails,
                error -> Log.e(TAG, error.getMessage()));

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private void setPreparationDetails(JSONObject response) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ").create();
        try {
            PreparationWrapper wrapper = gson.fromJson(response.toString(), PreparationWrapper.class);
            preparationDetails = wrapper.getDetails();

            if(preparationDetails != null && preparationDetails.size() > 0){
                productDescriptionTxt.setText(preparationDetails.get(0).getBarcode().getProduct().getDescription());
                getTopTenEpcByBarcodeAsync(preparationDetails.get(0).getBarcode());
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void getTopTenEpcByBarcodeAsync(Barcode barcode) {
        final String mURL = String.format(API_URL + GET_TOP10_EPC_BARCODE_SERVICE, barcode.getId());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                mURL,
                null,
                this::setListOfTenEpc,
                error -> Log.e(TAG, error.getMessage()));

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private void setListOfTenEpc(JSONObject response) {
        ListOfEpc wrapper = new Gson().fromJson(response.toString(), ListOfEpc.class);

        listOfTenEpc = wrapper.getEpcBarcodeList();

        Log.i(TAG, new Gson().toJson(listOfTenEpc));
    }

    public void savePreparationAndDetailsAsync(PreparationWrapper wrapper) throws JSONException {
        final String mURL = API_URL + ADD_PREPARATION_SERVICE;

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ").create();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                mURL,
                new JSONObject(gson.toJson(wrapper)),
                this::onSavePreparationResponse,
                error -> {
                    Toast.makeText(context, "Ocurrió un error al envíar los datos al servidor", Toast.LENGTH_LONG).show();
                    Log.e(TAG, error.getMessage());
                });

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private void onSavePreparationResponse(JSONObject jsonObject) {
        Toast.makeText(context, "Datos guardados correctamente", Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(context, MenuActivity.class));
    }
}
