package com.example.myshoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText edName, edPhone, edPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edName = (EditText) findViewById(R.id.register_name_input);
        edPhone = (EditText) findViewById(R.id.register_phone_number_input);
        edPassword = (EditText) findViewById(R.id.register_password_input);
        btnRegister = (Button) findViewById(R.id.register_btn);
    }
}
