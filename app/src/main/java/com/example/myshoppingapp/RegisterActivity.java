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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText edName, edPhone, edPassword;
    private Button btnRegister;
    private ProgressDialog lodingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edName = (EditText) findViewById(R.id.register_name_input);
        edPhone = (EditText) findViewById(R.id.register_phone_number_input);
        edPassword = (EditText) findViewById(R.id.register_password_input);
        btnRegister = (Button) findViewById(R.id.register_btn);
        lodingBar = new ProgressDialog(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount();

                edName.setText("");
                edPhone.setText("");
                edPassword.setText("");

            }
        });
    }

    private void createAccount() {

        String name = edName.getText().toString();
        String phone = edPhone.getText().toString();
        String password = edPassword.getText().toString();

        if (TextUtils.isEmpty(name))
        {

            edName.setError("Enter the name");
            return;

        }else if (TextUtils.isEmpty(phone))
        {

            edPhone.setError("Enter the phone number");
            return;

        }else if (TextUtils.isEmpty(password))
        {

            edPassword.setError("Enter the password");
            return;

        }else
        {

            lodingBar.setTitle("Create Account");
            lodingBar.setMessage("Please wait , while we are checking the credentiais");
            lodingBar.setCanceledOnTouchOutside(false);
            lodingBar.show();

            ValidationPhoneNumber(name, phone, password);


        }


    }

    private void ValidationPhoneNumber(final String name, final String phone, final String password) {

        final DatabaseReference RootRef;

        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Users").child(phone).exists()))
                {

                    HashMap<String,Object> userData = new HashMap<>();

                    userData.put("Name",name);
                    userData.put("Phone",phone);
                    userData.put("Password",password);

                    RootRef.child("Users").child(phone).updateChildren(userData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {

                                        Toast.makeText(RegisterActivity.this,"Congratulations your account is create",Toast.LENGTH_LONG).show();
                                        lodingBar.dismiss();
                                        Intent intent=new Intent(RegisterActivity.this,loginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {

                                        lodingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this,"Network error please try again",Toast.LENGTH_LONG).show();

                                    }

                                }
                            });

                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"That account already existing",Toast.LENGTH_LONG).show();
                    lodingBar.dismiss();
                    Toast.makeText(RegisterActivity.this,"Please try again, Use another phone number",Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
