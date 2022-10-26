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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.internship.Model.Account;
import com.example.internship.Model.JobPost;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recycler_menu;
    CompanyAdapter adapter;
    ArrayList<JobPost> cpns;
    FirebaseDatabase db;
    DatabaseReference ref;
    ImageButton imgUser;
    TextView txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        recycler_menu = findViewById(R.id.recyclere_menu);
        recycler_menu.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(manager);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("JobPost");
        loadData();
        Account Username = (Account) getIntent().getSerializableExtra("acc");;
        txtUsername.setText(Username.getName());
        imgUser = (ImageButton) findViewById(R.id.imgUser);
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UserDetailsActivity.class);
                intent.putExtra("acc",Username);
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
                    JobPost cpn = data.getValue(JobPost.class);
                    cpns.add(cpn);
                }
                adapter = new CompanyAdapter(HomeActivity.this, cpns);
                recycler_menu.setAdapter(adapter);
                adapter.notifyDataSetChanged();
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