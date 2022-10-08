package com.zebra.rfid.demo.sdksample.views.preparation;

import static com.zebra.rfid.demo.sdksample.utils.Constants.PREPARATION_WRAPPER_OBJ;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zebra.rfid.api3.RFIDReader;
import com.zebra.rfid.api3.TagData;
import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.components.epcschema.EPCSchemaStrategy;
import com.zebra.rfid.demo.sdksample.components.epcschema.SGTIN96Schema;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.RFIDHandler;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.RfidUseCase;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers.ResponseHandlerInterface;
import com.zebra.rfid.demo.sdksample.models.Barcode;
import com.zebra.rfid.demo.sdksample.models.PreparationDetail;
import com.zebra.rfid.demo.sdksample.services.PreparationService;
import com.zebra.rfid.demo.sdksample.utils.data.PreparationProblems;
import com.zebra.rfid.demo.sdksample.utils.wrappers.PreparationWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class PreparationValidationActivity extends AppCompatActivity implements ResponseHandlerInterface {

    private final String TAG = "PreparationValidationActivity";

    private PreparationService preparationService;

    private TextView readerStatus;
    private Button acceptBtn;
    private ListView problemsView;

    private List<PreparationDetail> details;
    private HashMap<PreparationDetail, Integer> qtyPreparedByDetail;
    private List<PreparationProblems> problems;
    private RFIDHandler rfidHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparation_validation);

        instantiateVariables();

        rfidHandler = new RFIDHandler();

        RFIDReader reader = RFIDHandler.getReader();

        if (reader == null || reader.isConnected())
            rfidHandler.onCreate(this, this, RfidUseCase.INVENTORY, readerStatus);
        else {
            Log.d(TAG, "Reader stills alive!");
            rfidHandler.resetReaderConfig(RfidUseCase.INVENTORY);
        }
    }

    private void instantiateVariables() {
        preparationService = new PreparationService(this);

        problems = new ArrayList<>();

        readerStatus = findViewById(R.id.readerStatus4prepValidation);
        problemsView = findViewById(R.id.problemsList);
        acceptBtn = findViewById(R.id.savePreparation);

        problemsView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, problems));

        PreparationWrapper wrapper = (PreparationWrapper) getIntent().getSerializableExtra(PREPARATION_WRAPPER_OBJ);

        details = wrapper.getDetails();

        qtyPreparedByDetail = new HashMap<>();

        details.forEach(i -> qtyPreparedByDetail.put(i, 0));

        acceptBtn.setOnClickListener(e -> savePreparation());
    }

    private void savePreparation() {
        new AlertDialog.Builder(this)
                .setTitle("Guardar cambios")
                .setMessage("Al aceptar confirmas que has comprobado las diferencias generadas")
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    preparationService.savePreparation(details);
                })
                .setNegativeButton(R.string.CancelButton, null).show();
    }


    @Override
    public void handleTagdata(TagData[] tagData) {
        EPCSchemaStrategy epcSchema = new SGTIN96Schema();
        for (TagData tagDatum : tagData) {
            updateQtyPreparedByBarcode(tagDatum, epcSchema);
        }
    }

    private void updateQtyPreparedByBarcode(TagData tagDatum, EPCSchemaStrategy epcSchema) {
        try {
            Barcode barcode = epcSchema.getBarcodeFromEPC(tagDatum.getTagID());

            Optional<PreparationDetail> detail = details.stream()
                    .filter(i -> i.getBarcode().getId().equals(barcode.getId()))
                    .findAny();

            if (detail.isPresent()) {
                int value = qtyPreparedByDetail.containsKey(detail.get())
                        ? qtyPreparedByDetail.get(detail.get())
                        : 0;

                qtyPreparedByDetail.put(detail.get(), value + 1);
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void handleTriggerPress(boolean pressed) {
        if (pressed) rfidHandler.executeRfidAction(null);

        else {
            rfidHandler.stopRfidAction();
            updateProblemsList();
        }
    }

    private void updateProblemsList() {
        qtyPreparedByDetail.forEach((detail, integer) -> {
            int realQty = qtyPreparedByDetail.get(detail);
            Optional<PreparationDetail> det = details.stream()
                    .filter(i -> i.getId().equals(detail.getId()))
                    .findFirst();

            int supposedQty = det.isPresent() ? 0 : det.get().getPreparedQty();

            if (realQty != supposedQty)
                problems.add(new PreparationProblems(detail.getBarcode(),
                        supposedQty,
                        realQty,
                        Math.abs(supposedQty - realQty)));
        });

        runOnUiThread(() -> {
            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, problems);
            problemsView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
    }
}