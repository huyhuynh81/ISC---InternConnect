package com.example.internship;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.internship.Model.AccountCompany;
import com.example.internship.Model.Company;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddComAdminActivity extends AppCompatActivity {
    ImageView imgLogoAd_Add_Admin;
    EditText edtEmail_Add_Admin, edtName_Add_Admin, edtLocation_Add_Admin, edtPhone_Add_Admin;
    Button btnAddPost_Admin_Add;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseUser user;
    FirebaseAuth auth;
    DatabaseReference reference;
    DatabaseReference reference2;
    private  String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_com_admin);

        edtEmail_Add_Admin = findViewById(R.id.edtEmail_Add_Admin);
        edtName_Add_Admin = findViewById(R.id.edtName_Add_Admin);
        edtLocation_Add_Admin = findViewById(R.id.edtLocation_Add_Admin);
        edtPhone_Add_Admin = findViewById(R.id.edtPhone_Add_Admin);
        imgLogoAd_Add_Admin = findViewById(R.id.imgLogoAd_Add_Admin);
        btnAddPost_Admin_Add = findViewById(R.id.btnUpdatePost_Admin_Add);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Company");
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Company");
        imgLogoAd_Add_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
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
        AccountCompany Username = (AccountCompany) getIntent().getSerializableExtra("acc");
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (data.getData() != null) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 60, 60, true);
                    imgLogoAd_Add_Admin.setImageBitmap(bitmap1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                btnAddPost_Admin_Add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UploadFile(data.getData());
                                Toast.makeText(AddComAdminActivity.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomeCompanyAdminActivity.class);
                                startActivity(intent);
                                finish();
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
                        Company jb = new Company(
                                edtEmail_Add_Admin.getText().toString(),
                                edtLocation_Add_Admin.getText().toString(),
                                url.toString(),
                                edtName_Add_Admin.getText().toString(),
                                edtPhone_Add_Admin.getText().toString());
                        databaseReference.child(edtName_Add_Admin.getText().toString()).setValue(jb);
                    }
                });
    }
}