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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internship.Model.Account;
import com.example.internship.Model.AccountCompany;
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

public class AccountAdminActivity extends AppCompatActivity {
    RecyclerView recycler_menu;
    AccountAdminAdapter adapter;
    ArrayList<Account> cpns;
    FirebaseDatabase db;
    DatabaseReference ref;
    ImageButton add_admin;
    TextView txtUsername;
    ChipNavigationBar chipNavigationBar;
    androidx.appcompat.widget.SearchView searchView;
    FirebaseUser user;
    DatabaseReference reference;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_admin);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        recycler_menu = findViewById(R.id.recyclere_account_admin);
        searchView = findViewById(R.id.sr_Job);
        recycler_menu.setHasFixedSize(true);
        LinearLayoutManager manager6 = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(manager6);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Account");

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Account");
        userID = user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account accProfile = snapshot.getValue(Account.class);
                if (accProfile != null) {
                    String UserName = accProfile.getName();
                    txtUsername.setText(UserName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountAdminActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });

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