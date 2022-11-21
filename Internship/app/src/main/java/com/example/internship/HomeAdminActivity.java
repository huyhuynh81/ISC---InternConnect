package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.internship.Model.AccountCompany;
import com.example.internship.Model.JobPost;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class HomeAdminActivity extends AppCompatActivity {

    Dialog dialog;
    RelativeLayout imgSchoolAd, imgCompanyAd, imgLogoutAd, imgAccountAd, imgSettingAd;
    FirebaseDatabase db;
    DatabaseReference ref, ref1;
    ImageButton imgAdmin;
    TextView txtUsername;
    ChipNavigationBar chipNavigationBar;
    FirebaseUser user;
    private String userID;
    Button btnHuy, btnDangXuat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Account");

        dialog = new Dialog(this);

        AccountCompany Username = (AccountCompany) getIntent().getSerializableExtra("acc");
        txtUsername.setText(Username.getName());
        imgCompanyAd = (RelativeLayout) findViewById(R.id.imgCompanyAd);
        imgCompanyAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAdminActivity.this, HomeCompanyAdminActivity.class);
                intent.putExtra("acc",Username);
                startActivity(intent);
            }
        });


        imgSchoolAd = (RelativeLayout) findViewById(R.id.imgSchoolAd);
        imgSchoolAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAdminActivity.this, HomeMajorActivity.class);
                intent.putExtra("acc",Username);
                startActivity(intent);
            }
        });

        imgLogoutAd = (RelativeLayout) findViewById(R.id.imgLogoutAd);
        imgLogoutAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogoutPopup(Gravity.CENTER);
            }
        });

        imgAccountAd = (RelativeLayout) findViewById(R.id.imgAccountAd);
        imgAccountAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAdminActivity.this, AccountAdminActivity.class);
                intent.putExtra("acc",Username);
                startActivity(intent);
            }
        });

    }

    private void openLogoutPopup(int gravity) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_popup_logout);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        btnHuy = (Button) dialog.findViewById(R.id.btnHuy);
        btnDangXuat = (Button) dialog.findViewById(R.id.btnDangXuat);

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAdminActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}