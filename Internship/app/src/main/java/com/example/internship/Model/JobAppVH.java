package com.example.internship.Model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship.R;

import java.text.DateFormat;
import java.util.Calendar;

public class JobAppVH  extends RecyclerView.ViewHolder{
    private Context context;
    public TextView txtNameCom;
    public TextView txtCV;
    public TextView txtName;
    public TextView txtMajor;
    public TextView txtNameSchool;
    public TextView txtDateApp;
    public CardView cardview;
    public ImageButton pdfButton;

    public JobAppVH(@NonNull View itemView) {
        super(itemView);
        Calendar calendar  = Calendar.getInstance();

        txtNameCom = (TextView) itemView.findViewById(R.id.txtNameOfCompany);
        txtCV = (TextView) itemView.findViewById(R.id.txtCV);
        txtMajor= (TextView) itemView.findViewById(R.id.txtMajor);
        txtNameSchool =(TextView) itemView.findViewById(R.id.txtName_School);
        txtName = (TextView) itemView.findViewById(R.id.txtUsername);
        txtDateApp = (TextView) itemView.findViewById(R.id.txtDateApp);
        cardview = (CardView) itemView.findViewById(R.id.History_layout);
        pdfButton = (ImageButton)  itemView.findViewById(R.id.PDFimg);
    }

}
