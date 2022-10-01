package com.zebra.rfid.demo.sdksample.views.order;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.models.Customer;
import com.zebra.rfid.demo.sdksample.services.CustomerService;
import com.zebra.rfid.demo.sdksample.services.OrderService;
import com.zebra.rfid.demo.sdksample.views.itemlocation.ItemSelectionActivity;

public class OrderActivity extends AppCompatActivity {

    private EditText nameTxt, lastNameTxt, documentTxt, emailTxt, phoneTxt;
    private Button cancelBtn, continueBtn;
    private OrderService orderService;
    private CustomerService customerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        instantiateVariables();
    }

    private void instantiateVariables() {
        this.orderService = new OrderService();
        this.customerService = new CustomerService(this);

        nameTxt = findViewById(R.id.nameTxt);
        lastNameTxt = findViewById(R.id.lastNameTxt);
        documentTxt = findViewById(R.id.documentTxt);
        emailTxt = findViewById(R.id.emailTxt);
        phoneTxt = findViewById(R.id.phoneTxt);

        cancelBtn = findViewById(R.id.cancelButtonReception1);
        continueBtn = findViewById(R.id.continueBtnReception1);

        cancelBtn.setOnClickListener(i -> backToSearchItemActivity());
        continueBtn.setOnClickListener(i -> goToNextStepIfValid());
    }

    private void goToNextStepIfValid() {
        Customer customer = new Customer(documentTxt.getText().toString(),
                nameTxt.getText().toString(),
                lastNameTxt.getText().toString(),
                emailTxt.getText().toString(),
                phoneTxt.getText().toString());

        if (this.customerService.isCustomerValid(customer)){
            //Intent intent =

        }


    }

    private void backToSearchItemActivity() {
        new AlertDialog.Builder(this)
                .setTitle("Cambios sin guardar")
                .setMessage("¿Estás seguro que quieres volver atrás?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    Intent intent = new Intent(this, ItemSelectionActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.CancelButton, null).show();
    }
}