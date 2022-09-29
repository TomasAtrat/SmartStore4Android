package com.zebra.rfid.demo.sdksample.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.zebra.rfid.demo.sdksample.R;
import com.zebra.rfid.demo.sdksample.services.AuthService;

public class MainActivity extends AppCompatActivity /*implements RFIDHandler.ResponseHandlerInterface*/ {

    private static final String TAG = "MainActivity";
    private Button loginBtn;
    private EditText usernameField, passwordField;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instantiateVariables();

    }

    private void instantiateVariables() {
        authService = new AuthService(this);
        loginBtn = findViewById(R.id.login);
        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        loginBtn.setOnClickListener(event -> login());
    }

    private void login() {
        try {
            authService.login(usernameField.getText().toString(), passwordField.getText().toString());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
