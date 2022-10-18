package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internship.Model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class MainActivity extends AppCompatActivity {
    Button btnDangNhap;
    EditText edtPassword, edtPhone;
    ImageView imgShowPass;
    boolean isEnable;
    TextView txtForgotPass, txtSignUp;
    Integer admin = 1;
    Integer student = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgShowPass = findViewById(R.id.imgShowPass);
        txtForgotPass = findViewById(R.id.txtForgotPass);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        txtSignUp = findViewById(R.id.txtSignUp);

        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignIn();
            }
        });

        imgShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEnable) {
                    isEnable = true;
                    imgShowPass.setSelected(isEnable);
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    isEnable = false;
                    imgShowPass.setSelected(isEnable);
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

    }

    public void onClickSignIn() {
        String password = edtPassword.getText().toString().trim();

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference("Account");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(edtPhone.getText().toString()).exists()) {
                    Account acc = snapshot.child(edtPhone.getText().toString()).getValue(Account.class);
                    String pass = acc.getPass();
                    BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(),pass);
                    if(result.verified == true){
                        if(acc.getRole() == student){
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("acc",acc);
                            startActivity(intent);
                        }
                        else if(acc.getRole() == admin){
                            Intent intent = new Intent(getApplicationContext(), HomeAdminActivity.class);
                            intent.putExtra("acc",acc);
                            startActivity(intent);
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "User not exist...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}