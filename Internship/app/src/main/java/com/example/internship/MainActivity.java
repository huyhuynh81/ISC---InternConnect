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
import android.widget.TextView;
import android.widget.Toast;

import com.example.internship.Model.AccountCompany;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    Button btnDangNhap;
    EditText edtPassword, edtEmail;
    ImageView imgShowPass;
    boolean isEnable;
    TextView txtForgotPass, txtSignUp;
    Integer company = 1, student = 2, school = 3, admin = 4;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgShowPass = findViewById(R.id.imgShowPass);
        txtForgotPass = findViewById(R.id.txtForgotPass);
        edtEmail = findViewById(R.id.edtEmail);
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
        if (edtEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "B???n ch??a nh???p Email", Toast.LENGTH_SHORT).show();
        } else if (edtPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "B???n ch??a nh???p m???t kh???u", Toast.LENGTH_SHORT).show();
        } else {
            final FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference ref = db.getReference("Account");
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.child(firebaseAuth.getCurrentUser().getUid()).exists()){
                                    AccountCompany accCpn = snapshot.child(firebaseAuth.getCurrentUser().getUid()).getValue(AccountCompany.class);
                                    if(accCpn.getRole() == admin){
                                        Intent intent = new Intent(getApplicationContext(), HomeAdminActivity .class);
                                        intent.putExtra("acc", accCpn);
                                        startActivity(intent);
                                    }
                                    else if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                        if (accCpn.getRole() == student) {
                                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                            intent.putExtra("acc", accCpn);
                                            startActivity(intent);
                                        } else if (accCpn.getRole() == company) {
                                            Intent intent = new Intent(getApplicationContext(), HomeCompanyActivity.class);
                                            intent.putExtra("acc", accCpn);
                                            startActivity(intent);
                                        }
                                        else if(accCpn.getRole() == school){
                                            Intent intent = new Intent(getApplicationContext(), HomeSchoolActivity.class);
                                            intent.putExtra("acc", accCpn);
                                            startActivity(intent);
                                        }
//                                            else if(accCpn.getRole() == admin){
//                                                Intent intent = new Intent(getApplicationContext(), HomeAdminActivity .class);
//                                                intent.putExtra("acc", accCpn);
//                                                startActivity(intent);
//                                            }
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "T??i kho???n kh??ng x??c th???c", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "T??i kho???n kh??ng t???n t???i", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "????ng nh???p kh??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}

