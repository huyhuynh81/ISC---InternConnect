package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.internship.Model.AccountCompany;
import com.example.internship.Model.JobApp;
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

public class JobAppActivity extends AppCompatActivity {
    RecyclerView recycler_menu;
    JobAppAdapter adapter;
    ArrayList<JobApp> apps;
    FirebaseDatabase db;
    DatabaseReference ref, ref1;
    FirebaseUser user;
    private String userID;
    ChipNavigationBar chipNavigationBar;
    TextView txtUsername;
    SearchView searchView;
    ImageButton imgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_app);
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        txtUsername = findViewById(R.id.txtUsername);
        chipNavigationBar.setItemSelected(R.id.History,true);
        AccountCompany Username = (AccountCompany) getIntent().getSerializableExtra("acc");
        txtUsername.setText(Username.getName());
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.Home:
                        Intent intent1 = new Intent(JobAppActivity.this, HomeCompanyActivity.class);
                        intent1.putExtra("acc",Username);
                        startActivity(intent1);
                        CustomIntent.customType(JobAppActivity.this,"fadein-to-fadeout");
                        break;
                    case R.id.Addpost:
                        Intent intent = new Intent(JobAppActivity.this, AddPostActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.History:
                        break;
                }

            }
        });
        imgUser = findViewById(R.id.imgUser);
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobAppActivity.this, CompanyDetailsActivity.class);
                intent.putExtra("acc",Username);
                startActivity(intent);
            }
        });
        recycler_menu = findViewById(R.id.recycler_JobApply);
        recycler_menu.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(manager);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Account");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(manager1);
        loadData();
    }
    private void loadData() {
        db = FirebaseDatabase.getInstance();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ref1 = db.getReference("JobApp");
                AccountCompany accountCompany = snapshot.child(userID).getValue(AccountCompany.class);
                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        apps = new ArrayList<>();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            JobApp jba = data.getValue(JobApp.class);
                            if(accountCompany.getCompany().equals(jba.getNameCom())){
                                apps.add(jba);
                            }
                        }
                        adapter = new JobAppAdapter(JobAppActivity.this, apps);
                        recycler_menu.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.sr_Job).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(adapter != null){
                    adapter.getFilter().filter(s);
                }
                return false;
            }
        });
        return true;
    }
}




