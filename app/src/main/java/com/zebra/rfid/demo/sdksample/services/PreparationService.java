package com.zebra.rfid.demo.sdksample.services;

import android.content.Context;

import com.zebra.rfid.demo.sdksample.components.clients.PreparationClient;
import com.zebra.rfid.demo.sdksample.models.PreparationDetail;

import java.util.ArrayList;
import java.util.List;

public class PreparationService {

    private Context context;
    private PreparationClient preparationClient;

    public PreparationService(Context context) {
        this.context = context;
        preparationClient = new PreparationClient(context);
    }

    public List<String> validatePreparedDetail(PreparationDetail detail, String barcode, int preparedQty) {
        List<String> errors = new ArrayList<>();

        if (detail.getBarcode().getId().equalsIgnoreCase((barcode)))
            errors.add("El código de barras es incorrecto");

        if (preparedQty > detail.getOrderedQty())
            errors.add("No se puede preparar una cantidad mayor a la establecida de " + detail.getOrderedQty());

        return errors;
    }

    public void fetchPreparationDetailsAsync(Long orderId) {
        preparationClient.fetchPreparationDetailsAsync(orderId);
    }
}
