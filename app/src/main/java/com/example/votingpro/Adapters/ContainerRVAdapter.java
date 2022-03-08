package com.example.votingpro.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.votingpro.Classes.Category;
import com.example.votingpro.Classes.MyApp;
import com.example.votingpro.Interfacese.RcvClickEvent;
import com.example.votingpro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContainerRVAdapter extends RecyclerView.Adapter<ContainerRVAdapter.MyViewHolder> {

    public static final String TAG = "ContainerRVAdapter";
    private ArrayList<Category> categoriesList = new ArrayList<>();
    private RcvClickEvent rvEvent;

    public ContainerRVAdapter() {
    }

    public ContainerRVAdapter(Context context)
    {
        this.rvEvent = (RcvClickEvent) context;
    }

    public void setCategoriesList(ArrayList<Category> categoriesList) {

        if (categoriesList == null)
        {
            this.categoriesList = new ArrayList<>();

        }else {
            this.categoriesList = categoriesList;

            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_rva_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.itemView.setTag(categoriesList.get(position));
        Category category = categoriesList.get(position);

        holder.tvName.setText(category.getName());
        holder.tvStatus.setText(category.getStatus());
        holder.tvTodaySubject.setText(category.getSubject());
        holder.tvLike.setText(""+category.getLikeVotes());
        holder.tvNeutral.setText(""+category.getNeutralVotes());
        holder.tvDislike.setText(""+category.getDislikeVotes());

        Glide.with(holder.itemView.getContext()).asBitmap().load(category.getProfileImage()).into(holder.ivProfile);


        Log.d(TAG, "Like --> "+category.getLikeVotes()+" Neutral --> "+category.getNeutralVotes()+" Dislike --> "+category.getDislikeVotes());




    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName, tvStatus, tvTodaySubject, tvLike, tvNeutral, tvDislike;
        private CircleImageView ivProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName_CAL);
            tvStatus = itemView.findViewById(R.id.tvStatus_CAL);
            tvTodaySubject = itemView.findViewById(R.id.tvTodaySubject);
            tvLike = itemView.findViewById(R.id.tvLike_CAL);
            tvNeutral = itemView.findViewById(R.id.tvNeutral_CAL);
            tvDislike = itemView.findViewById(R.id.tvDislike_CAL);
            ivProfile = itemView.findViewById(R.id.profile_image_CAL);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    rvEvent.setOnItemClick( categoriesList.indexOf( (Category) itemView.getTag() ) );

                }
            });

        }
    }
}
