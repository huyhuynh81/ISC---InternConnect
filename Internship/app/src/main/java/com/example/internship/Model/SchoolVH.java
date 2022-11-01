package com.example.internship.Model;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship.R;

public class SchoolVH extends RecyclerView.ViewHolder {

    private Context context;

    public TextView txtEmail, txtName, txtMajor;
    public ImageView imgLogo;
    public CardView cardV;

    public SchoolVH(@NonNull View itemView) {
        super(itemView);

        txtEmail = itemView.findViewById(R.id.txtEmail);
        txtName = itemView.findViewById(R.id.txtName);
        txtMajor = itemView.findViewById(R.id.txtMajor);
    }
}
