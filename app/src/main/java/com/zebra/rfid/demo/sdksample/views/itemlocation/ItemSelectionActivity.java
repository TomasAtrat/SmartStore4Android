package com.zebra.rfid.demo.sdksample.views.itemlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.models.Barcode;
import com.zebra.rfid.demo.sdksample.services.AuthService;
import com.zebra.rfid.demo.sdksample.services.OrderService;
import com.zebra.rfid.demo.sdksample.services.ProductService;

public class ItemSelectionActivity extends AppCompatActivity {

    private EditText barcodeTxt;
    private Button searchItemBtn;
    private ProductService productService;
    private OrderService orderService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selection);

        instantiateVariables();
    }

    private void instantiateVariables() {
        barcodeTxt = findViewById(R.id.barcodeTxt);

        searchItemBtn = findViewById(R.id.searchItemButton);

        searchItemBtn.setOnClickListener(this::searchItem);

        productService = new ProductService();
        orderService = new OrderService();
    }

    private void searchItem(View view) {
        Barcode barcode = new Barcode(barcodeTxt.getText().toString());

        productService.getBarcodeIfHasStock(barcode);

        //If theres stock then open the location activity

        //else ask if want to create order
    }
}