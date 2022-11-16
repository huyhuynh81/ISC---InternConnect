package com.example.internship;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship.Model.JobApp;
import com.example.internship.Model.JobAppVH;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JobAppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    ArrayList<JobApp> lsJobApply = new ArrayList<>();
    ArrayList<JobApp> lsJobApplyFilter = new ArrayList<>();


    public void release(){context = null;}

    public JobAppAdapter(Context ctx, ArrayList<JobApp> lsJobApply)
    {
        this.context = ctx;
        this.lsJobApply = lsJobApply;
        this.lsJobApplyFilter = new ArrayList<>(lsJobApply);
    }


    public void setItems(ArrayList<JobApp> jba){
        lsJobApply.addAll(jba);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.layout_item_jobapp, parent,false);
        return new JobAppVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        JobAppVH vh = (JobAppVH) holder;
        JobApp jba = lsJobApply.get(position);

//        Locale id = new Locale("in", "ID");
//        SimpleDateFormat sim = new SimpleDateFormat("dd/MM/yyyy", id);
//        String ngay = jba.getDateApp();
//        String s = sim.format(ngay);

        vh.txtNameCom.setText(jba.getNameCom());
        vh.txtName.setText(jba.getNameUser());
        vh.txtCV.setText(jba.getUrl());
        vh.txtNameSchool.setText(jba.getNameSchool());
        vh.txtMajor.setText(jba.getMajor());
        vh.txtDateApp.setText(jba.getDate());
        vh.txtStatus.setText(jba.getStatus());

        vh.pdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url =  vh.txtCV.getText().toString();
                vh.txtCV.setVisibility(View.GONE);
                Intent intent= new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        });
        vh.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent (context, DetailsJobAppActivity.class);
                intent.putExtra("obj_JobApp",jba);
                context.startActivity(intent);
            }
        });
        if(jba.getStatus().equals("Đã xác nhận")){
            vh.txtStatus.setTextColor(context.getColor(R.color.accept));
        }
        if(jba.getStatus().equals("Không xác nhận")){
            vh.txtStatus.setTextColor(context.getColor(R.color.refuse));
        }

    }

    public int getItemCount() {
        if(lsJobApply != null){
            return lsJobApply.size();
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
            List<JobApp> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(lsJobApplyFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (JobApp item : lsJobApplyFilter) {
                    if (item.getMajor().toLowerCase().contains(filterPattern)) {
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
            lsJobApply.clear();
            lsJobApply.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}
