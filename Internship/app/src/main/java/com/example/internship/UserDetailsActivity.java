package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.internship.Model.Account;

public class UserDetailsActivity extends AppCompatActivity {

    Dialog dialog;
    TextView txtUsername1;
    Button btnLogout, btnInternConnect, btnChinhSach, btnHuy, btnDangXuat, btnDoiMatkhau, btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        btnProfile= (Button) findViewById(R.id.btnProfile);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnInternConnect = (Button) findViewById(R.id.btnInternConnect);
        btnChinhSach = (Button) findViewById(R.id.btnChinhSach);
        btnDoiMatkhau = (Button) findViewById(R.id.btnDoiMatkhau);
        txtUsername1 = (TextView) findViewById(R.id.txtUsername1);
        Account Username = (Account) getIntent().getSerializableExtra("acc");;
        txtUsername1.setText(Username.getName());

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetailsActivity.this, ProfileUserActivity.class);
                intent.putExtra("acc", Username);
                startActivity(intent);
            }
        });

        btnDoiMatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetailsActivity.this, ChangePasswordActivity.class);
                intent.putExtra("acc", Username);
                startActivity(intent);
            }
        });

        dialog = new Dialog(this);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogoutPopup(Gravity.CENTER);
            }
        });

        btnInternConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetailsActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        btnChinhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetailsActivity.this, SecurityActivity.class);
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
                Intent intent = new Intent(UserDetailsActivity.this, MainActivity.class);
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