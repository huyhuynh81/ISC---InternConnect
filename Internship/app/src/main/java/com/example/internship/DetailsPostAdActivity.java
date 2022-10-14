package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.internship.Model.JobPost;
import com.squareup.picasso.Picasso;

public class DetailsPostAdActivity extends AppCompatActivity {

    ImageView imgLogo_Details;
    EditText edtPosition_Details, edtName_Details, edtLocation_Details, edtSalary_Details, edtGender_Details,
            edtRequired_Details, edtBenefit_Details, edtNumber_Details;
    Button btnUpdatePost, btnDelete;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_post_ad);
        JobPost cpn = (JobPost) getIntent().getSerializableExtra("obj_cpn");;
        edtPosition_Details = findViewById(R.id.edtPosition_Details);
        edtName_Details = findViewById(R.id.edtName_Details);
        edtLocation_Details = findViewById(R.id.edtLocation_Details);
        edtSalary_Details = findViewById(R.id.edtSalary_Details);
        edtGender_Details = findViewById(R.id.edtGender_Details);
        edtRequired_Details = findViewById(R.id.edtRequired_Details);
        edtBenefit_Details = findViewById(R.id.edtBenifit_Details);
        edtNumber_Details = findViewById(R.id.edtNumber_Details);
        imgLogo_Details = findViewById(R.id.imgLogoAd_Details);
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

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdatePost = (Button) findViewById(R.id.btnUpdatePost);

        edtPosition_Details.setEnabled(false);
        edtName_Details.setEnabled(false);
        edtLocation_Details.setEnabled(false);
        edtNumber_Details.setEnabled(false);
        edtSalary_Details.setEnabled(false);
        edtGender_Details.setEnabled(false);
        edtRequired_Details.setEnabled(false);
        edtBenefit_Details.setEnabled(false);

        btnUpdatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(DetailsPostAdActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                onBackPressed();
                edtPosition_Details.setEnabled(true);
                edtName_Details.setEnabled(true);
                edtNumber_Details.setEnabled(true);
                edtSalary_Details.setEnabled(true);
                edtGender_Details.setEnabled(true);
                edtLocation_Details.setEnabled(true);
                edtRequired_Details.setEnabled(true);
                edtBenefit_Details.setEnabled(true);

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Detail_edit.this, SupportActivity.class);
//                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        progressDialog.dismiss();
    }
}
