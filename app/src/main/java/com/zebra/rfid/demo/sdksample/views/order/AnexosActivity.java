package com.zebra.rfid.demo.sdksample.views.order;

import static com.zebra.rfid.demo.sdksample.utils.Constants.CUSTOMER_OBJ;
import static com.zebra.rfid.demo.sdksample.utils.Constants.LIST_OF_STOCK_OBJ;
import static com.zebra.rfid.demo.sdksample.utils.Constants.ORDER_WRAPPER_OBJ;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.models.OrderInfo;
import com.zebra.rfid.demo.sdksample.services.OrderService;
import com.zebra.rfid.demo.sdksample.utils.wrappers.ListOfStock;
import com.zebra.rfid.demo.sdksample.utils.wrappers.OrderWrapper;

import org.json.JSONException;

public class AnexosActivity extends AppCompatActivity {

    private final String TAG = "AnexosActivity";

    private EditText anexo1Txt, anexo2Txt, anexo3Txt, anexo4Txt;
    private Button goBackBtn, saveBtn;

    private OrderService orderService;

    private OrderWrapper orderWrapper;
    private ListOfStock listOfStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anexos);

        instantiateVariables();
        setButtonActions();
    }

    private void instantiateVariables() {
        this.orderService = new OrderService(this);

        orderWrapper = (OrderWrapper) getIntent().getSerializableExtra(ORDER_WRAPPER_OBJ);
        listOfStock = (ListOfStock) getIntent().getSerializableExtra(LIST_OF_STOCK_OBJ);

        anexo1Txt = findViewById(R.id.anexo1Txt);
        anexo2Txt = findViewById(R.id.anexo2Txt);
        anexo3Txt = findViewById(R.id.anexo3Txt);
        anexo4Txt = findViewById(R.id.anexo4Txt);

        goBackBtn = findViewById(R.id.cancelButtonReceptionAnexo);
        saveBtn = findViewById(R.id.acceptBtnReceptionAnexo);
    }

    private void setButtonActions() {
        goBackBtn.setOnClickListener(i -> backToStepTwo());
        saveBtn.setOnClickListener(i -> saveOrder());
    }

    private void saveOrder() {
        OrderInfo orderInfo = orderWrapper.getOrderInfo();
        orderInfo.setDescription1(anexo1Txt.getText().toString());
        orderInfo.setDescription2(anexo2Txt.getText().toString());
        orderInfo.setDescription3(anexo3Txt.getText().toString());
        orderInfo.setDescription4(anexo4Txt.getText().toString());

        orderWrapper.getOrderDetailList().forEach(i -> i.setOrderInfo(orderInfo));
        orderWrapper.setOrderInfo(orderInfo);

        try {
            this.orderService.addOrder(orderWrapper);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void backToStepTwo() {
        new AlertDialog.Builder(this)
                .setTitle("Cambios sin guardar")
                .setMessage("¿Estás seguro que quieres volver atrás?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    Intent intent = new Intent(this, OrderFormStepTwoActivity.class);
                    intent.putExtra(LIST_OF_STOCK_OBJ, listOfStock);
                    intent.putExtra(CUSTOMER_OBJ, orderWrapper.getOrderInfo().getCustomer());
                    startActivity(intent);
                })
                .setNegativeButton(R.string.CancelButton, null).show();
    }
}
