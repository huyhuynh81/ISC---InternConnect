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

public class MajorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    ArrayList<Major> lsMajor = new ArrayList<>();
    ArrayList<Major> lsMajorFilter = new ArrayList<>();

    public void release(){context = null;}

    public MajorAdapter(Context ctx, ArrayList<Major> lsMajor)
    {
        this.context = ctx;
        this.lsMajor = lsMajor;
        this.lsMajorFilter = new ArrayList<>(lsMajor);
    }


    public void setItems(ArrayList<Major> mjs){
        lsMajor.addAll(mjs);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.layout_item_major, parent,false);
        return new MajorVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MajorVH vh = (MajorVH) holder;
        Major mjs = lsMajor.get(position);
        vh.txtNameMajor.setText(mjs.getName());
        vh.txtDateAdd.setText(mjs.getDate());
        vh.txtNameMajor.setEnabled(false);
        vh.txtDateAdd.setEnabled(false);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("Major");
        ((MajorVH) holder).imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lsMajor.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference.child(((MajorVH) holder).txtNameMajor.getText().toString().trim()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "X??a th??nh c??ng", Toast.LENGTH_SHORT).show();
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
        if(lsMajor != null){
            return lsMajor.size();
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
            List<Major> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(lsMajorFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Major item : lsMajorFilter) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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
            lsMajor.clear();
            lsMajor.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}
