package com.example.internship;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.internship.Model.Account;
import com.example.internship.Model.JobApp;
import com.example.internship.Model.JobPost;
import com.example.internship.Model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class SchoolDetailsActivity extends AppCompatActivity {

    EditText edtNameStudent, edtEmailStudent, edtCodeStudent, edtAcademicYearStudent, edtAddressStudent, edtGenderStudent, edtMajorStudent, edtSchoolStudent;
    CheckBox chbVerify;
    Button btnUpdateStudent, btnQuaylai;
    ProgressDialog progressDialog;
    Dialog dialog;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details);
        dialog = new Dialog(this);
        Student std = (Student) getIntent().getSerializableExtra("obj_cpn");
        chbVerify = findViewById(R.id.chbVerify);
        edtNameStudent = findViewById(R.id.edtNameStudent);
        edtEmailStudent = findViewById(R.id.edtEmailStudent);
        edtCodeStudent = findViewById(R.id.edtCodeStudent);
        edtAcademicYearStudent = findViewById(R.id.edtAcademicYearStudent);
        edtAddressStudent = findViewById(R.id.edtAddressStudent);
        edtGenderStudent = findViewById(R.id.edtGenderStudent);
        edtMajorStudent = findViewById(R.id.edtMajorStudent);
        edtSchoolStudent = findViewById(R.id.edtSchoolStudent);
        edtNameStudent.setText(std.getName());
        edtEmailStudent.setText(std.getEmail());
        edtCodeStudent.setText(std.getCode());
        edtAcademicYearStudent.setText(std.getAcademicYear());
        edtAddressStudent.setText(std.getAddress());
        edtGenderStudent.setText(std.getGender());
        edtMajorStudent.setText(std.getMajor());
        edtSchoolStudent.setText(std.getSchool());

        btnQuaylai = (Button) findViewById(R.id.btnQuaylai);
        btnUpdateStudent = (Button) findViewById(R.id.btnUpdateStudent);
        edtNameStudent.setEnabled(false);
        edtEmailStudent.setEnabled(false);
        edtCodeStudent.setEnabled(false);
        edtAcademicYearStudent.setEnabled(false);
        edtAddressStudent.setEnabled(false);
        edtGenderStudent.setEnabled(false);
        edtMajorStudent.setEnabled(false);
        edtSchoolStudent.setEnabled(false);
        chbVerify.setEnabled(false);
        chbVerify.setChecked(true);

        if(std.getVerify().equals("true")){
            chbVerify.setChecked(true);
        }
        else {
            chbVerify.setChecked(false);
        }


        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(SchoolDetailsActivity.this);
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
                pdCanceller.postDelayed(progressRunable, 500);
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                chbVerify.setEnabled(true);

                btnUpdateStudent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressDialog = new ProgressDialog(SchoolDetailsActivity.this);
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
                        pdCanceller.postDelayed(progressRunable, 500);
                        progressDialog.setContentView(R.layout.progress_dialog);
                        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        chbVerify.setEnabled(true);

                        user = FirebaseAuth.getInstance().getCurrentUser();
                        reference = FirebaseDatabase.getInstance().getReference("Student");
                        userID = user.getUid();
                        Map<String, Object> map = new HashMap<>();
                        map.put("code", edtCodeStudent.getText().toString().trim());
                        map.put("academicYear", edtAcademicYearStudent.getText().toString().trim());
                        map.put("address", edtAddressStudent.getText().toString().trim());
                        map.put("email", edtEmailStudent.getText().toString().trim());
                        map.put("gender", edtGenderStudent.getText().toString().trim());
                        map.put("major", edtMajorStudent.getText().toString().trim());
                        map.put("name", edtNameStudent.getText().toString().trim());
                        map.put("school", edtSchoolStudent.getText().toString().trim());
                        if (chbVerify.isChecked()) {
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    map.put("verify", "true");
                                    reference.child(std.getID()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(SchoolDetailsActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    map.put("verify", "false");
                                    reference.child(std.getID()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(SchoolDetailsActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
            }
        });
    }

}
