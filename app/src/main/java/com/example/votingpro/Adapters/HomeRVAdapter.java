package com.example.votingpro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.votingpro.Classes.Category;
import com.example.votingpro.Interfacese.RcvClickEvent;
import com.example.votingpro.R;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeRVAdapter extends RecyclerView.Adapter<HomeRVAdapter.CategoryViewHolder> {

    private ArrayList<Category> categoriesList = new ArrayList<>();
    private RcvClickEvent rcvEvent;

    public HomeRVAdapter(Context context) {
        this.rcvEvent = (RcvClickEvent) context;
    }

    public void setCategoriesList(ArrayList<Category> categoriesList) {

        if (categoriesList == null)
        {
            categoriesList = new ArrayList<>();
        }
        else {

            this.categoriesList = categoriesList;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_adapter_rv_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        holder.itemView.setTag(categoriesList.get(position));

        Category categories = categoriesList.get(position);

        holder.tvName.setText(categories.getCategoryName());

        // 0, education 1, political 2, social 3, entertainment 4, local
        Glide.with(holder.itemView.getContext()).asBitmap().load(categories.getProfileImage()).into(holder.ivCategory);


    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private CircleImageView ivCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName_CAL);
            ivCategory = itemView.findViewById(R.id.ivCatIma_CAL);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    rcvEvent.setOnItemClick(categoriesList.indexOf((Category) itemView.getTag()));

                }
            });


        }
    }
}
