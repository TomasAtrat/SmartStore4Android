package com.zebra.rfid.demo.sdksample.views.inventory;

import static com.zebra.rfid.demo.sdksample.utils.Constants.INVENTORY_OBJ;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zebra.rfid.api3.TagData;
import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.components.epcschema.EPCSchemaStrategy;
import com.zebra.rfid.demo.sdksample.components.epcschema.SGTIN96Schema;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.RFIDHandler;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers.ResponseHandlerInterface;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.RfidUseCase;
import com.zebra.rfid.demo.sdksample.models.Barcode;
import com.zebra.rfid.demo.sdksample.models.Inventory;
import com.zebra.rfid.demo.sdksample.models.InventoryDetail;
import com.zebra.rfid.demo.sdksample.models.InventoryProblem;
import com.zebra.rfid.demo.sdksample.services.AuthService;
import com.zebra.rfid.demo.sdksample.services.InventoryService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PerformInventoryActivity extends AppCompatActivity implements ResponseHandlerInterface {

    private static final String TAG = "PerformInventoryActivity";


    private LinkedList<String> barcodes;
    private InventoryService inventoryService;

    private Inventory inventory;

    private ListView detailsList;
    private RFIDHandler rfidHandler;
    private TextView readerStatus;
    private Button saveInventory;

    public static HashMap<InventoryDetail, Integer> counterByBarcode;
    public static ArrayAdapter<InventoryDetail> adapter;
    public static List<InventoryDetail> details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_perform_inventory);

            instantiateVariables();

            rfidHandler = new RFIDHandler();
            rfidHandler.onCreate(this, this, RfidUseCase.INVENTORY, readerStatus);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(this, "Se produjo un error", Toast.LENGTH_LONG).show();
        }
    }

    private void instantiateVariables() {
        this.inventoryService = new InventoryService(this);

        inventory = (Inventory) getIntent().getSerializableExtra(INVENTORY_OBJ);
        inventory.setStartingDate(new Date(System.currentTimeMillis()));
        inventory.setUserAssigned(AuthService.userInfo);

        readerStatus = findViewById(R.id.readerStatus);

        barcodes = new LinkedList<>();

        Log.d(TAG, inventory.getDescription());

        saveInventory = findViewById(R.id.saveInventory);
        saveInventory.setOnClickListener(e -> {
            reloadList();

        });

        TextView title = findViewById(R.id.inventoryNumber);
        title.setText("Inventario # " + inventory.getId() + " - " + inventory.getDescription());

        detailsList = findViewById(R.id.detailsList);

        this.inventoryService.setInventoryDetails(inventory.getId(), detailsList);
    }

    //region ResponseHandlerInterface
    @Override
    public void handleTagdata(TagData[] tagData) {
        EPCSchemaStrategy epcSchema = new SGTIN96Schema();
        for (TagData tagDatum : tagData) {
            if (tagDatum.getTagSeenCount() <= 1)
                updateInventory(epcSchema, tagDatum);
        }
    }

    private void updateInventory(EPCSchemaStrategy epcSchema, TagData tagDatum) {
        try {
            Barcode barcode = epcSchema.getBarcodeFromEPC(tagDatum.getTagID());

            Optional<InventoryDetail> detail = details.stream().filter(i -> i.getBarcode().getId().equals(barcode.getId())).findFirst();

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

    private void reloadList() {
        details.forEach(i -> {
            i.setReadQty(counterByBarcode.get(i));
            i.setInventory(inventory);
        });
        this.inventoryService.saveInventory(inventory, details);
    }

    //endregion

    //region Application Lifecycle

    @Override
    protected void onPause() {
        super.onPause();
        rfidHandler.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        String status = rfidHandler.onResume();
        readerStatus.setText(status);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rfidHandler.onDestroy();
    }

    //endregion
}
