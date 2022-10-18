package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.internship.Model.Account;
import com.example.internship.Model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ProfileUserActivity extends AppCompatActivity {
    Spinner spnMajor;
    DatabaseReference databaseReference;
    List<String> name;
    EditText edtNameUser, edtCode, edtAcademicYear, edtAddress, edtEmail;
    Button btnUpdateProfile;
    RadioButton male, female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        edtNameUser = findViewById(R.id.edtNameUser);
        edtCode = findViewById(R.id.edtCode);
        edtAcademicYear = findViewById(R.id.edtAcademicYear);
        edtAddress = findViewById(R.id.edtAddress);
        edtEmail = findViewById(R.id.edtEmail);
        male = findViewById(R.id.rdoMale);
        female = findViewById(R.id.rdoFemale);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        spnMajor = findViewById(R.id.spnMajor);
        Account Username = (Account) getIntent().getSerializableExtra("acc");
        edtNameUser.setText(Username.getName());
        edtEmail.setText(Username.getEmail());
        name = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Major").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childsnapshot : snapshot.getChildren()) {
                    String majorname = childsnapshot.child("name").getValue(String.class);
                    name.add(majorname);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ProfileUserActivity.this, android.R.layout.simple_spinner_item, name);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spnMajor.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Student");
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (isValidEmail(edtEmail.getText().toString())) {
                            if (male.isChecked()) {
                                Student student = new Student(edtAcademicYear.getText().toString(), edtAddress.getText().toString(), edtEmail.getText().toString(), "Nam", spnMajor.getSelectedItem().toString(), edtNameUser.getText().toString());
                                databaseReference.child(edtCode.getText().toString()).setValue(student);
                                Toast.makeText(ProfileUserActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            if (female.isChecked()) {
                                Student student = new Student(edtAcademicYear.getText().toString(), edtAddress.getText().toString(), edtEmail.getText().toString(), "Nữ", spnMajor.getSelectedItem().toString(), edtNameUser.getText().toString());
                                databaseReference.child(edtCode.getText().toString()).setValue(student);
                                Toast.makeText(ProfileUserActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else {
                            Toast.makeText(ProfileUserActivity.this, "Wrong email", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private boolean isValidEmail(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}