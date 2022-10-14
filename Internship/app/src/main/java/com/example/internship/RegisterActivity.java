package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.internship.Model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference ref;
    List<String> role;
    Button btnDangKy, btnSignIn;
    EditText edtName, edtPhone, edtEmail, edtPass, edtRe_Password;
    ImageView imgShowPass, imgShowPass1;
    RadioButton rdoAdmin, rdoStudent;
    boolean isEnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        edtPass = findViewById(R.id.edtPassword);
        edtPhone = findViewById(R.id.edtPhone);
        edtRe_Password = findViewById(R.id.edtRe_Password);
        imgShowPass = findViewById(R.id.imgShowPass);
        imgShowPass1 = findViewById(R.id.imgShowPass1);
        rdoAdmin = findViewById(R.id.rdoAdmin);
        rdoStudent = findViewById(R.id.rdoStudent);
        role = new ArrayList<>();
        btnDangKy = findViewById(R.id.btnDangKy);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRegister();
            }
        });

        imgShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEnable) {
                    isEnable = true;
                    imgShowPass.setSelected(isEnable);
                    edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    isEnable = false;
                    imgShowPass.setSelected(isEnable);
                    edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        imgShowPass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEnable) {
                    isEnable = true;
                    imgShowPass.setSelected(isEnable);
                    edtRe_Password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    isEnable = false;
                    imgShowPass.setSelected(isEnable);
                    edtRe_Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    public void onClickRegister(){
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference("Account");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(isValidEmail(edtEmail.getText().toString().trim()) && isValidPhone(edtPhone.getText().toString().trim()) && isValidPassword(edtPass.getText().toString().trim()) && edtPass.getText().toString().equals(edtRe_Password.getText().toString())){
                    if(snapshot.child(edtPhone.getText().toString().trim()).exists()){
                        Toast.makeText(RegisterActivity.this, "Number Already...", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(rdoAdmin.isChecked()){
                            Account account = new Account(edtEmail.getText().toString(), edtPass.getText().toString(), edtName.getText().toString(), 1);
                            ref.child(edtPhone.getText().toString()).setValue(account);
                            Toast.makeText(RegisterActivity.this, "Sign up Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else if(rdoStudent.isChecked()){
                            Account account = new Account(edtEmail.getText().toString(), edtPass.getText().toString(), edtName.getText().toString(), 2);
                            ref.child(edtPhone.getText().toString()).setValue(account);
                            Toast.makeText(RegisterActivity.this, "Sign up Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Email, Password or Number is incorrect...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean isValidEmail(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private boolean isValidPhone(String phone){
        return Pattern.compile("^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$")
                .matcher(phone).matches() ;
    }

    private boolean isValidPassword(String pass){
        return Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$").matcher(pass).matches();
    }
}