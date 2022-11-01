package com.example.internship;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship.Model.SchoolVH;
import com.example.internship.Model.Student;


import java.util.ArrayList;
import java.util.List;

public class SchoolAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    ArrayList<Student> lsStudent = new ArrayList<>();
    ArrayList<Student> lsStudentFilter = new ArrayList<>();

    public void release(){context = null;}

    public SchoolAdapter(Context ctx, ArrayList<Student> lsStudent)
    {
        this.context = ctx;
        this.lsStudent = lsStudent;
        this.lsStudentFilter = new ArrayList<>(lsStudent);
    }


    public void setItems(ArrayList<Student> std){
        lsStudent.addAll(std);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.layout_item_school, parent,false);
        return new SchoolVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SchoolVH vh = (SchoolVH) holder;
        Student std = lsStudent.get(position);
        vh.txtName.setText(std.getName());
        vh.txtEmail.setText(std.getEmail());
        vh.txtMajor.setText(std.getMajor());
//        String image = sch.getLogo();
//        Picasso.with(context).load(image).into(vh.imgLogo);
//        vh.cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, DetailsActivity.class);
//                intent.putExtra("obj_cpn", cpn);
//                context.startActivity(intent);
//            }
//        });
    }

//    private void onClicktoDetails(Company cpn) {
//
//    }

    public int getItemCount() {
        if(lsStudent != null){
            return lsStudent.size();
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
            List<Student> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(lsStudentFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Student item : lsStudentFilter) {
                    if (item.getEmail().toLowerCase().contains(filterPattern)) {
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
            lsStudent.clear();
            lsStudent.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}
