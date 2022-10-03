package com.zebra.rfid.demo.sdksample.services;

import static com.zebra.rfid.demo.sdksample.utils.Constants.ADD_ORDER_SERVICE;
import static com.zebra.rfid.demo.sdksample.utils.Constants.API_URL;
import static com.zebra.rfid.demo.sdksample.utils.validation.CommonValidation.isStringNullOrEmpty;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zebra.rfid.demo.sdksample.components.VolleyRequest;
import com.zebra.rfid.demo.sdksample.models.OrderDetail;
import com.zebra.rfid.demo.sdksample.models.OrderInfo;
import com.zebra.rfid.demo.sdksample.utils.enums.ExpeditionTypeEnum;
import com.zebra.rfid.demo.sdksample.utils.validation.CommonValidation;
import com.zebra.rfid.demo.sdksample.utils.wrappers.OrderWrapper;
import com.zebra.rfid.demo.sdksample.views.MenuActivity;
import com.zebra.rfid.demo.sdksample.views.itemlocation.ItemSelectionActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderService {
    private final String TAG = "OrderService";

    private Context context;

    public OrderService() {
    }

    public OrderService(Context context) {
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


    public List<String> isOrderValid(OrderInfo orderInfo) {
        List<String> errors = new ArrayList<>();

        if (orderInfo.getExpedition().getId().equals(ExpeditionTypeEnum.BOPIS.getValue()) &&
                orderInfo.getAcceptsPartialExpedition())
            errors.add("No se puede aceptar expedición parcial de un pedido recogido en tienda");

        if (orderInfo.getExpedition().getId().equals(ExpeditionTypeEnum.SEND_TO_ADDRESS.getValue()) &&
                isStringNullOrEmpty(orderInfo.getAddress()))
            errors.add("Debe especificarse una dirección si el tipo de expedición es de tipo " + orderInfo.getExpedition().getDescription());

        return errors;
    }
}
