package com.example.internship;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship.Model.Company;
import com.example.internship.Model.AccountCompanyVH;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    ArrayList<Company> lsCompany = new ArrayList<>();
    ArrayList<Company> lsCompanyFilter = new ArrayList<>();

    public void release(){context = null;}

    public CompanyAdapter(Context ctx, ArrayList<Company> lsCompany)
    {
        this.context = ctx;
        this.lsCompany = lsCompany;
        this.lsCompanyFilter = new ArrayList<>(lsCompany);
    }


    public void setItems(ArrayList<Company> cpn){
        lsCompany.addAll(cpn);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.layout_item_com_admin, parent,false);
        return new AccountCompanyVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AccountCompanyVH vh2 = (AccountCompanyVH) holder;
        Company cpn = lsCompany.get(position);
        vh2.txtName_Company_Admin.setText(cpn.getLocation());
        vh2.txtPosition_Admin.setText(cpn.getName());
//        vh2.txtEmail_Admin.setText(cpn.getEmail());
        String image = cpn.getLogo();
        Picasso.with(context).load(image).into(vh2.imgLogo_Admin);
        vh2.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DetailsComAdminActivity.class);
                intent.putExtra("detail_com" , cpn);
                context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        if(lsCompany != null){
            return lsCompany.size();
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
            List<Company> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(lsCompanyFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Company item : lsCompanyFilter) {
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
            lsCompany.clear();
            lsCompany.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}
