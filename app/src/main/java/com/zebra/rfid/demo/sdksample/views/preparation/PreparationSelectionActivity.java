package com.zebra.rfid.demo.sdksample.views.preparation;

import static com.zebra.rfid.demo.sdksample.utils.Constants.ORDER_INFO_OBJ;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.models.OrderInfo;
import com.zebra.rfid.demo.sdksample.services.OrderService;
import com.zebra.rfid.demo.sdksample.views.reception.ReceptionActivity;

public class PreparationSelectionActivity extends AppCompatActivity {

    private final String TAG = "PreparationSelectionActivity";

    private Spinner orderSpinner;
    private Button prepareOrderBtn;

    private OrderService orderService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_preparation_selection);

            instantiateVariables();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    private void instantiateVariables() {
        orderService = new OrderService(this);

        orderSpinner = findViewById(R.id.orderSpinner);
        this.orderService.setAvailableOrders(orderSpinner);

        prepareOrderBtn = findViewById(R.id.selectOrder);
        prepareOrderBtn.setOnClickListener(i-> prepareOrder());
    }

    private void prepareOrder() {
        OrderInfo orderInfo = (OrderInfo) this.orderSpinner.getSelectedItem();

        Intent intent = new Intent(this, PreparationActivity.class);
        intent.putExtra(ORDER_INFO_OBJ, orderInfo);
        startActivity(intent);
    }

}