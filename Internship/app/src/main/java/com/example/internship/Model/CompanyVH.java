package com.example.internship.Model;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.internship.HomeActivity;
import com.example.internship.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CompanyVH extends RecyclerView.ViewHolder {

    private Context context;

    public TextView txtName_Company_Admin, txtPosition_Admin, txtEmail_Admin;
    public ImageView imgLogo_Admin;
    public CardView cardview;


    public CompanyVH(@NonNull View itemView) {
        super(itemView);
        txtName_Company_Admin = (TextView) itemView.findViewById(R.id.txtName_Company_Admin);
        txtPosition_Admin = (TextView) itemView.findViewById(R.id.txtPosition_Admin);
        txtEmail_Admin = (TextView) itemView.findViewById(R.id.txtEmail_Company_Admin);
        imgLogo_Admin = (ImageView) itemView.findViewById(R.id.imgLogo_admin);
        cardview = (CardView) itemView.findViewById(R.id.card_com_admin);
    }
}