package com.example.internship;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internship.Model.JobPost;
import com.example.internship.Model.putImage;
import com.example.internship.Model.putPDF;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddPostActivity extends AppCompatActivity {
    EditText addPosition_Details, addName_Details, addLocation_Details, addSalary_Details, addGender_Details, addRequired_Details, addBenifit_Details, addNumber_Details;
    ImageButton addimgLogo_Details;
    Button btnAdd, btnBack;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    TextView txtLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("JobPost");
        addPosition_Details = findViewById(R.id.addPosition_Details);
        addName_Details = findViewById(R.id.addName_Details);
        addLocation_Details = findViewById(R.id.addLocation_Details);
        addSalary_Details = findViewById(R.id.addSalary_Details);
        addGender_Details = findViewById(R.id.addGender_Details);
        addRequired_Details = findViewById(R.id.addRequired_Details);
        addBenifit_Details = findViewById(R.id.addBenifit_Details);
        addNumber_Details = findViewById(R.id.addNumber_Details);
        addimgLogo_Details = findViewById(R.id.addimgLogo_Details);
        txtLogo = findViewById(R.id.txtLogo);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addimgLogo_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(AddPostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddPostActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    selectImage();
                }
            }
        });

    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture..."), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (data.getData() != null) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 60, 60, true);
                    addimgLogo_Details.setImageBitmap(bitmap1);
                    txtLogo.setVisibility(View.GONE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UploadFile(data.getData());
                                Toast.makeText(AddPostActivity.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            } else
                Toast.makeText(this, "NO FILE CHOSEN", Toast.LENGTH_SHORT).show();
        }
    }

    private void UploadFile(Uri data) {
        storageReference = storageReference.child(System.currentTimeMillis() + ".png");
        storageReference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri url = uriTask.getResult();
                        JobPost jb = new JobPost(addBenifit_Details.getText().toString(),
                                addGender_Details.getText().toString(),
                                addLocation_Details.getText().toString(),
                                url.toString(),
                                addName_Details.getText().toString(),
                                addNumber_Details.getText().toString(),
                                addPosition_Details.getText().toString(),
                                addRequired_Details.getText().toString(),
                                addSalary_Details.getText().toString()
                        );
                        databaseReference.child(addName_Details.getText().toString()).setValue(jb);
                    }
                });
    }

}
