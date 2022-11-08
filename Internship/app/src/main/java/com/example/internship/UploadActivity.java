package com.example.internship;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.internship.Model.Account;
import com.example.internship.Model.Student;
import com.example.internship.Model.putPDF;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class UploadActivity extends AppCompatActivity {

    Button btnUpload, btnApply, btnBack, btnQuaylai;
    TextView txtPath;
    Dialog dialog;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ActivityResultLauncher<Intent> resultLauncher;
    ViewFlipper simpleFlipper;
    FirebaseUser user;
    FirebaseAuth auth;
    DatabaseReference reference;
    DatabaseReference reference2;
    private  String userID;
    String major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        dialog = new Dialog(this);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnApply = (Button) findViewById(R.id.btnApply);
        btnQuaylai = (Button) findViewById(R.id.btnQuaylai);
        txtPath = (TextView) findViewById(R.id.txtPath);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("JobApp");
        simpleFlipper = (ViewFlipper) findViewById(R.id.simpleFlipper);
        simpleFlipper.setAutoStart(true);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Account");
        userID = user.getUid();

        reference2 = FirebaseDatabase.getInstance().getReference("Student");



        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UploadActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    selectPDF();
                }
            }
        });

    }

    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF Files..."), 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (data.getData() != null) {
                txtPath.setText(data.getDataString()
                        .substring(data.getDataString().lastIndexOf("/") + 1));
                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UploadFile(data.getData());
                    }
                });
            } else
                Toast.makeText(this, "NO FILE CHOSEN", Toast.LENGTH_SHORT).show();
        }
    }

    private void createNotification(){
        String id = "my_channel_id_01";
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = manager.getNotificationChannel(id);
            if(channel==null){
                channel = new NotificationChannel(id,"Chanel Title",NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("[Channel descriptioin]");
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100,1000,200,340});
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                manager.createNotificationChannel(channel);

            }
        }
        Intent notificationIntent = new Intent(this,NotificationActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this,1,notificationIntent,PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id)
                .setSmallIcon(R.drawable.logo_intern)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.imgsuccess))
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.imgsuccess))
                        .bigLargeIcon(null))
                .setContentTitle("ISC | Intern Connect")
                .setContentText("Bạn đã nộp đơn thành công!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{100,1000,200,340})
                .setAutoCancel(false)
                .setTicker("Notification");
        builder.setContentIntent(contentIntent);
        NotificationManagerCompat m = NotificationManagerCompat.from(getApplicationContext());
        m.notify(1,builder.build());
    }

    private void openApplyPopup(int gravity) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_popup_apply);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        btnBack = (Button) dialog.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void openApplyPopupVerify(int gravity) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_popup_verify);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        btnBack = (Button) dialog.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    private void UploadFile(Uri data) {
        String intent = getIntent().getStringExtra("com_name");
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reference2.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Student student = snapshot.getValue(Student.class);
                        if(student.getVerify().equals("true")){
                            storageReference = storageReference.child("Upload/" + System.currentTimeMillis() + ".pdf");
                            storageReference.putFile(data)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            createNotification();
                                            openApplyPopup(Gravity.CENTER);
                                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                            while (!uriTask.isComplete()) ;
                                            Uri url = uriTask.getResult();
                                            Date d = new Date();
                                            CharSequence s  = DateFormat.format("dd/MM/yyyy", d.getTime());
                                            putPDF putPDF = new putPDF(txtPath.getText().toString(), url.toString(), student.getName(), intent, student.getMajor(), student.getSchool(),s.toString());
                                            databaseReference.child(Objects.requireNonNull(databaseReference.push().getKey())).setValue(putPDF);
                                        }
                                    });
                        }
                        else if(student.getVerify().equals("false")){
                            openApplyPopupVerify(Gravity.CENTER);
                        }
                        else if(student.getVerify().isEmpty()){
                            openApplyPopupVerify(Gravity.CENTER);
                        }

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