package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.internship.Model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class ForgotPassActivity extends AppCompatActivity {

    ImageView imgShowPassNew, imgShowPassNew1;
    EditText edtPhoneLogin, edtNewpass,edtRenew_Pass;
    Button btnChangePass;
    boolean isEnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        imgShowPassNew = findViewById(R.id.imgShowPassNew);
        imgShowPassNew1 = findViewById(R.id.imgShowPassNew1);
        edtPhoneLogin = findViewById(R.id.edtPhoneLogin);
        edtNewpass = findViewById(R.id.edtNewpass);
        edtRenew_Pass = findViewById(R.id.edtRenew_Pass);
        btnChangePass = findViewById(R.id.btnChangePass);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference("Account");
        String password = edtNewpass.getText().toString().trim();
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        imgShowPassNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEnable) {
                    isEnable = true;
                    imgShowPassNew.setSelected(isEnable);
                    edtNewpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    isEnable = false;
                    imgShowPassNew.setSelected(isEnable);
                    edtNewpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        imgShowPassNew1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEnable) {
                    isEnable = true;
                    imgShowPassNew1.setSelected(isEnable);
                    edtRenew_Pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    isEnable = false;
                    imgShowPassNew1.setSelected(isEnable);
                    edtRenew_Pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(edtPhoneLogin.getText().toString()).exists()){
                            Account acc = snapshot.child(edtPhoneLogin.getText().toString()).getValue(Account.class);
                            if(edtRenew_Pass.getText().toString().equals(edtNewpass.getText().toString())){
                                ref.child(edtPhoneLogin.getText().toString()).child("pass").setValue(bcryptHashString);
                                Toast.makeText(ForgotPassActivity.this, "Change Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ForgotPassActivity.this, MainActivity.class);
                                startActivity(intent);                                }
                            else {
                                Toast.makeText(ForgotPassActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(ForgotPassActivity.this, "Phone number not exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}