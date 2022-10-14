package com.example.internship;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship.Model.JobPost;
import com.example.internship.Model.CompanyVH;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class JobPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    ArrayList<JobPost> lsJobPost = new ArrayList<>();
    ArrayList<JobPost> lsJobPostFilter = new ArrayList<>();

    public void release(){context = null;}

    public JobPostAdapter(Context ctx, ArrayList<JobPost> lsJobPost)
    {
        this.context = ctx;
        this.lsJobPost = lsJobPost;
        this.lsJobPostFilter = new ArrayList<>(lsJobPost);
    }


    public void setItems(ArrayList<JobPost> cpn){
        lsJobPost.addAll(cpn);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.layout_item, parent,false);
        return new CompanyVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CompanyVH vh = (CompanyVH) holder;
        JobPost cpn = lsJobPost.get(position);
        vh.txtName_Company.setText(cpn.getName());
        vh.txtPosition.setText(cpn.getPosition());
        vh.txtNumber.setText(cpn.getNumber());
        String image = cpn.getLogo();
        Picasso.with(context).load(image).into(vh.imgLogo);
        vh.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsPostAdActivity.class);
                intent.putExtra("obj_cpn", cpn);
                context.startActivity(intent);
            }
        });
    }

//    private void onClicktoDetails(Company cpn) {
//
//    }

    public int getItemCount() {
        if(lsJobPost != null){
            return lsJobPost.size();
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
            List<JobPost> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(lsJobPostFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (JobPost item : lsJobPostFilter) {
                    if (item.getPosition().toLowerCase().contains(filterPattern)) {
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
            lsJobPost.clear();
            lsJobPost.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}
