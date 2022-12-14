package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AddAccountAdminActivity extends AppCompatActivity {
    Spinner spnSchool1;
    DatabaseReference databaseReference;
    List<String> name, name2;
    List<String> role;
    Button btnCreate;
    EditText edtName, edtPhone, edtEmail, edtPass, edtRe_Password;
    AutoCompleteTextView edtNameCompany;
    ImageView imgShowPass, imgShowPass1;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_admin);
        edtEmail = findViewById(R.id.edtEmail_Admin);
        edtName = findViewById(R.id.edtName_Admin);
        edtPass = findViewById(R.id.edtPassword_Admin);
        edtPhone = findViewById(R.id.edtPhone_Admin);
        edtRe_Password = findViewById(R.id.edtRe_Password_Admin);
        imgShowPass = findViewById(R.id.imgShowPass);
        imgShowPass1 = findViewById(R.id.imgShowPass1);
        btnCreate= findViewById(R.id.btnCreate);
        firebaseAuth = FirebaseAuth.getInstance();
        name = new ArrayList<>();
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = db.getReference();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtName.getText().toString().isEmpty()){
                    Toast.makeText(AddAccountAdminActivity.this, "B???n ch??a nh???p t??n ng?????i d??ng", Toast.LENGTH_SHORT).show();
                }
                else if(edtPhone.getText().toString().isEmpty()){
                    Toast.makeText(AddAccountAdminActivity.this, "B???n ch??a nh???p s??? ??i???n tho???i", Toast.LENGTH_SHORT).show();
                }
                else if(edtEmail.getText().toString().isEmpty()){
                    Toast.makeText(AddAccountAdminActivity.this, "B???n ch??a nh???p Email", Toast.LENGTH_SHORT).show();
                }
                else if(edtPass.getText().toString().isEmpty()){
                    Toast.makeText(AddAccountAdminActivity.this, "B???n ch??a nh???p m???t kh???u", Toast.LENGTH_SHORT).show();
                }
                else if(edtRe_Password.getText().toString().isEmpty()){
                    Toast.makeText(AddAccountAdminActivity.this, "B???n ch??a nh???p l???i m???t kh???u", Toast.LENGTH_SHORT).show();
                }
                else {
                    btnCreate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(edtName.getText().toString().isEmpty()){
                                Toast.makeText(AddAccountAdminActivity.this, "B???n ch??a nh???p t??n ng?????i d??ng", Toast.LENGTH_SHORT).show();
                            }
                            else if(edtPhone.getText().toString().isEmpty()){
                                Toast.makeText(AddAccountAdminActivity.this, "B???n ch??a nh???p s??? ??i???n tho???i", Toast.LENGTH_SHORT).show();
                            }
                            else if(edtEmail.getText().toString().isEmpty()){
                                Toast.makeText(AddAccountAdminActivity.this, "B???n ch??a nh???p Email", Toast.LENGTH_SHORT).show();
                            }
                            else if(edtPass.getText().toString().isEmpty()){
                                Toast.makeText(AddAccountAdminActivity.this, "B???n ch??a nh???p m???t kh???u", Toast.LENGTH_SHORT).show();
                            }
                            else if(edtRe_Password.getText().toString().isEmpty()){
                                Toast.makeText(AddAccountAdminActivity.this, "B???n ch??a nh???p l???i m???t kh???u", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                final FirebaseDatabase db = FirebaseDatabase.getInstance();
                                final DatabaseReference ref = db.getReference("Account");
                                String email = edtEmail.getText().toString();
                                String pass = edtPass.getText().toString();
                                String code = databaseReference.push().getKey();
                                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            if (isValidEmail(edtEmail.getText().toString().trim()) && isValidPhone(edtPhone.getText().toString().trim()) && isValidPassword(edtPass.getText().toString().trim()) && edtPass.getText().toString().equals(edtRe_Password.getText().toString())) {
                                                String ID = firebaseAuth.getCurrentUser().getUid();
                                                Account account = new Account(edtPhone.getText().toString(), edtEmail.getText().toString(), edtPass.getText().toString(), edtName.getText().toString(), 4,ID);
                                                ref.child(ID).setValue(account)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Toast.makeText(AddAccountAdminActivity.this, "????ng k?? th??nh c??ng", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(AddAccountAdminActivity.this, AccountAdminActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        });
                                            }
                                        } else {
                                            Toast.makeText(AddAccountAdminActivity.this, "X??c th???c kh??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddAccountAdminActivity.this, "fail", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
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