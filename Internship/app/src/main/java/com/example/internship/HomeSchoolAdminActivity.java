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

import com.example.internship.Model.AccountCompany;
import com.example.internship.Model.School;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class HomeSchoolAdminActivity extends AppCompatActivity {

    androidx.appcompat.widget.SearchView searchView;
    RecyclerView recycler_menu;
    SchoolAdminAdapter adapter;
    ArrayList<School> sch;
    FirebaseDatabase db;
    BottomNavigationView bottomNavigationView;
    DatabaseReference ref, ref1;
    ImageButton imgAdmin;
    FloatingActionButton imgAddPost;
    TextView txtUsername;
    ChipNavigationBar chipNavigationBar;
    FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_major);

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        searchView = findViewById(R.id.sr_Job);
        recycler_menu = findViewById(R.id.recyclere_menu);
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        recycler_menu.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(manager);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("School");

        loadData();
        AccountCompany Username = (AccountCompany) getIntent().getSerializableExtra("acc");
        txtUsername.setText(Username.getName());
        imgAdmin = (ImageButton) findViewById(R.id.imgAdmin);
        imgAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeSchoolAdminActivity.this, CompanyDetailsActivity.class);
                intent.putExtra("acc", Username);
                startActivity(intent);
            }
        });
        chipNavigationBar.setItemSelected(R.id.Home, true);
        recycler_menu.setOnTouchListener(new TranslateAnimationUtil(HomeSchoolAdminActivity.this, chipNavigationBar));
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.Home:
                        break;
                    case R.id.Addpost:
                        Intent intent = new Intent(HomeSchoolAdminActivity.this, AddSchoolAdminActivity.class);
                        intent.putExtra("acc", Username);
                        startActivity(intent);
                        break;
                    case R.id.Back:
                        Intent intent1 = new Intent(HomeSchoolAdminActivity.this, HomeAdminActivity.class);
                        intent1.putExtra("acc", Username);
                        startActivity(intent1);
                        CustomIntent.customType(HomeSchoolAdminActivity.this, "fadein-to-fadeout");
                        break;
                }

            }
        });
    }

    public void loadData() {
        db = FirebaseDatabase.getInstance();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sch = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    School school = data.getValue(School.class);
                    sch.add(school);
                }
                adapter = new SchoolAdminAdapter(HomeSchoolAdminActivity.this, sch);
                recycler_menu.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}