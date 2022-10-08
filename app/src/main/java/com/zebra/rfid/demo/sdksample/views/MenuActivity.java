package com.zebra.rfid.demo.sdksample.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.services.AuthService;
import com.zebra.rfid.demo.sdksample.views.inventory.InventorySelectionActivity;
import com.zebra.rfid.demo.sdksample.views.itemlocation.ItemSelectionActivity;
import com.zebra.rfid.demo.sdksample.views.preparation.PreparationSelectionActivity;
import com.zebra.rfid.demo.sdksample.views.reception.ReceptionSelectionActivity;

public class MenuActivity extends AppCompatActivity {

    private Button receptionBtn, locateItemBtn, InventoryBtn, preparationBtn, LogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        instantiateVariables();
    }

    private void instantiateVariables() {
        receptionBtn = findViewById(R.id.receptionBtn);
        locateItemBtn = findViewById(R.id.locateItemBtn);
        InventoryBtn = findViewById(R.id.inventoryBtn);
        preparationBtn = findViewById(R.id.preparationBtn);
        LogoutBtn = findViewById(R.id.logoutBtn);

        locateItemBtn.setOnClickListener(event -> {
            goToActivity(ItemSelectionActivity.class);
        });

        preparationBtn.setOnClickListener(event -> {
            goToActivity(PreparationSelectionActivity.class);
        });

        receptionBtn.setOnClickListener(event -> {
            goToActivity(ReceptionSelectionActivity.class);
        });

        InventoryBtn.setOnClickListener(event -> {
            Intent intent = new Intent(this, InventorySelectionActivity.class);
            startActivity(intent);
        });

        LogoutBtn.setOnClickListener(event -> goToActivity(MainActivity.class));
    }

    private <T> void goToActivity(Class<T> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}