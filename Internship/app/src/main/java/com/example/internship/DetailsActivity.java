package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.internship.Model.JobPost;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    TextView txtPosition_Details, txtName_Details, txtLocation_Details, txtSalary_Details, txtGender_Details,txtRequired_Details, txtBenefit_Details, txtNumber_Details;
    ImageView imgLogo_Details;
    Button btnUngTuyen, btnHoTro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        JobPost cpn = (JobPost) getIntent().getSerializableExtra("obj_cpn");;
        txtPosition_Details = findViewById(R.id.txtPosition_Details);
        txtName_Details = findViewById(R.id.txtName_Details);
        txtLocation_Details = findViewById(R.id.txtLocation_Details);
        txtSalary_Details = findViewById(R.id.txtSalary_Details);
        txtGender_Details = findViewById(R.id.txtGender_Details);
        txtRequired_Details = findViewById(R.id.txtRequired_Details);
        txtBenefit_Details = findViewById(R.id.txtBenifit_Details);
        txtNumber_Details = findViewById(R.id.txtNumber_Details);
        imgLogo_Details = findViewById(R.id.imgLogo_Details);
        txtPosition_Details.setText(cpn.getPosition());
        txtName_Details.setText(cpn.getName());
        txtLocation_Details.setText(cpn.getLocation());
        txtSalary_Details.setText(cpn.getSalary());
        txtGender_Details.setText(cpn.getGender());
        txtRequired_Details.setText(cpn.getRequired());
        txtBenefit_Details.setText(cpn.getBenefit());
        txtNumber_Details.setText(cpn.getNumber());
        String image = cpn.getLogo();
        Picasso.with(this).load(image).into(imgLogo_Details);

        btnUngTuyen = (Button) findViewById(R.id.btnUngTuyen);
        btnHoTro = (Button) findViewById(R.id.btnHoTro);
        btnUngTuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });

        btnHoTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, SupportActivity.class);
                startActivity(intent);
            }
        });
    }
}