package com.zebra.rfid.demo.sdksample.views.order;

import static com.zebra.rfid.demo.sdksample.utils.Constants.CUSTOMER_OBJ;
import static com.zebra.rfid.demo.sdksample.utils.Constants.LIST_OF_STOCK_OBJ;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.models.Customer;
import com.zebra.rfid.demo.sdksample.services.CustomerService;
import com.zebra.rfid.demo.sdksample.services.OrderService;
import com.zebra.rfid.demo.sdksample.utils.wrappers.ListOfStock;
import com.zebra.rfid.demo.sdksample.views.itemlocation.ItemSelectionActivity;

import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private EditText nameTxt, lastNameTxt, documentTxt, emailTxt, phoneTxt;
    private Button cancelBtn, continueBtn;
    private OrderService orderService;
    private CustomerService customerService;

    private Customer customer;
    private ListOfStock listOfStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        instantiateVariables();
    }

    private void instantiateVariables() {
        this.orderService = new OrderService(this);
        this.customerService = new CustomerService();

        listOfStock = (ListOfStock) getIntent().getSerializableExtra(LIST_OF_STOCK_OBJ);

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
        customer = new Customer(documentTxt.getText().toString(),
                nameTxt.getText().toString(),
                lastNameTxt.getText().toString(),
                emailTxt.getText().toString(),
                phoneTxt.getText().toString());

        List<String> errors = this.customerService.isCustomerValid(customer);

        if (errors.size() == 0) {
            Intent intent = new Intent(this, OrderFormStepTwoActivity.class);
            intent.putExtra(CUSTOMER_OBJ, customer);
            intent.putExtra(LIST_OF_STOCK_OBJ, listOfStock);
            startActivity(intent);
        } else {
            errors.forEach(i -> Toast.makeText(this, i, Toast.LENGTH_LONG).show());
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