package com.zebra.rfid.demo.sdksample.views.reception;

import static com.zebra.rfid.demo.sdksample.utils.Constants.RECEPTION_OBJ;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zebra.rfid.api3.TagData;
import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.components.epcschema.EPCSchemaStrategy;
import com.zebra.rfid.demo.sdksample.components.epcschema.SGTIN96Schema;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.RFIDHandler;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.RfidUseCase;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers.ResponseHandlerInterface;
import com.zebra.rfid.demo.sdksample.models.Barcode;
import com.zebra.rfid.demo.sdksample.models.ReceptionDetail;
import com.zebra.rfid.demo.sdksample.models.ReceptionList;
import com.zebra.rfid.demo.sdksample.services.ReceptionService;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ReceptionActivity extends AppCompatActivity implements ResponseHandlerInterface {
    private static final String TAG = "ReceptionActivity";

    private LinkedList<String> barcodes;
    private ReceptionService receptionService;

    private ReceptionList reception;

    private ListView detailsList;
    private RFIDHandler rfidHandler;
    private TextView readerStatus;
    private Button saveReception;

    public static HashMap<ReceptionDetail, Integer> counterByBarcode;
    public static ArrayAdapter<ReceptionDetail> adapter;
    public static List<ReceptionDetail> details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);

        instantiateVariables();

        rfidHandler = new RFIDHandler();
        rfidHandler.onCreate(this, this, RfidUseCase.RECEPTION, readerStatus);
    }

    private void instantiateVariables() {
        this.receptionService = new ReceptionService(this);

        reception = (ReceptionList) getIntent().getSerializableExtra(RECEPTION_OBJ);
        reception.setStartingDate(new Date(System.currentTimeMillis()));

        readerStatus = findViewById(R.id.readerStatus4reception);

        barcodes = new LinkedList<>();

        Log.d(TAG, reception.getDescription());

        saveReception = findViewById(R.id.saveReception);
        saveReception.setOnClickListener(e -> {
            reloadList();
        });

        TextView title = findViewById(R.id.receptionNumber);
        title.setText("Referencia # " + reception.getId() + " - " + reception.getDescription());

        detailsList = findViewById(R.id.detailsList);

        this.receptionService.setReceptionDetails(reception.getId(), detailsList);
    }

    private void reloadList() {
        details.forEach(i -> {
            i.setReceivedQty(counterByBarcode.get(i));
            i.setReceptionList(reception);
        });

        this.receptionService.saveReception(reception, details);
    }

    @Override
    public void handleTagdata(TagData[] tagData) {
        EPCSchemaStrategy epcSchema = new SGTIN96Schema();
        for (TagData tagDatum : tagData) {
            if (tagDatum.getTagSeenCount() <= 1)
                updateReception(epcSchema, tagDatum);
        }
    }

    private void updateReception(EPCSchemaStrategy epcSchema, TagData tagDatum) {
        try {
            Barcode barcode = epcSchema.getBarcodeFromEPC(tagDatum.getTagID());

            Optional<ReceptionDetail> detail = details.stream().filter(i -> i.getBarcode().getId().equals(barcode.getId())).findFirst();

            if (detail.isPresent()) {
                int value = counterByBarcode.get(detail.get());
                counterByBarcode.put(detail.get(), value + 1);
            }
            Log.d(TAG, "Barcode retrieved : " + barcode.getId());

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void handleTriggerPress(boolean pressed) {
        if (pressed) handleActionWhenTriggerIsPressed();

        else handleActionWhenTriggerNotPressed();
    }

    private void handleActionWhenTriggerIsPressed() {
        rfidHandler.executeRfidAction();
    }

    private void handleActionWhenTriggerNotPressed() {
        rfidHandler.stopRfidAction();
    }

}