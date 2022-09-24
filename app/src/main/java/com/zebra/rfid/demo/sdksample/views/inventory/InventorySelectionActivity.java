package com.zebra.rfid.demo.sdksample.views.inventory;

import static com.zebra.rfid.demo.sdksample.utils.Constants.INVENTORY_OBJ;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.models.Inventory;
import com.zebra.rfid.demo.sdksample.services.InventoryService;

public class InventorySelectionActivity extends AppCompatActivity {
    private static final String TAG = "InventorySelectionActivity";
    private InventoryService inventoryService;

    private Spinner inventorySpinner;
    private Button selectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inventory_selection);

        instantiateVariables();

    }

    private void instantiateVariables() {
        try {
            this.inventoryService = new InventoryService(this);

            selectButton = findViewById(R.id.selectInventory);

            inventorySpinner = findViewById(R.id.inventorySpinner);
            this.inventoryService.setAvailableInventories(inventorySpinner);

            selectButton.setOnClickListener(i-> performInventory());

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(this, "Se produjo un error", Toast.LENGTH_LONG).show();
        }
    }

    private void performInventory() {
        Inventory inventory = (Inventory) this.inventorySpinner.getSelectedItem();
        Log.d(TAG, "selected item: " + inventory.getDescription());
        Intent intent = new Intent(this, PerformInventoryActivity.class);
        intent.putExtra(INVENTORY_OBJ, inventory);
        startActivity(intent);
    }
}