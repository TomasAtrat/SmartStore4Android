package com.zebra.rfid.demo.sdksample.services;

import static com.zebra.rfid.demo.sdksample.utils.Constants.API_URL;
import static com.zebra.rfid.demo.sdksample.utils.Constants.BARCODE_OBJ;
import static com.zebra.rfid.demo.sdksample.utils.Constants.GET_STOCK_SERVICE;
import static com.zebra.rfid.demo.sdksample.utils.Constants.LIST_OF_STOCK_OBJ;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.components.VolleyRequest;
import com.zebra.rfid.demo.sdksample.models.Barcode;
import com.zebra.rfid.demo.sdksample.utils.wrappers.ListOfStock;
import com.zebra.rfid.demo.sdksample.views.MainActivity;
import com.zebra.rfid.demo.sdksample.views.order.OrderActivity;

import org.json.JSONObject;

public class ProductService {

    private Context context;

    public ProductService(Context context) {
        this.context = context;
    }

    private final String TAG = "ProductService";

    public void getBarcodeIfHasStock(Barcode barcode) {
        final String mURL = String.format(API_URL + GET_STOCK_SERVICE, barcode.getId());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                mURL,
                null,
                this::onResponseReceived,
                error -> Log.e(TAG, error.getMessage()));

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private void onResponseReceived(JSONObject jsonObject) {
        Log.d(TAG, "Json: " + jsonObject.toString());

        ListOfStock wrapper = new Gson().fromJson(jsonObject.toString(), ListOfStock.class);

        if (wrapper.getStockList() == null)
            Toast.makeText(context, "No existe stock para el artículo en ninguna sucursal", Toast.LENGTH_LONG).show();

        else navigateToIntentWhenResponseHasStock(wrapper);
    }

    private void navigateToIntentWhenResponseHasStock(ListOfStock wrapper) {
        if (currentBranchHasStock(wrapper)) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(BARCODE_OBJ, wrapper);
            context.startActivity(intent);
        } else {
            new AlertDialog.Builder(context)
                    .setTitle("Producto sin stock")
                    .setMessage("No hay stock para este producto en la sucursal actual. ¿Deseas realizar un pedido para otra sucursal?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        Intent intent = new Intent(context, OrderActivity.class);
                        intent.putExtra(LIST_OF_STOCK_OBJ, wrapper);
                        context.startActivity(intent);
                    })
                    .setNegativeButton(R.string.CancelButton, null).show();
        }
    }

    private boolean currentBranchHasStock(ListOfStock wrapper) {
        return wrapper.getStockList().stream().anyMatch(i -> i.getBranch().getId().equals(AuthService.userInfo.getIdBranch()));
    }

}
