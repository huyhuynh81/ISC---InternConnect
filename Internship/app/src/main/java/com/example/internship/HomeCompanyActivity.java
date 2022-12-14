package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.internship.Model.JobPost;
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

public class HomeCompanyActivity extends AppCompatActivity {
    androidx.appcompat.widget.SearchView searchView;
    RecyclerView recycler_menu;
    JobPostComAdapter adapter;
    ArrayList<JobPost> cpns;
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
        setContentView(R.layout.activity_home_company);
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
        ref = db.getReference("Account");

        loadData();
        AccountCompany Username = (AccountCompany) getIntent().getSerializableExtra("acc");
        txtUsername.setText(Username.getName());
        imgAdmin = (ImageButton) findViewById(R.id.imgAdmin);
        imgAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCompanyActivity.this, CompanyDetailsActivity.class);
                intent.putExtra("acc",Username);
                startActivity(intent);
            }
        });
        chipNavigationBar.setItemSelected(R.id.Home,true);
        recycler_menu.setOnTouchListener(new TranslateAnimationUtil(HomeCompanyActivity.this,chipNavigationBar));
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.Home:
                        break;
                    case R.id.Addpost:
                        Intent intent = new Intent(HomeCompanyActivity.this, AddPostActivity.class);
                        intent.putExtra("acc", Username);
                        startActivity(intent);
                        break;
                    case R.id.History:
                        Intent intent1 = new Intent(HomeCompanyActivity.this, JobAppActivity.class);
                        intent1.putExtra("acc",Username);
                        startActivity(intent1);
                        CustomIntent.customType(HomeCompanyActivity.this,"fadein-to-fadeout");
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
                ref1 = db.getReference("JobPost");
                AccountCompany accountCompany = snapshot.child(userID).getValue(AccountCompany.class);
                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cpns = new ArrayList<>();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            JobPost cpn = data.getValue(JobPost.class);
                            if(cpn.getName().equals(accountCompany.getCompany())){
                                cpns.add(cpn);
                            }
                        }
                        adapter = new JobPostComAdapter(HomeCompanyActivity.this, cpns);
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
        searchView = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.sr_Job).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
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
