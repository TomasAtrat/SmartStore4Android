package com.zebra.rfid.demo.sdksample.views.itemlocation;

import static com.zebra.rfid.demo.sdksample.utils.Constants.BARCODE_OBJ;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zebra.rfid.api3.TagData;
import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.components.epcschema.EPCSchemaStrategy;
import com.zebra.rfid.demo.sdksample.components.epcschema.SGTIN96Schema;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.RFIDHandler;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.RfidUseCase;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers.ResponseHandlerInterface;
import com.zebra.rfid.demo.sdksample.models.Barcode;

public class ItemLocationActivity extends AppCompatActivity implements ResponseHandlerInterface {

    private final String TAG = "ItemLocationActivity";

    private TextView readerStatus, barcodeTitle;

    private Barcode barcode;
    private RFIDHandler rfidHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_location);

        instantiateVariables();
    }

    private void instantiateVariables() {
        barcode = (Barcode) getIntent().getSerializableExtra(BARCODE_OBJ);

        readerStatus = findViewById(R.id.readerStatus4location);
        barcodeTitle = findViewById(R.id.barcodeSelectedTxt);

        barcodeTitle.setText(barcodeTitle.getText().toString() + barcode.getId());

        rfidHandler = new RFIDHandler();
        rfidHandler.onCreate(this, this, RfidUseCase.SEARCH_ITEM, readerStatus);

        //Get any SGTIN by barcode and perform locate by that tag

    }


    @Override
    public void handleTagdata(TagData[] tagData) {


    }

    @Override
    public void handleTriggerPress(boolean pressed) {

    }
}