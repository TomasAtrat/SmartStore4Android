package com.zebra.rfid.demo.sdksample.views.itemlocation;

import static com.zebra.rfid.demo.sdksample.utils.Constants.BARCODE_OBJ;
import static java.lang.Double.parseDouble;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.zebra.rfid.api3.TagData;
import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.RFIDHandler;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.RfidUseCase;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers.ResponseHandlerInterface;
import com.zebra.rfid.demo.sdksample.models.Barcode;
import com.zebra.rfid.demo.sdksample.models.EpcBarcode;
import com.zebra.rfid.demo.sdksample.services.ProductService;

import java.util.List;

public class ItemLocationActivity extends AppCompatActivity implements ResponseHandlerInterface {

    private final String TAG = "ItemLocationActivity";

    public static List<EpcBarcode> listOfTenEpc;

    private TextView readerStatus, barcodeTitle;
    private HalfGauge distanceIndicator;

    private ProductService productService;

    private Barcode barcode;
    private RFIDHandler rfidHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_item_location);

            instantiateVariables();

            rfidHandler = new RFIDHandler();
            rfidHandler.onCreate(this, this, RfidUseCase.SEARCH_ITEM, readerStatus);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void instantiateVariables() {
        productService = new ProductService(this);

        barcode = (Barcode) getIntent().getSerializableExtra(BARCODE_OBJ);

        distanceIndicator = findViewById(R.id.distanceIndicator);
        setUpDistanceIndicator();

        readerStatus = findViewById(R.id.readerStatus4location);
        barcodeTitle = findViewById(R.id.barcodeSelectedTxt);

        barcodeTitle.setText(barcodeTitle.getText().toString() + barcode.getId());

        productService.getTopTenEpcByBarcodeAsync(barcode);
    }

    private void setUpDistanceIndicator() {
        Range range1 = new Range();
        Range range2 = new Range();
        Range range3 = new Range();

        range1.setColor(Color.parseColor("#FA7500"));
        range1.setFrom(0);
        range1.setTo(33);

        range2.setColor(Color.parseColor("#E6C910"));
        range2.setFrom(34);
        range2.setTo(65);

        range3.setColor(Color.parseColor("#4DE036"));
        range3.setFrom(66);
        range3.setTo(100);

        distanceIndicator.addRange(range1);
        distanceIndicator.addRange(range2);
        distanceIndicator.addRange(range3);

        distanceIndicator.setMinValue(0);
        distanceIndicator.setMaxValue(100);
        distanceIndicator.setValue(0);
    }


    @Override
    public void handleTagdata(TagData[] tagData) {
        for (TagData tagDatum : tagData) {
            if (tagDatum.isContainsLocationInfo()) {
                Log.d(TAG, String.valueOf(tagDatum.LocationInfo.getRelativeDistance()));
                runOnUiThread(() -> {
                    double relativeDistance = parseDouble(String.valueOf(tagDatum.LocationInfo.getRelativeDistance()));
                    distanceIndicator.setValue(relativeDistance);
                });
            }
        }
    }

    @Override
    public void handleTriggerPress(boolean pressed) {
        if (pressed)
            rfidHandler.executeRfidAction(new String[]{listOfTenEpc.stream().findAny().get().getId()});
        else {
            rfidHandler.stopRfidAction();
            runOnUiThread(() -> {
                distanceIndicator.setValue(0);
            });
        }
    }
}