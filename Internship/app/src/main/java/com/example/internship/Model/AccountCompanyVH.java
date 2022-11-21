package com.example.internship.Model;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship.R;

public class AccountCompanyVH extends RecyclerView.ViewHolder {

    private Context context;

    public TextView txtName_Company_Admin, txtPosition_Admin, txtEmail_Admin;
    public ImageView imgLogo_Admin;
    public CardView cardview;


    public AccountCompanyVH(@NonNull View itemView) {
        super(itemView);
        txtName_Company_Admin = (TextView) itemView.findViewById(R.id.txtName_Company_Admin);
        txtPosition_Admin = (TextView) itemView.findViewById(R.id.txtPosition_Admin);
        txtEmail_Admin = (TextView) itemView.findViewById(R.id.txtEmail_Company_Admin);
        imgLogo_Admin = (ImageView) itemView.findViewById(R.id.imgLogo_admin);
        cardview = (CardView) itemView.findViewById(R.id.card_com_admin);
    }
}