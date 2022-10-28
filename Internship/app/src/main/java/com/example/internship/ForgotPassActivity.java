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

public class ForgotPassActivity extends AppCompatActivity {

    EditText edtEmailLogin, edtRe_NewPassword, edtNewPassword;
    Button btnChangePass;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtRe_NewPassword = findViewById(R.id.edtRe_NewPassword);
        btnChangePass = findViewById(R.id.btnChangePass);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference("Account");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Account acc = snapshot.child(firebaseAuth.getCurrentUser().getUid()).getValue(Account.class);
                        if(acc.getEmail().equals(edtEmailLogin.getText().toString())){
                            if (snapshot.child(firebaseAuth.getCurrentUser().getUid()).exists()) {
                                if (edtRe_NewPassword.getText().toString().equals(edtNewPassword.getText().toString()) && isValidPassword(edtNewPassword.getText().toString())) {
                                    firebaseUser.updatePassword(edtNewPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                ref.child(firebaseAuth.getCurrentUser().getUid()).child("pass").setValue(edtNewPassword.getText().toString().trim());
                                                Toast.makeText(ForgotPassActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                            else {
                                                Toast.makeText(ForgotPassActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(ForgotPassActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ForgotPassActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                            }
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