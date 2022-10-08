package com.zebra.rfid.demo.sdksample.views.preparation;

import static com.zebra.rfid.demo.sdksample.utils.Constants.ORDER_INFO_OBJ;

import static java.lang.Double.parseDouble;
import static java.lang.String.format;
import static java.lang.System.err;
import static java.lang.System.lineSeparator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.zebra.rfid.api3.TagData;
import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.RFIDHandler;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.RfidUseCase;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers.ResponseHandlerInterface;
import com.zebra.rfid.demo.sdksample.models.EpcBarcode;
import com.zebra.rfid.demo.sdksample.models.OrderDetail;
import com.zebra.rfid.demo.sdksample.models.OrderInfo;
import com.zebra.rfid.demo.sdksample.models.PreparationDetail;
import com.zebra.rfid.demo.sdksample.services.PreparationService;
import com.zebra.rfid.demo.sdksample.views.itemlocation.ItemSelectionActivity;

import java.util.Date;
import java.util.List;

public class PreparationActivity extends AppCompatActivity implements ResponseHandlerInterface {

    private final String TAG = "PreparationActivity";
    private final String TITLE_FORMAT = "Código de barras: %s, %s Cantidad preparar %d";

    private Button backBtn, continueBtn;
    private TextView readerStatus, barcodeTitle;
    private HalfGauge distanceIndicator;
    private EditText barcodeReadTxt, preparedQtyTxt;
    public static EditText productDescriptionTxt;

    private OrderInfo orderInfo;
    public static List<EpcBarcode> listOfTenEpc;
    public static List<PreparationDetail> preparationDetails;
    private PreparationDetail currentDetail;
    private int detailsIterator;

    private RFIDHandler rfidHandler;
    private PreparationService preparationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_preparation);

            //Need 10 tags per product
            instantiateVariables();

            fetchPreparationDetailsAsync(orderInfo.getId());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void fetchPreparationDetailsAsync(Long orderId) {
        this.preparationService.fetchPreparationDetailsAsync(orderId);
    }

    private void instantiateVariables() {
        preparationService = new PreparationService(this);

        orderInfo = (OrderInfo) getIntent().getSerializableExtra(ORDER_INFO_OBJ);

        detailsIterator = 0;

        readerStatus = findViewById(R.id.readerStatus4preparation);
        barcodeTitle = findViewById(R.id.barcodeSearched);

        productDescriptionTxt = findViewById(R.id.productDescriptionTxt);
        barcodeReadTxt = findViewById(R.id.barcode4PrepTxt);
        preparedQtyTxt = findViewById(R.id.preparedQtyTxt);

        continueBtn = findViewById(R.id.acceptPreparedProductBtn);
        backBtn = findViewById(R.id.goBackToOrderSelectionBtn);

        continueBtn.setOnClickListener(e -> prepareNextProduct());
        backBtn.setOnClickListener(e -> goBack());

        distanceIndicator = findViewById(R.id.distanceIndicator4preparation);
        setUpDistanceIndicator();

        rfidHandler = new RFIDHandler();
        rfidHandler.onCreate(this, this, RfidUseCase.SEARCH_ITEM, readerStatus);
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

    private void goBack() {
        new AlertDialog.Builder(this)
                .setTitle("Volver atrás")
                .setMessage("¿Estás seguro que quieres volver atrás?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    Intent intent = new Intent(this, PreparationSelectionActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.CancelButton, null).show();
    }

    private void prepareNextProduct() {
        int preparedQty = Integer.parseInt(preparedQtyTxt.getText().toString());

        List<String> errors = preparationService.validatePreparedDetail(currentDetail,
                barcodeReadTxt.getText().toString(),
                preparedQty);

        if (errors != null && errors.size() > 0)
            errors.forEach(i -> Toast.makeText(this, i, Toast.LENGTH_LONG).show());
        else {
            saveCurrentDetail(preparedQty);
            continueWithNextDetail();
        }
    }

    private void saveCurrentDetail(int preparedQty) {
        preparationDetails.get(detailsIterator).setUpdateDate(new Date());
        preparationDetails.get(detailsIterator).setPreparedQty(preparedQty);
    }

    private void continueWithNextDetail() {
        detailsIterator++;

        currentDetail = this.preparationDetails.get(detailsIterator);

        String title = format(TITLE_FORMAT,
                currentDetail.getBarcode().getId(),
                lineSeparator(),
                currentDetail.getOrderedQty());

        barcodeTitle.setText(title);

        //It means that it comes the next item
        //Look for ten epc related to that barcode
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

    }
}