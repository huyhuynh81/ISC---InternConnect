package com.example.internship.Model;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship.R;

public class SchoolVH extends RecyclerView.ViewHolder {

    public TextView txtNameSchool;
    public TextView txtDateAdd;
    public ImageButton imgDelete;

    public SchoolVH(@NonNull View itemView) {
        super(itemView);
        txtNameSchool = (TextView) itemView.findViewById(R.id.txtNameSchool);
        txtDateAdd = (TextView) itemView.findViewById(R.id.txtDateAdd);
        imgDelete = (ImageButton) itemView.findViewById(R.id.imgDelete);
    }
}
