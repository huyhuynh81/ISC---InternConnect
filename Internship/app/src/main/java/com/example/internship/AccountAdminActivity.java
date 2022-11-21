package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.internship.Model.Account;
import com.example.internship.Model.AccountCompany;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class AccountAdminActivity extends AppCompatActivity {
    RecyclerView recycler_menu;
    AccountAdminAdapter adapter;
    ArrayList<Account> cpns;
    FirebaseDatabase db;
    DatabaseReference ref;
    ImageButton add_admin;
    TextView txtUsername;
    ChipNavigationBar chipNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_admin);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        recycler_menu = findViewById(R.id.recyclere_account_admin);
        recycler_menu.setHasFixedSize(true);
        LinearLayoutManager manager6 = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(manager6);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Account");
        AccountCompany Username = (AccountCompany) getIntent().getSerializableExtra("acc");
        txtUsername.setText(Username.getName());
        loadData();
        chipNavigationBar.setItemSelected(R.id.Home,true);
        recycler_menu.setOnTouchListener(new TranslateAnimationUtil(AccountAdminActivity.this,chipNavigationBar));
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.Home:
                        break;
                    case R.id.Addpost:
                        Intent intent = new Intent(AccountAdminActivity.this, AddAccountAdminActivity.class);
                        intent.putExtra("acc", Username);
                        startActivity(intent);
                        break;
                    case R.id.Back:
                        Intent intent1 = new Intent(AccountAdminActivity.this, HomeAdminActivity.class);
                        intent1.putExtra("acc",Username);
                        startActivity(intent1);
                        CustomIntent.customType(AccountAdminActivity.this,"fadein-to-fadeout");
                        break;
                }

            }
        });
    }
    public void loadData(){
        db = FirebaseDatabase.getInstance();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cpns = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Account cpn = data.getValue(Account.class);
                    cpns.add(cpn);
                }
                adapter = new AccountAdminAdapter(AccountAdminActivity.this, cpns);
                recycler_menu.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}