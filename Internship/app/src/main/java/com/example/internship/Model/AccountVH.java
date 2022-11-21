package com.example.internship.Model;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.internship.R;

public class AccountVH  extends RecyclerView.ViewHolder {
    private Context context;

    public TextView txtName_Admin, txtRole_Admin, txtEmail_Admin, txtPhone_Admin;
    public CardView cardview;
    public SwipeRevealLayout SwipeRevealLayout;
    public RelativeLayout layout_delete;
    public ImageView imgxoa;

    public AccountVH(@NonNull View itemView) {
        super(itemView);
        txtEmail_Admin = (TextView) itemView.findViewById(R.id.txtEmail_Admin);
        txtRole_Admin = (TextView) itemView.findViewById(R.id.txtRole_Admin);
        txtName_Admin = (TextView) itemView.findViewById(R.id.txtName_Admin);
        txtPhone_Admin = (TextView) itemView.findViewById(R.id.txtPhone_Admin);
        cardview = (CardView) itemView.findViewById(R.id.cardAccount_Admin);
//        SwipeRevealLayout= (SwipeRevealLayout) itemView.findViewById(R.id.SwipeRevealLayout);
//        layout_delete = (RelativeLayout) itemView.findViewById(R.id.layout_delete);
//        imgxoa = (ImageView) itemView.findViewById(R.id.imgDelete);
    }
}
