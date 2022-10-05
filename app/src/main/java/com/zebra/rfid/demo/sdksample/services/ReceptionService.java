package com.zebra.rfid.demo.sdksample.services;

import static com.zebra.rfid.demo.sdksample.utils.Constants.ADD_RECEPTION_SERVICE;
import static com.zebra.rfid.demo.sdksample.utils.Constants.API_URL;
import static com.zebra.rfid.demo.sdksample.utils.Constants.ERP_URL;
import static com.zebra.rfid.demo.sdksample.utils.Constants.GET_RECEPTION_DETAILS_SERVICE;
import static com.zebra.rfid.demo.sdksample.utils.Constants.GET_RECEPTION_SERVICE;
import static com.zebra.rfid.demo.sdksample.views.reception.ReceptionActivity.adapter;
import static com.zebra.rfid.demo.sdksample.views.reception.ReceptionActivity.counterByBarcode;
import static com.zebra.rfid.demo.sdksample.views.reception.ReceptionActivity.details;

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
import com.zebra.rfid.demo.sdksample.models.ReceptionDetail;
import com.zebra.rfid.demo.sdksample.models.ReceptionList;
import com.zebra.rfid.demo.sdksample.models.ReceptionProblem;
import com.zebra.rfid.demo.sdksample.utils.wrappers.ListOfReception;
import com.zebra.rfid.demo.sdksample.utils.wrappers.Reception;
import com.zebra.rfid.demo.sdksample.views.MenuActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReceptionService {

    private static final String TAG = "InventoryService";
    private static final String QTY_EXCEEDS_MSG = "Cantidad leída excede esperado";
    private static final String QTY_LACKS_MSG = "Cantidad esperada faltante";
    private Context context;
    private List<ReceptionList> references;

    public ReceptionService(Context context) {
        this.context = context;
    }

    public void setAvailableReferences(Spinner receptionSpinner) {
        final String mURL = API_URL + GET_RECEPTION_SERVICE;

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

    public void saveReception(ReceptionList reception, List<ReceptionDetail> details) {
        try {
            List<ReceptionProblem> problems = getReceptionProblemsIfAny(details);

            Reception receptionData = new Reception(reception,
                    details,
                    problems);

            this.informToERP(receptionData);

            this.sendReception(receptionData);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    private void informToERP(Reception receptionData) throws JSONException {
        final String mURL = ERP_URL + ADD_RECEPTION_SERVICE;

        Gson gson = new GsonBuilder()
                .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

        Log.d(TAG, "Gson: " + gson.toJson(receptionData));

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                mURL,
                new JSONObject(gson.toJson(receptionData)),
                null,
                error -> {
                    Log.e(TAG, error.getMessage());
                    Toast.makeText(context, "Se produjo un error al envíar los datos al ERP", Toast.LENGTH_LONG).show();
                });

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private void sendReception(Reception receptionData) throws JSONException {
        final String mURL = API_URL + ADD_RECEPTION_SERVICE;

        Gson gson = new GsonBuilder()
                .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

        Log.d(TAG, "Gson: " + gson.toJson(receptionData));

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                mURL,
                new JSONObject(gson.toJson(receptionData)),
                this::onSendReceptionResponse,
                error -> {
                    Log.e(TAG, error.getMessage());
                    Toast.makeText(context, "Se produjo un error al envíar los datos al servidor", Toast.LENGTH_LONG).show();
                });

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private void onSendReceptionResponse(JSONObject jsonObject) {
        Toast.makeText(context, "Datos guardadados correctamente", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(context, MenuActivity.class);
        context.startActivity(intent);
    }

    private List<ReceptionProblem> getReceptionProblemsIfAny(List<ReceptionDetail> details) {
        List<ReceptionProblem> problems = new ArrayList<>();

        details.forEach(detail -> {
            if (!detail.getReceivedQty().equals(detail.getScheduledQty()))
                problems.add(createReceptionProblem(detail));
        });

        return problems;
    }

    private ReceptionProblem createReceptionProblem(ReceptionDetail detail) {
        int difference = detail.getScheduledQty() - detail.getReceivedQty();

        return new ReceptionProblem(detail.getBarcode().getProduct().getId(), Math.abs(difference), difference > 0 ? QTY_LACKS_MSG : QTY_EXCEEDS_MSG, false, detail);
    }

    public void setReceptionDetails(Long id, ListView detailsList) {
        final String mURL = String.format(API_URL + GET_RECEPTION_DETAILS_SERVICE, id);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                mURL,
                null,
                response -> this.setReceptionDetailsIntoList(response, detailsList),
                error -> Log.e(TAG, error.getMessage()));

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private void setReceptionDetailsIntoList(JSONObject response, ListView detailsList) {
        try {
            Reception receptionData = new Gson().fromJson(response.toString(), Reception.class);

            details = new ArrayList<>(receptionData.getDetails());

            counterByBarcode = convertDetailsListToHashMap(details);

            adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, details);

            detailsList.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private HashMap<ReceptionDetail, Integer> convertDetailsListToHashMap(List<ReceptionDetail> details) {
        HashMap<ReceptionDetail, Integer> hashMap = new HashMap<>();
        details.forEach(i -> hashMap.put(i, 0));
        return hashMap;
    }
}
