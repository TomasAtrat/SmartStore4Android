package com.zebra.rfid.demo.sdksample.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.services.AuthService;
import com.zebra.rfid.demo.sdksample.views.inventory.InventorySelectionActivity;

public class MenuActivity extends AppCompatActivity {

    private Button receptionBtn, InventoryBtn, LogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        instantiateVariables();
    }

    private void instantiateVariables() {
        receptionBtn = findViewById(R.id.receptionBtn);
        InventoryBtn = findViewById(R.id.inventoryBtn);
        LogoutBtn = findViewById(R.id.logoutBtn);

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