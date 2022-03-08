package com.example.votingpro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.votingpro.Classes.Category;
import com.example.votingpro.Fragments.Education_Fragment;
import com.example.votingpro.Interfacese.RcvClickEvent;
import com.example.votingpro.Interfacese.RecFragClickEvent;
import com.example.votingpro.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyRVFragAdapter extends RecyclerView.Adapter<MyRVFragAdapter.MyViewHolder> {

    private ArrayList<Category> categoryList = new ArrayList<>();
    private RecFragClickEvent rcvClickEvent;

    public MyRVFragAdapter(Fragment context) {
        this.rcvClickEvent = (RecFragClickEvent) context;
    }

    public void setCategoryList(ArrayList<Category> categoryList) {

        if (categoryList == null) {
            this.categoryList = new ArrayList<>();
        } else {
            this.categoryList = categoryList;

            notifyDataSetChanged();
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_rvfrag_adapter_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Category category = categoryList.get(position);

        holder.itemView.setTag(categoryList.get(position));

        holder.tvName.setText(category.getName());

        Glide.with(holder.itemView.getContext()).asBitmap().load(category.getProfileImage()).into(holder.ivProfile);

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView ivProfile;
        private TextView tvName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName_rva);
            ivProfile = itemView.findViewById(R.id.profile_image__rva);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    rcvClickEvent.setOnFragItemClick(categoryList.indexOf( (Category) itemView.getTag() ));

                }
            });


        }
    }
}
