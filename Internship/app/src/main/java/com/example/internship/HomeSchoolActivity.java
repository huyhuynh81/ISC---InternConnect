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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.internship.Model.AccountCompany;
import com.example.internship.Model.AccountSchool;
import com.example.internship.Model.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class HomeSchoolActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recycler_menu;
    SchoolAdapter adapter;
    Spinner spnFilterCom;
    ArrayList<Student> stds;
    ArrayList<String> name2;
    FirebaseDatabase db;
    DatabaseReference ref, ref1, ref2;
    ImageButton imgSchool;
    TextView txtUsername;
    FirebaseUser user;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_school);
        spnFilterCom = findViewById(R.id.spnFilterCom);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        recycler_menu = findViewById(R.id.recyclere_menu);
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
        imgSchool = (ImageButton) findViewById(R.id.imgSchool);
        imgSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeSchoolActivity.this, CompanyDetailsActivity.class);
                intent.putExtra("acc", Username);
                startActivity(intent);
            }
        });

        name2 = new ArrayList<>();
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = db.getReference();
        databaseReference.child("Major").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childsnapshot : snapshot.getChildren()) {
                    String SchoolName = childsnapshot.child("name").getValue(String.class);
                    name2.add(SchoolName);
                }
                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(HomeSchoolActivity.this, android.R.layout.simple_spinner_item, name2);
                arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spnFilterCom.setAdapter(arrayAdapter2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void loadData() {
        db = FirebaseDatabase.getInstance();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ref1 = db.getReference("Student");
                AccountSchool accountSchool = snapshot.child(userID).getValue(AccountSchool.class);
                    ref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            stds = new ArrayList<>();
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Student std = data.getValue(Student.class);
                                if(std.getSchool().equals(accountSchool.getSchool())){
                                    stds.add(std);
                                }
                            }
                            adapter = new SchoolAdapter(HomeSchoolActivity.this, stds);
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
        getMenuInflater().inflate(R.menu.menu, menu);
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
                if (adapter != null) {
                    adapter.getFilter().filter(s);
                }
                return false;
            }
        });
        return true;
    }

//    public void SearchData(){
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                if(adapter != null){
//                    adapter.getFilter().filter(s);
//                }
//                return false;
//            }
//        });
//    }

}