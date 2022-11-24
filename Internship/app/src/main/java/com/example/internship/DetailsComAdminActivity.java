package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internship.Model.Account;
import com.example.internship.Model.Company;
import com.example.internship.Model.JobPost;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class DetailsComAdminActivity extends AppCompatActivity {
    ImageView imgLogoAd_Details_Admin;
    TextView txtLogo_Admin;
    EditText edtEmail_Details_Admin, edtName_Details_Admin, edtLocation_Details_Admin, edtPhone_Details_Admin;
    Button btnUpdatePost_Admin, btnDelete_Admin;
    ProgressDialog progressDialog;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_com_admin);
        dialog = new Dialog(this);
        Company cpn = (Company) getIntent().getSerializableExtra("detail_com");
        edtEmail_Details_Admin = findViewById(R.id.edtEmail_Details_Admin);
        edtName_Details_Admin = findViewById(R.id.edtName_Details_Admin);
        edtLocation_Details_Admin = findViewById(R.id.edtLocation_Details_Admin);
        edtPhone_Details_Admin = findViewById(R.id.edtPhone_Details_Admin);
        txtLogo_Admin = findViewById(R.id.txtLogo_Admin);
        imgLogoAd_Details_Admin = findViewById(R.id.imgLogoAd_Details_Admin);

        edtEmail_Details_Admin.setText(cpn.getEmail());
        edtName_Details_Admin.setText(cpn.getName());
        edtLocation_Details_Admin.setText(cpn.getLocation());
        edtPhone_Details_Admin.setText(cpn.getPhone());
        txtLogo_Admin.setText(cpn.getLogo());
        String image = cpn.getLogo();
        Picasso.with(this).load(image).into(imgLogoAd_Details_Admin);
        btnUpdatePost_Admin = findViewById(R.id.btnUpdatePost_Admin);
        btnDelete_Admin = findViewById(R.id.btnDelete_Admin);


        edtEmail_Details_Admin.setEnabled(false);
        edtName_Details_Admin.setEnabled(false);
        edtLocation_Details_Admin.setEnabled(false);
        edtPhone_Details_Admin.setEnabled(false);
        btnUpdatePost_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtEmail_Details_Admin.setEnabled(true);
                edtName_Details_Admin.setEnabled(true);
                edtLocation_Details_Admin.setEnabled(true);
                edtPhone_Details_Admin.setEnabled(true);
                btnUpdatePost_Admin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference reference = db.getReference("Company");
                        Map<String,Object> map = new HashMap<>();
                        map.put("Email",edtEmail_Details_Admin.getText().toString().trim());
                        map.put("Logo",txtLogo_Admin.getText().toString().trim());
                        map.put("Location",edtLocation_Details_Admin.getText().toString().trim());
                        map.put("Name",edtName_Details_Admin.getText().toString().trim());
                        map.put("Phone",edtPhone_Details_Admin.getText().toString().trim());

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                reference.child(edtName_Details_Admin.getText().toString()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(DetailsComAdminActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();

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




        btnDelete_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference reference = db.getReference("Company");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference.child(edtName_Details_Admin.getText().toString()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(DetailsComAdminActivity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DetailsComAdminActivity.this, HomeCompanyAdminActivity.class);
                                startActivity(intent);
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