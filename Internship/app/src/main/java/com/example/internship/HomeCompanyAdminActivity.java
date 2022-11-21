package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.internship.Model.AccountCompany;
import com.example.internship.Model.Company;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class HomeCompanyAdminActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recycler_menu;
    CompanyAdapter adapter;
    ArrayList<Company> cpns;
    FirebaseDatabase db;
    DatabaseReference ref;
    ImageButton Add_Com;
    TextView txtUsername;
    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_admin);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        recycler_menu = findViewById(R.id.recyclere_com_admin);
        recycler_menu.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(manager);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Company");
        AccountCompany Username = (AccountCompany) getIntent().getSerializableExtra("acc");
        txtUsername.setText(Username.getName());

        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        chipNavigationBar.setItemSelected(R.id.Home,true);
        recycler_menu.setOnTouchListener(new TranslateAnimationUtil(HomeCompanyAdminActivity.this,chipNavigationBar));
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.Home:
                        break;
                    case R.id.Addpost:
                        Intent intent = new Intent(HomeCompanyAdminActivity.this, AddComAdminActivity.class);
                        intent.putExtra("acc",Username);
                        startActivity(intent);
                        break;
                    case R.id.Back:
                        Intent intent1 = new Intent(HomeCompanyAdminActivity.this, HomeAdminActivity.class);
                        intent1.putExtra("acc",Username);
                        startActivity(intent1);
                        CustomIntent.customType(HomeCompanyAdminActivity.this,"fadein-to-fadeout");
                        break;
                }

            }
        });

        loadData();
        Add_Com = findViewById(R.id.Add_Com);

        Add_Com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCompanyAdminActivity.this,AddComAdminActivity.class);
                startActivity(intent);
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
                    Company cpn = data.getValue(Company.class);
                    cpns.add(cpn);
                }
                adapter = new CompanyAdapter(HomeCompanyAdminActivity.this, cpns);
                recycler_menu.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}