package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internship.Model.Account;
import com.example.internship.Model.AccountCompany;
import com.example.internship.Model.AccountSchool;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    Spinner spnSchool1;
    DatabaseReference databaseReference;
    List<String> name, name2;
    List<String> role;
    Button btnDangKy;
    TextView txtSignIn;
    EditText edtName, edtPhone, edtEmail, edtPass, edtRe_Password;
    AutoCompleteTextView edtNameCompany;
    ImageView imgShowPass, imgShowPass1;
    RadioButton rdoAdmin, rdoStudent, rdoSchool;
    boolean isEnable;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtNameCompany = findViewById(R.id.edtNameCompany);
        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        edtPass = findViewById(R.id.edtPassword);
        edtPhone = findViewById(R.id.edtPhone);
        edtRe_Password = findViewById(R.id.edtRe_Password);
        imgShowPass = findViewById(R.id.imgShowPass);
        imgShowPass1 = findViewById(R.id.imgShowPass1);
        rdoAdmin = findViewById(R.id.rdoAdmin);
        rdoStudent = findViewById(R.id.rdoStudent);
        rdoSchool = findViewById(R.id.rdoSchool);
        spnSchool1 = findViewById(R.id.spnSchool1);
        spnSchool1.setEnabled(false);
        edtNameCompany.setEnabled(false);
        role = new ArrayList<>();
        btnDangKy = findViewById(R.id.btnDangKy);
        txtSignIn = findViewById(R.id.txtSignIn);
        firebaseAuth = FirebaseAuth.getInstance();

        rdoSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdoSchool.isChecked()){
                    spnSchool1.setEnabled(true);
                    edtNameCompany.setEnabled(false);
                }
            }
        });

        rdoStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdoStudent.isChecked()){
                    spnSchool1.setEnabled(false);
                    edtNameCompany.setEnabled(false);
                }
            }
        });

        rdoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdoAdmin.isChecked()){
                    spnSchool1.setEnabled(false);
                    edtNameCompany.setEnabled(true);
                }
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
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

        name = new ArrayList<>();
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = db.getReference();
        databaseReference.child("School").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childsnapshot : snapshot.getChildren()) {
                    String SchoolName = childsnapshot.child("nameSchool").getValue(String.class);
                    name.add(SchoolName);
                }
                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_item, name);
                arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spnSchool1.setAdapter(arrayAdapter2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        name2 = new ArrayList<>();
        databaseReference.child("Company").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childsnapshot : snapshot.getChildren()) {
                    String CompanyName = childsnapshot.child("Name").getValue(String.class);
                    name2.add(CompanyName);
                }
                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_dropdown_item_1line, name2);
                edtNameCompany.setThreshold(3);
                //arrayAdapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                edtNameCompany.setAdapter(arrayAdapter2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClickRegister() {
        if(edtName.getText().toString().isEmpty()){
            Toast.makeText(this, "Bạn chưa nhập tên người dùng", Toast.LENGTH_SHORT).show();
        }
        else if(edtPhone.getText().toString().isEmpty()){
            Toast.makeText(this, "Bạn chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
        }
        else if(edtEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Bạn chưa nhập Email", Toast.LENGTH_SHORT).show();
        }
        else if(edtPass.getText().toString().isEmpty()){
            Toast.makeText(this, "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
        }
        else if(edtRe_Password.getText().toString().isEmpty()){
            Toast.makeText(this, "Bạn chưa nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
        }
        else {
            final FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference ref = db.getReference("Account");
            String email = edtEmail.getText().toString();
            String pass = edtPass.getText().toString();
            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Đã gửi email xác thực", Toast.LENGTH_SHORT).show();

                                    if (isValidEmail(edtEmail.getText().toString().trim()) && isValidPhone(edtPhone.getText().toString().trim()) && isValidPassword(edtPass.getText().toString().trim()) && edtPass.getText().toString().equals(edtRe_Password.getText().toString())) {
                                        if (rdoAdmin.isChecked()) {
                                            AccountCompany account = new AccountCompany(edtPhone.getText().toString(), edtEmail.getText().toString(), edtPass.getText().toString(), edtName.getText().toString(), edtNameCompany.getText().toString(), 1);
                                            ref.child(firebaseAuth.getCurrentUser().getUid()).setValue(account)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(RegisterActivity.this, "Sign up Successfully", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                        }
                                        else if (rdoStudent.isChecked()) {
                                            Account account = new Account(edtPhone.getText().toString(), edtEmail.getText().toString(), edtPass.getText().toString(), edtName.getText().toString(), 2);
                                            ref.child(firebaseAuth.getCurrentUser().getUid()).setValue(account)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(RegisterActivity.this, "Sign up Successfully", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                        }
                                        else if(rdoSchool.isChecked()){
                                            AccountSchool account = new AccountSchool(edtPhone.getText().toString(), edtEmail.getText().toString(), edtPass.getText().toString(), edtName.getText().toString(), 3, spnSchool1.getSelectedItem().toString());
                                            ref.child(firebaseAuth.getCurrentUser().getUid()).setValue(account)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(RegisterActivity.this, "Sign up Successfully", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                        }
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Xác thực không thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private boolean isValidEmail(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@" + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|" + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private boolean isValidPhone(String phone) {
        return Pattern.compile("^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$").matcher(phone).matches();
    }

    private boolean isValidPassword(String pass) {
        return Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$").matcher(pass).matches();
    }
}


