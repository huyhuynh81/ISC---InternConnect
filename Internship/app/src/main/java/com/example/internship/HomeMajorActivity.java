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
import com.example.internship.Model.JobPost;
import com.example.internship.Model.Major;
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

public class HomeMajorActivity extends AppCompatActivity {

    androidx.appcompat.widget.SearchView searchView;
    RecyclerView recycler_menu;
    MajorAdapter adapter;
    ArrayList<Major> mjs;
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
        ref = db.getReference("Major");

        loadData();
        AccountCompany Username = (AccountCompany) getIntent().getSerializableExtra("acc");
        txtUsername.setText(Username.getName());
        imgAdmin = (ImageButton) findViewById(R.id.imgAdmin);
        imgAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeMajorActivity.this, CompanyDetailsActivity.class);
                intent.putExtra("acc", Username);
                startActivity(intent);
            }
        });
        chipNavigationBar.setItemSelected(R.id.Home, true);
        recycler_menu.setOnTouchListener(new TranslateAnimationUtil(HomeMajorActivity.this, chipNavigationBar));
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.Home:
                        break;
                    case R.id.Addpost:
                        Intent intent = new Intent(HomeMajorActivity.this, AddPostActivity.class);
                        intent.putExtra("acc", Username);
                        startActivity(intent);
                        break;
                    case R.id.Back:
                        Intent intent1 = new Intent(HomeMajorActivity.this, HomeAdminActivity.class);
                        intent1.putExtra("acc", Username);
                        startActivity(intent1);
                        CustomIntent.customType(HomeMajorActivity.this, "fadein-to-fadeout");
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
                mjs = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Major major = data.getValue(Major.class);
                    mjs.add(major);
                }
                adapter = new MajorAdapter(HomeMajorActivity.this, mjs);
                recycler_menu.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}