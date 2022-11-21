package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.internship.Model.Major;
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

public class AddMajorActivity extends AppCompatActivity {

    EditText edtNameMajor, edtDateMajor;
    Button btnAddMajor;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_major);
        edtNameMajor = findViewById(R.id.edtNameMajor);
        edtDateMajor = findViewById(R.id.edtDateMajor);
        btnAddMajor = findViewById(R.id.btnAddMajor);
        Locale id = new Locale("in", "ID");
        SimpleDateFormat sim = new SimpleDateFormat("dd/MM/yyyy", id);
        Date d = new Date();
        String s  = sim.format(d.getTime());
        edtDateMajor.setText(s);
        edtDateMajor.setEnabled(false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Major");
        btnAddMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Major major = new Major(edtNameMajor.getText().toString(), edtDateMajor.getText().toString());
                databaseReference.child(edtNameMajor.getText().toString()).setValue(major)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(AddMajorActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            }
        });

    }
}