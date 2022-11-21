package com.example.internship.Model;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship.R;

public class MajorVH extends RecyclerView.ViewHolder {

    public TextView txtNameMajor;
    public TextView txtDateAdd;
    public ImageButton imgDelete;

    public MajorVH(@NonNull View itemView) {
        super(itemView);
        txtNameMajor = (TextView) itemView.findViewById(R.id.txtNameMajor);
        txtDateAdd = (TextView) itemView.findViewById(R.id.txtDateAdd);
        imgDelete = (ImageButton) itemView.findViewById(R.id.imgDelete);
    }
}
