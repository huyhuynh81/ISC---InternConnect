package com.example.internship.Model;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship.R;

public class SchoolVH extends RecyclerView.ViewHolder {

    private Context context;

    public TextView txtEmail, txtName, txtMajor;
    public CheckBox chbVerifyHome;
    public ImageView imgUncheck, imgcheck;
    public CardView cardV;

    public SchoolVH(@NonNull View itemView) {
        super(itemView);

        txtEmail = itemView.findViewById(R.id.txtEmail);
        txtName = itemView.findViewById(R.id.txtName);
        txtMajor = itemView.findViewById(R.id.txtMajor);
        cardV = itemView.findViewById(R.id.cardV);
        imgcheck = itemView.findViewById(R.id.imgCheck);
        imgUncheck = itemView.findViewById(R.id.imgUncheck);
        //chbVerifyHome = itemView.findViewById(R.id.chbVerifyHome);
    }
}
