package com.zebra.rfid.demo.sdksample.services;

import static com.zebra.rfid.demo.sdksample.utils.validation.CommonValidation.isStringNullOrEmpty;

import android.content.Context;
import android.widget.Spinner;

import com.zebra.rfid.demo.sdksample.components.clients.OrderClient;
import com.zebra.rfid.demo.sdksample.models.OrderInfo;
import com.zebra.rfid.demo.sdksample.utils.enums.ExpeditionTypeEnum;
import com.zebra.rfid.demo.sdksample.utils.wrappers.OrderWrapper;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final String TAG = "OrderService";

    private Context context;
    private OrderClient orderClient;

    public OrderService() {
    }

    public OrderService(Context context) {
        this.context = context;
        orderClient = new OrderClient(context);
    }

    public void addOrder(OrderWrapper orderWrapper) throws JSONException {
        this.orderClient.addOrder(orderWrapper);
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

    public void setAvailableOrders(Spinner orderSpinner) {
        this.orderClient.setAvailableOrders(orderSpinner);
    }
}
