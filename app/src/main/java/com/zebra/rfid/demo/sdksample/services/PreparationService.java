package com.zebra.rfid.demo.sdksample.services;

import android.content.Context;
import android.util.Log;

import com.zebra.rfid.demo.sdksample.components.clients.PreparationClient;
import com.zebra.rfid.demo.sdksample.models.Barcode;
import com.zebra.rfid.demo.sdksample.models.Preparation;
import com.zebra.rfid.demo.sdksample.models.PreparationDetail;
import com.zebra.rfid.demo.sdksample.utils.wrappers.PreparationWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PreparationService {

    private final String TAG = "PreparationService";

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

    public void getTopTenEpcByBarcodeAsync(Barcode barcode) {
        preparationClient.getTopTenEpcByBarcodeAsync(barcode);
    }

    public void savePreparation(List<PreparationDetail> details) {
        try {
            Preparation preparation = details.stream().findAny().get().getPreparation();
            preparation.setUpdateDate(new Date());

            if (details.stream().allMatch(i -> i.getOrderedQty().equals(i.getPreparedQty()))) {
                preparation.setEndingDate(new Date());
                preparation.setFinished(true);
                preparation.setHasProblems(false);
            }else{
                preparation.setHasProblems(true);
                preparation.setFinished(false);
            }

            PreparationWrapper wrapper = new PreparationWrapper(preparation, details);

            preparationClient.savePreparationAndDetailsAsync(wrapper);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
