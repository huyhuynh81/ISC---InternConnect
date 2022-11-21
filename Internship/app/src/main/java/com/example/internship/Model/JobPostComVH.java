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

public class JobPostComVH extends RecyclerView.ViewHolder {

    private Context context;

    public TextView txtName_Company, txtPosition, txtNumber, txtIdPost;
    public ImageView imgLogo;
    public CardView cardview;
    public SwipeRevealLayout SwipeRevealLayout;
    public RelativeLayout layout_delete;
    public ImageView imgxoa;

    public JobPostComVH(@NonNull View itemView) {
        super(itemView);
        txtName_Company = (TextView) itemView.findViewById(R.id.txtName_Company);
        txtPosition = (TextView) itemView.findViewById(R.id.txtPosition);
        txtNumber = (TextView) itemView.findViewById(R.id.txtNumber);
        imgLogo = (ImageView) itemView.findViewById(R.id.imgLogo);
        cardview = (CardView) itemView.findViewById(R.id.cardV);
        SwipeRevealLayout=(SwipeRevealLayout)itemView.findViewById(R.id.SwipeRevealLayout);
        layout_delete = (RelativeLayout) itemView.findViewById(R.id.layout_delete);
        imgxoa = (ImageView) itemView.findViewById(R.id.imgDelete);
        txtIdPost = (TextView) itemView.findViewById(R.id.txtIdPost);

    }
}