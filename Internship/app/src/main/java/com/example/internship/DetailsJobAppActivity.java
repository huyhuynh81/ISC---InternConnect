package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internship.Model.JobApp;
import com.example.internship.Model.JobPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DetailsJobAppActivity extends AppCompatActivity {

    TextView txtNameCom_JA, txtNameUser_JA, txtNameSchool_JA, txtMajor_JA, txtDate_JA, txtStatus_JA, txtCV_JA, txtVerify;
    ImageButton imgCV_JA;

    Button btnAccept, btnRefuse;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_job_app);
        txtNameCom_JA = (TextView) findViewById(R.id.txtNameCom_JA);
        txtNameUser_JA = (TextView) findViewById(R.id.txtNameUser_JA);
        txtNameSchool_JA = (TextView) findViewById(R.id.txtNameSchool_JA);
        txtMajor_JA = (TextView) findViewById(R.id.txtMajor_JA);
        txtDate_JA = (TextView) findViewById(R.id.txtDate_JA);
        txtStatus_JA = (TextView) findViewById(R.id.txtStatus_JA);
        imgCV_JA = (ImageButton) findViewById(R.id.imgCV_JA);
        btnAccept = (Button) findViewById(R.id.btnAccept_JA);
        btnRefuse = (Button) findViewById(R.id.btnRefuse_JA);
        txtCV_JA = (TextView) findViewById(R.id.txtCV_JA);
        txtVerify = (TextView) findViewById(R.id.txtVerify);

        JobApp jba = (JobApp) getIntent().getSerializableExtra("obj_JobApp");

//        String datetime = jba.getDateApp();
//        Calendar date = Calendar.getInstance();
//        SimpleDateFormat sim = new SimpleDateFormat("dd/MM/yyyy");
//        datetime = sim.format(date.getTime());

        txtCV_JA.setText(jba.getUrl());
        txtNameCom_JA.setText(jba.getNameCom());
        txtNameUser_JA.setText(jba.getNameUser());
        txtNameSchool_JA.setText(jba.getNameSchool());
        txtMajor_JA.setText(jba.getMajor());
        txtDate_JA.setText(jba.getDate());
        txtStatus_JA.setText(jba.getStatus());
        if (jba.getVerify().equals("true")) {
            txtVerify.setText("Tài khoản đã xác thực");
        } else if (jba.getVerify().equals("false")) {
            txtVerify.setText("Tài khoản chưa xác thực");
        }

        btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("JobApp");
                userID = user.getUid();
                Map<String, Object> map = new HashMap<>();
                map.put("date", txtDate_JA.getText().toString().trim());
                map.put("major", txtMajor_JA.getText().toString().trim());
                map.put("name", txtNameUser_JA.getText().toString().trim());
                map.put("nameCom", txtNameCom_JA.getText().toString().trim());
                map.put("nameSchool", txtNameSchool_JA.getText().toString().trim());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        map.put("status", "Không xác nhận");
                        reference.child(jba.getID_User()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(DetailsJobAppActivity.this, "Success", Toast.LENGTH_SHORT).show();
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
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("JobApp");
                userID = user.getUid();
                Map<String, Object> map = new HashMap<>();
                map.put("date", txtDate_JA.getText().toString().trim());
                map.put("major", txtMajor_JA.getText().toString().trim());
                map.put("name", txtNameUser_JA.getText().toString().trim());
                map.put("nameCom", txtNameCom_JA.getText().toString().trim());
                map.put("nameSchool", txtNameSchool_JA.getText().toString().trim());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        map.put("status", "Đã xác nhận");
                        reference.child(jba.getID_User()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(DetailsJobAppActivity.this, "Success", Toast.LENGTH_SHORT).show();
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