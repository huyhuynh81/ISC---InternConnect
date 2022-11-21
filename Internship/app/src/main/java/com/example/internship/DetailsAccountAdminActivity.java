package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.internship.Model.Account;
import com.example.internship.Model.Company;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DetailsAccountAdminActivity extends AppCompatActivity {
    EditText edtEmailAcc_Details_Admin, edtRole_Details_Admin, edtPass_Details_Admin, edtNameAcc_Details_Admin,edtPhoneAcc_Details_Admin;
    Button btnUpdatePostAcc_Admin, btnDeleteAcc_Admin;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_account_admin);

        Account cpn = (Account) getIntent().getSerializableExtra("detail_acc");
        edtEmailAcc_Details_Admin = findViewById(R.id.edtEmailAcc_Details_Admin);
        edtRole_Details_Admin = findViewById(R.id.edtRole_Details_Admin);
        edtPass_Details_Admin = findViewById(R.id.edtPass_Details_Admin);
        edtNameAcc_Details_Admin = findViewById(R.id.edtNameAcc_Details_Admin);
        edtPhoneAcc_Details_Admin = findViewById(R.id.edtPhoneAcc_Details_Admin);

        edtEmailAcc_Details_Admin.setText(cpn.getEmail());
        String role=String.valueOf(cpn.getRole());
        if(role.equals("4")){
            edtRole_Details_Admin.setText("Admin");
        }
        edtPass_Details_Admin.setText(cpn.getPass());
        edtNameAcc_Details_Admin.setText(cpn.getName());
        edtPhoneAcc_Details_Admin.setText(cpn.getPhone());
        btnUpdatePostAcc_Admin = findViewById(R.id.btnUpdatePostAcc_Admin);
        btnDeleteAcc_Admin = findViewById(R.id.btnDeleteAcc_Admin);

        edtEmailAcc_Details_Admin.setEnabled(false);
        edtRole_Details_Admin.setEnabled(false);
        edtPass_Details_Admin.setEnabled(false);
        edtNameAcc_Details_Admin.setEnabled(false);
        edtPhoneAcc_Details_Admin.setEnabled(false);

        btnUpdatePostAcc_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(DetailsAccountAdminActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                progressDialog.setTitle("Đang kết nối...");

                Runnable progressRunable = new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.cancel();
                    }
                };
                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunable,500);
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );

                edtEmailAcc_Details_Admin.setEnabled(true);
                edtPass_Details_Admin.setEnabled(true);
                edtNameAcc_Details_Admin.setEnabled(true);
                edtPhoneAcc_Details_Admin.setEnabled(true);
                btnUpdatePostAcc_Admin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressDialog = new ProgressDialog(DetailsAccountAdminActivity.this);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.show();
                        progressDialog.setTitle("Connecting");

                        Runnable progressRunable = new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.cancel();
                            }
                        };
                        Handler pdCanceller = new Handler();
                        pdCanceller.postDelayed(progressRunable,500);
                        progressDialog.setContentView(R.layout.progress_dialog);
                        progressDialog.getWindow().setBackgroundDrawableResource(
                                android.R.color.transparent
                        );
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference reference = db.getReference("Account");
                        Map<String,Object> map = new HashMap<>();
                        map.put("email",edtEmailAcc_Details_Admin.getText().toString().trim());
                        map.put("name",edtNameAcc_Details_Admin.getText().toString().trim());
                        map.put("pass",edtPass_Details_Admin.getText().toString().trim());
                        map.put("role",4);
                        map.put("phone",edtPhoneAcc_Details_Admin.getText().toString().trim());
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                reference.child(cpn.getId_account()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(DetailsAccountAdminActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                        Intent inten = new Intent (DetailsAccountAdminActivity.this,AccountAdminActivity.class);
                                        startActivity(inten);
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                });
            }
        });
        btnDeleteAcc_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference reference = db.getReference("Account");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference.child(cpn.getId_account()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(DetailsAccountAdminActivity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                                Intent inten = new Intent (DetailsAccountAdminActivity.this,AccountAdminActivity.class);
                                startActivity(inten);
                                finish();
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}