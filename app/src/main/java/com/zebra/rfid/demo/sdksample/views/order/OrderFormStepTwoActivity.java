package com.zebra.rfid.demo.sdksample.views.order;

import static com.zebra.rfid.demo.sdksample.utils.Constants.CUSTOMER_OBJ;
import static com.zebra.rfid.demo.sdksample.utils.Constants.LIST_OF_STOCK_OBJ;
import static com.zebra.rfid.demo.sdksample.utils.Constants.ORDER_WRAPPER_OBJ;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.models.Barcode;
import com.zebra.rfid.demo.sdksample.models.Branch;
import com.zebra.rfid.demo.sdksample.models.Customer;
import com.zebra.rfid.demo.sdksample.models.ExpeditionType;
import com.zebra.rfid.demo.sdksample.models.OrderDetail;
import com.zebra.rfid.demo.sdksample.models.OrderInfo;
import com.zebra.rfid.demo.sdksample.models.Stock;
import com.zebra.rfid.demo.sdksample.services.ExpeditionService;
import com.zebra.rfid.demo.sdksample.services.OrderService;
import com.zebra.rfid.demo.sdksample.utils.enums.ExpeditionTypeEnum;
import com.zebra.rfid.demo.sdksample.utils.wrappers.ListOfStock;
import com.zebra.rfid.demo.sdksample.utils.wrappers.OrderWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderFormStepTwoActivity extends AppCompatActivity {

    private final String TAG = "OrderFormStepTwoActivity";

    private EditText barcodeTxt, productTxt, addressQty, orderedQtyTxt;
    private CheckBox acceptsPartialExpeditionCheckBox;
    private Spinner branchSpinner, expeditionTypeSpinner;
    private Button goBackBtn, anexosBtn, saveBtn;

    private OrderService orderService;

    private Customer customer;
    private ListOfStock listOfStock;
    private OrderInfo orderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form_step_two);

        instantiateVariables();
        populateDefaultValues();
        setButtonActions();
    }

    private void setButtonActions() {
        saveBtn.setOnClickListener(i -> saveOrder());
        anexosBtn.setOnClickListener(i -> goToAnexosActivity());
        goBackBtn.setOnClickListener(i -> backToStepOne());
    }

    private void populateDefaultValues() {
        ExpeditionService expeditionService = new ExpeditionService(this);

        Optional<Stock> stock = listOfStock.getStockList().stream().findAny();
        stock.ifPresent(value -> {
            barcodeTxt.setText(value.getBarcodeBarcode().getId());
            productTxt.setText(value.getBarcodeBarcode().getProduct().getDescription());
        });

        expeditionService.populateExpeditionTypeSpinner(expeditionTypeSpinner);

        populateBranchSpinner();

        expeditionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((ExpeditionType) adapterView.getSelectedItem()).getId().equals(ExpeditionTypeEnum.SEND_TO_ADDRESS.getValue()))
                    addressQty.setEnabled(true);
                else
                    addressQty.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    private void instantiateVariables() {
        this.orderService = new OrderService(this);

        customer = (Customer) getIntent().getSerializableExtra(CUSTOMER_OBJ);
        listOfStock = (ListOfStock) getIntent().getSerializableExtra(LIST_OF_STOCK_OBJ);

        barcodeTxt = findViewById(R.id.barcodeOrderedTxt);
        productTxt = findViewById(R.id.productOrderedTxt);

        addressQty = findViewById(R.id.addressTxt);
        orderedQtyTxt = findViewById(R.id.orderedQtyTxt);

        acceptsPartialExpeditionCheckBox = findViewById(R.id.acceptsPartialExpeditionTxt);

        expeditionTypeSpinner = findViewById(R.id.expeditionTypeSpinner);

        branchSpinner = findViewById(R.id.branchSpinner);

        goBackBtn = findViewById(R.id.cancelButtonReception2);
        anexosBtn = findViewById(R.id.anexosBtn);
        saveBtn = findViewById(R.id.acceptBtnReception2);
    }

    private void populateBranchSpinner() {
        List<Branch> branches = new ArrayList<>();
        listOfStock.getStockList().forEach(i -> branches.add(i.getBranch()));

        Set<Branch> uniqueBranches = new HashSet<>(branches);

        ArrayAdapter<Branch> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new ArrayList<>(uniqueBranches));

        branchSpinner.setAdapter(adapter);
    }

    private OrderInfo getOrderFromForm() {
        return new OrderInfo(new Date(),
                addressQty.getText().toString(),
                acceptsPartialExpeditionCheckBox.isChecked(),
                "", "", "", "",
                (ExpeditionType) expeditionTypeSpinner.getSelectedItem(),
                (Branch) branchSpinner.getSelectedItem(),
                customer);
    }

    private OrderDetail getOrderDetailFromForm() {
        return new OrderDetail(Integer.parseInt(orderedQtyTxt.getText().toString()),
                0,
                new Barcode(barcodeTxt.getText().toString()),
                orderInfo);
    }

    private void saveOrder() {
        orderInfo = getOrderFromForm();

        OrderDetail detail = getOrderDetailFromForm();

        List<String> errors = this.orderService.isOrderValid(orderInfo);
        try {
            if (errors.size() == 0) {
                OrderWrapper orderWrapper = new OrderWrapper(orderInfo, Stream.of(detail).collect(Collectors.toList()));
                this.orderService.addOrder(orderWrapper);
            } else errors.forEach(i -> Toast.makeText(this, i, Toast.LENGTH_LONG).show());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void goToAnexosActivity() {
        orderInfo = getOrderFromForm();

        OrderDetail detail = getOrderDetailFromForm();

        Intent intent = new Intent(this, AnexosActivity.class);
        intent.putExtra(ORDER_WRAPPER_OBJ, new OrderWrapper(orderInfo, Stream.of(detail).collect(Collectors.toList())));
        intent.putExtra(LIST_OF_STOCK_OBJ, listOfStock);
        startActivity(intent);
    }

    private void backToStepOne() {
        new AlertDialog.Builder(this)
                .setTitle("Cambios sin guardar")
                .setMessage("¿Estás seguro que quieres volver atrás?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    Intent intent = new Intent(this, OrderActivity.class);
                    intent.putExtra(LIST_OF_STOCK_OBJ, listOfStock);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.CancelButton, null).show();
    }
}