package com.example.myshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myshoppingapp.model.UsersDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText edPhone, edPassword;
    private Button btnLogin;
    private ProgressDialog lodingBar;
    private final String DBName="Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edPhone = (EditText) findViewById(R.id.login_phone_number_input);
        edPassword = (EditText) findViewById(R.id.login_password_input);
        btnLogin = (Button) findViewById(R.id.login_btn);
        lodingBar = new ProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAccount();
            }
        });


    }

    private void loginAccount() {

        String phone = edPhone.getText().toString();
        String password = edPassword.getText().toString();

        if (TextUtils.isEmpty(phone))
        {
            edPhone.setError("Enter phone number");
            return;
        }
        else if (TextUtils.isEmpty(password))
        {
            edPassword.setError("Enter password");
            return;
        }
        else
        {

            lodingBar.setTitle("Login Account");
            lodingBar.setMessage("Please wait , while we are checking the credentiais");
            lodingBar.setCanceledOnTouchOutside(false);
            lodingBar.show();

            goToAccount(phone, password);

        }
    }

    private void goToAccount(final String phone, final String password) {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(DBName).child(phone).exists())
                {
                    UsersDetails userData=dataSnapshot.child(DBName).child(phone).getValue(UsersDetails.class);

                    if (userData.getUserPhone().equals(phone))
                    {
                        if (userData.getUserPassword().equals(password))
                        {
                            Toast.makeText(LoginActivity.this,"Your login",Toast.LENGTH_LONG).show();
                            lodingBar.dismiss();

                            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }
                        else {


                            Toast.makeText(LoginActivity.this,"Your Enter Wrong Password",Toast.LENGTH_LONG).show();
                            lodingBar.dismiss();
                            edPassword.setText("");

                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Account not exist",Toast.LENGTH_LONG).show();
                    lodingBar.dismiss();
                    Toast.makeText(LoginActivity.this,"Plise create an new account",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
