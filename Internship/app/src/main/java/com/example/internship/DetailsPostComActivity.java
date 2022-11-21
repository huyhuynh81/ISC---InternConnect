package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.internship.Model.Account;
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

public class DetailsPostComActivity extends AppCompatActivity {

    ImageView imgLogo_Details;
    EditText edtId_Post, edtPosition_Details, edtName_Details, edtLocation_Details, edtSalary_Details, edtGender_Details,
            edtRequired_Details, edtBenefit_Details, edtNumber_Details;
    Button btnUpdatePost, btnQuaylai;
    ProgressDialog progressDialog;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_post_com);
        dialog = new Dialog(this);
        Account Username = (Account) getIntent().getSerializableExtra("acc");
        JobPost cpn = (JobPost) getIntent().getSerializableExtra("obj_cpn");
        edtId_Post = findViewById(R.id.edtId_Post);
        edtPosition_Details = findViewById(R.id.edtPosition_Details);
        edtName_Details = findViewById(R.id.edtName_Details);
        edtLocation_Details = findViewById(R.id.edtLocation_Details);
        edtSalary_Details = findViewById(R.id.edtSalary_Details);
        edtGender_Details = findViewById(R.id.edtGender_Details);
        edtRequired_Details = findViewById(R.id.edtRequired_Details);
        edtBenefit_Details = findViewById(R.id.edtBenifit_Details);
        edtNumber_Details = findViewById(R.id.edtNumber_Details);
        imgLogo_Details = findViewById(R.id.imgLogoAd_Details);
        edtId_Post.setText(cpn.getId_Post());
        edtPosition_Details.setText(cpn.getPosition());
        edtName_Details.setText(cpn.getName());
        edtLocation_Details.setText(cpn.getLocation());
        edtSalary_Details.setText(cpn.getSalary());
        edtGender_Details.setText(cpn.getGender());
        edtRequired_Details.setText(cpn.getRequired());
        edtBenefit_Details.setText(cpn.getBenefit());
        edtNumber_Details.setText(cpn.getNumber());
        String image = cpn.getLogo();
        Picasso.with(this).load(image).into(imgLogo_Details);
        btnQuaylai = (Button) findViewById(R.id.btnQuaylai);
        btnUpdatePost = (Button) findViewById(R.id.btnUpdatePost);
        edtPosition_Details.setEnabled(false);
        edtName_Details.setEnabled(false);
        edtLocation_Details.setEnabled(false);
        edtNumber_Details.setEnabled(false);
        edtSalary_Details.setEnabled(false);
        edtGender_Details.setEnabled(false);
        edtRequired_Details.setEnabled(false);
        edtBenefit_Details.setEnabled(false);

        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUpdatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(DetailsPostComActivity.this);
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
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                edtPosition_Details.setEnabled(true);
                edtName_Details.setEnabled(true);
                edtNumber_Details.setEnabled(true);
                edtSalary_Details.setEnabled(true);
                edtGender_Details.setEnabled(true);
                edtLocation_Details.setEnabled(true);
                edtRequired_Details.setEnabled(true);
                edtBenefit_Details.setEnabled(true);

                btnUpdatePost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressDialog = new ProgressDialog(DetailsPostComActivity.this);
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
                        edtPosition_Details.setEnabled(false);
                        edtName_Details.setEnabled(false);
                        edtLocation_Details.setEnabled(false);
                        edtNumber_Details.setEnabled(false);
                        edtSalary_Details.setEnabled(false);
                        edtGender_Details.setEnabled(false);
                        edtRequired_Details.setEnabled(false);
                        edtBenefit_Details.setEnabled(false);
                        String _Name = edtName_Details.getText().toString();
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference reference = db.getReference("JobPost");
//                        DatabaseReference reference1 = db.getReference("JobPost").child();
                        Map<String,Object> map = new HashMap<>();
                        map.put("Benefit",edtBenefit_Details.getText().toString().trim());
                        map.put("Gender",edtGender_Details.getText().toString().trim());
                        map.put("Location",edtLocation_Details.getText().toString().trim());
                        map.put("Name",edtName_Details.getText().toString().trim());
                        map.put("Position",edtPosition_Details.getText().toString().trim());
                        map.put("Required",edtRequired_Details.getText().toString().trim());
                        map.put("Salary",edtSalary_Details.getText().toString().trim());
                        map.put("Number",edtNumber_Details.getText().toString().trim());
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                reference.child(edtId_Post.getText().toString()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(DetailsPostComActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
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
    }

//    private void openApplyPopup(int gravity) {
//        dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.layout_popup_delete);
//
//        Window window = dialog.getWindow();
//        if (window == null) {
//            return;
//        }
//
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        WindowManager.LayoutParams windowAttributes = window.getAttributes();
//        windowAttributes.gravity = gravity;
//        window.setAttributes(windowAttributes);
//        btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
//        btnDeletePost = (Button) dialog.findViewById(R.id.btnDeletePost);
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        DatabaseReference reference = db.getReference("JobPost");
//        btnDeletePost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                progressDialog = new ProgressDialog(DetailsPostAdActivity.this);
//                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                progressDialog.show();
//                progressDialog.setTitle("Connecting");
//
//                Runnable progressRunable = new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.cancel();
//                    }
//                };
//                Handler pdCanceller = new Handler();
//                pdCanceller.postDelayed(progressRunable,500);
//                progressDialog.setContentView(R.layout.progress_dialog);
//                progressDialog.getWindow().setBackgroundDrawableResource(
//                        android.R.color.transparent);
//                reference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        reference.child(edtName_Details.getText().toString().trim()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Toast.makeText(DetailsPostAdActivity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });
//
//        dialog.show();
//    }

}
