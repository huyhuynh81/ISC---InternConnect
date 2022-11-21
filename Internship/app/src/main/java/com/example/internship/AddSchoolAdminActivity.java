package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.internship.Model.Major;
import com.example.internship.Model.School;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddSchoolAdminActivity extends AppCompatActivity {

    EditText edtNameSchool, edtDateSchool;
    Button btnAddSchool;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_school_admin);
        edtNameSchool = findViewById(R.id.edtNameSchool);
        edtDateSchool = findViewById(R.id.edtDateSchool);
        btnAddSchool = findViewById(R.id.btnAddSchool);
        Locale id = new Locale("in", "ID");
        SimpleDateFormat sim = new SimpleDateFormat("dd/MM/yyyy", id);
        Date d = new Date();
        String s  = sim.format(d.getTime());
        edtDateSchool.setText(s);
        edtDateSchool.setEnabled(false);

        databaseReference = FirebaseDatabase.getInstance().getReference("School");
        btnAddSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                School school = new School(edtNameSchool.getText().toString(), edtDateSchool.getText().toString());
                databaseReference.child(edtNameSchool.getText().toString()).setValue(school)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(AddSchoolAdminActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            }
        });

    }
}