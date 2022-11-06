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
import com.example.internship.Model.AccountCompany;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText edtNewpassword, edtRenew_password, edtCurrentEmail, edtCurrentPass;
    Button btnUpdatePasswod;
    ImageView imgShowPassCurrent, imgShowPassNew, imgShowPassNew1;
    boolean isEnable;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        imgShowPassCurrent = findViewById(R.id.imgShowCurrentPass);
        imgShowPassNew = findViewById(R.id.imgShowPassNew);
        imgShowPassNew1 = findViewById(R.id.imgShowPassNew1);
        edtNewpassword = findViewById(R.id.edtNewpassword);
        edtRenew_password = findViewById(R.id.edtRenew_Password);
        edtCurrentPass = findViewById(R.id.edtCurrentPass);
        edtCurrentEmail = findViewById(R.id.edtCurrentEmail);
        btnUpdatePasswod = findViewById(R.id.btnUpdatePass);
        AccountCompany Username = (AccountCompany) getIntent().getSerializableExtra("acc");
        edtCurrentEmail.setText(Username.getEmail());
        edtCurrentEmail.setEnabled(false);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference("Account");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        imgShowPassCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEnable) {
                    isEnable = true;
                    imgShowPassCurrent.setSelected(isEnable);
                    edtCurrentPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    isEnable = false;
                    imgShowPassCurrent.setSelected(isEnable);
                    edtCurrentPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        imgShowPassNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEnable) {
                    isEnable = true;
                    imgShowPassNew.setSelected(isEnable);
                    edtNewpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    isEnable = false;
                    imgShowPassNew.setSelected(isEnable);
                    edtNewpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        imgShowPassNew1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEnable) {
                    isEnable = true;
                    imgShowPassNew1.setSelected(isEnable);
                    edtRenew_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    isEnable = false;
                    imgShowPassNew1.setSelected(isEnable);
                    edtRenew_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        btnUpdatePasswod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(firebaseAuth.getCurrentUser().getUid()).exists()) {
                            Account acc = snapshot.child(firebaseAuth.getCurrentUser().getUid()).getValue(Account.class);
                            if (acc.getPass().equals(edtCurrentPass.getText().toString()) && edtRenew_password.getText().toString().equals(edtNewpassword.getText().toString()) && isValidPassword(edtNewpassword.getText().toString())) {
                                firebaseUser.updatePassword(edtNewpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            ref.child(firebaseAuth.getCurrentUser().getUid()).child("pass").setValue(edtNewpassword.getText().toString().trim());
                                            Toast.makeText(ChangePasswordActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(ChangePasswordActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else {
                                Toast.makeText(ChangePasswordActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private boolean isValidPassword(String pass) {
        return Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$").matcher(pass).matches();
    }
}