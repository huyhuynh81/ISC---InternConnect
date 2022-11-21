package com.example.internship;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship.Model.JobPost;
import com.example.internship.Model.JobPostComVH;
import com.example.internship.Model.JobPostVH;
import com.example.internship.Model.Major;
import com.example.internship.Model.MajorVH;
import com.example.internship.Model.School;
import com.example.internship.Model.SchoolVH;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolAdminAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    ArrayList<School> lsSchool = new ArrayList<>();
    ArrayList<School> lsSchoolFilter = new ArrayList<>();

    public void release(){context = null;}

    public SchoolAdminAdapter(Context ctx, ArrayList<School> lsSchool)
    {
        this.context = ctx;
        this.lsSchool = lsSchool;
        this.lsSchoolFilter = new ArrayList<>(lsSchool);
    }


    public void setItems(ArrayList<School> sch){
        lsSchool.addAll(sch);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.layout_item_school_admin, parent,false);
        return new SchoolVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SchoolVH vh = (SchoolVH) holder;
        School sch = lsSchool.get(position);
        vh.txtNameSchool.setText(sch.getNameSchool());
        vh.txtDateAdd.setText(sch.getDate());
        vh.txtNameSchool.setEnabled(false);
        vh.txtDateAdd.setEnabled(false);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("School");
        ((SchoolVH) holder).imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lsSchool.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference.child(((SchoolVH) holder).txtNameSchool.getText().toString().trim()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });

    }

//    private void onClicktoDetails(Company cpn) {
//
//    }

    public int getItemCount() {
        if(lsSchool != null){
            return lsSchool.size();
        }
        return 0;
    }

    public Filter getFilter()
    {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<School> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(lsSchoolFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (School item : lsSchoolFilter) {
                    if (item.getNameSchool().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            lsSchool.clear();
            lsSchool.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}
