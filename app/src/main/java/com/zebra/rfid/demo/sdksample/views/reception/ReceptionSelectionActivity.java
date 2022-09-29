package com.zebra.rfid.demo.sdksample.views.reception;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.models.ReceptionList;
import com.zebra.rfid.demo.sdksample.services.ReceptionService;

public class ReceptionSelectionActivity extends AppCompatActivity {

    private static final String TAG = "InventorySelectionActivity";
    private ReceptionService receptionService;

    private Spinner receptionSpinner;
    private Button selectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception_selection);

        instantiateVariables();
    }

    private void instantiateVariables() {
        try {
            this.receptionService = new ReceptionService(this);

            selectButton = findViewById(R.id.selectReception);

            receptionSpinner = findViewById(R.id.receptionSpinner);
            this.receptionService.setAvailableReferences(receptionSpinner);

            selectButton.setOnClickListener(i-> performReception());

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(this, "Se produjo un error", Toast.LENGTH_LONG).show();
        }
    }

    private void performReception() {
        ReceptionList reception = (ReceptionList) this.receptionSpinner.getSelectedItem();
        Log.d(TAG, "selected reference: " + reception.getDescription());

//        Intent intent = new Intent(this, PerformInventoryActivity.class);
//        intent.putExtra(INVENTORY_OBJ, inventory);
//        startActivity(intent);
    }
}