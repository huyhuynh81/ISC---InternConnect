package com.example.internship.Model;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship.HomeActivity;
import com.example.internship.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CompanyVH  extends RecyclerView.ViewHolder {

    private Context context;

    public TextView txtName_Company, txtPosition, txtNumber;
    public ImageView imgLogo;
    public CardView cardview;

    public CompanyVH(@NonNull View itemView) {
        super(itemView);
        txtName_Company = (TextView) itemView.findViewById(R.id.txtName_Company);
        txtPosition = (TextView) itemView.findViewById(R.id.txtPosition);
        txtNumber = (TextView) itemView.findViewById(R.id.txtNumber);
        imgLogo = (ImageView) itemView.findViewById(R.id.imgLogo);
        cardview = (CardView) itemView.findViewById(R.id.cardV);
    }
}
