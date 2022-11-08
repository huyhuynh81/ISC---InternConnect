package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.example.internship.Model.Account;
import com.example.internship.Model.AccountCompany;
import com.example.internship.Model.JobApp;
import com.example.internship.Model.Student;
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

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recycler_menu;
    JobAppAdapter adapter;
    ArrayList<JobApp> hisUser;
    FirebaseDatabase db;
    DatabaseReference ref, ref1;
    FirebaseUser user;
    private String userID;
    ChipNavigationBar chipNavigationBar;
    TextView txtUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
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
                        Intent intent1 = new Intent(HistoryActivity.this, HomeActivity.class);
                        intent1.putExtra("acc",Username);
                        startActivity(intent1);
                        CustomIntent.customType(HistoryActivity.this,"fadein-to-fadeout");
                        break;
                    case R.id.Addpost:
                        String url = "https://www.cakeresume.com/cv-maker?ref=navs_cv_builder";
                        Intent intent= new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        break;
                    case R.id.History:
                        break;
                }

            }
        });
        recycler_menu = findViewById(R.id.recycler_HistoryUser);
        recycler_menu.setHasFixedSize(true);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Account");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(manager2);
        loadData();
    }
    private void loadData() {
        db = FirebaseDatabase.getInstance();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ref1 = db.getReference("JobApp");
                Account accountSchool = snapshot.child(userID).getValue(Account.class);
                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        hisUser = new ArrayList<>();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            JobApp jba = data.getValue(JobApp.class);
                            if(accountSchool.getName().equals(jba.getNameUser())){
                                hisUser.add(jba);
                            }
                        }
                        adapter = new JobAppAdapter(HistoryActivity.this, hisUser);
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
}

