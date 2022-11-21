package com.example.internship;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.internship.Model.Account;
import com.example.internship.Model.AccountVH;

import java.util.ArrayList;
import java.util.List;

public class AccountAdminAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    ArrayList<Account> lsAccount = new ArrayList<>();
    ArrayList<Account> lsAccountFilter = new ArrayList<>();
    ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    public void release(){context = null;}

    public AccountAdminAdapter(Context ctx, ArrayList<Account> lsAccount)
    {
        this.context = ctx;
        this.lsAccount = lsAccount;
        this.lsAccountFilter = new ArrayList<>(lsAccount);
    }


    public void setItems(ArrayList<Account> cpn){
        lsAccount.addAll(cpn);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.layout_item_account_admin, parent,false);
        return new AccountVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AccountVH vh = (AccountVH) holder;
        Account cpn = lsAccount.get(position);
        if(cpn == null){
            return;
        }
        //        viewBinderHelper.bind(((AccountVH) holder).SwipeRevealLayout,cpn.getName());
        //        ((AccountVH) holder).txtName_Admin.setText(cpn.getName());
        vh.txtName_Admin.setText(cpn.getName());
        vh.txtEmail_Admin.setText(cpn.getEmail());
        vh.txtPhone_Admin.setText(cpn.getPhone());
        String role = String.valueOf(cpn.getRole());
        if(role.equals("1")){
            vh.txtRole_Admin.setText("Công ty");
        }
        else if(role.equals("2")){
            vh.txtRole_Admin.setText("Sinh viên");
        }
        else if(role.equals("3")){
            vh.txtRole_Admin.setText("Trường");
        }
        else if(role.equals("4")){
            vh.txtRole_Admin.setText("Admin");
            vh.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,DetailsAccountAdminActivity.class);
                    intent.putExtra("detail_acc" , cpn);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(lsAccount != null){
            return lsAccount.size();
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
            List<Account> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(lsAccountFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Account item : lsAccountFilter) {
                    String role = String.valueOf(item.getRole());
                    String nameRole;
                    if(role.equals("1")){
                        nameRole = "Công ty";
                        if(nameRole.toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    }
                    else if(role.equals("2")){
                        nameRole = "Sinh viên";
                        if(nameRole.toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    }
                    else if(role.equals("3")){
                        nameRole = "Trường";
                        if(nameRole.toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    }
                    else if(role.equals("4")){
                        nameRole = "Admin";
                        if(nameRole.toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            lsAccount.clear();
            lsAccount.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
