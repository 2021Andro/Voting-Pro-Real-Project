package com.example.votingpro.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.votingpro.Activitys.Register.RegisterActivity;
import com.example.votingpro.Adapters.HomeRVAdapter;
import com.example.votingpro.Adapters.MyVPAdapter;
import com.example.votingpro.Classes.Category;
import com.example.votingpro.Classes.MyApp;
import com.example.votingpro.Fragments.Education_Fragment;
import com.example.votingpro.Fragments.Entertainment_Fragment;
import com.example.votingpro.Fragments.Local_Fragment;
import com.example.votingpro.Fragments.Political_Fragment;
import com.example.votingpro.Fragments.Social_Fragment;
import com.example.votingpro.Interfacese.RcvClickEvent;
import com.example.votingpro.Interfacese.RecFragClickEvent;
import com.example.votingpro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Home_Activity extends AppCompatActivity implements RcvClickEvent{

    private RecyclerView rvList;
    private ArrayList<Category> categoriesList;
    private HomeRVAdapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = findViewById(R.id.vpSlideFragment);
        rvList = findViewById(R.id.rvList);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);

        categoriesList = new ArrayList<>();

        rvList.setLayoutManager(layoutManager);

        myAdapter = new HomeRVAdapter(this);

        rvList.setAdapter(myAdapter);

        MyVPAdapter myVPAdapter = new MyVPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        myVPAdapter.setFragPage(new Education_Fragment(), "Education");
        myVPAdapter.setFragPage(new Political_Fragment(), "Political");
        myVPAdapter.setFragPage(new Social_Fragment(), "Social");
        myVPAdapter.setFragPage(new Entertainment_Fragment(), "Entertainment");
        myVPAdapter.setFragPage(new Local_Fragment(), "Local");

        viewPager.setAdapter(myVPAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 0, education 1, political 2, social 3, entertainment 4, local
        Category education, political, social, entertainment, local;

        education = new Category(0, "Education", "https://firebasestorage.googleapis.com/v0/b/voting-2c85b.appspot.com/o/Main%20Category%20Name%2Feducation.png?alt=media&token=dd83b725-314a-475d-85c6-71a500108fe8");
        political = new Category(1, "Political", "https://firebasestorage.googleapis.com/v0/b/voting-2c85b.appspot.com/o/Main%20Category%20Name%2Fpolitical.png?alt=media&token=1f55185e-1d8a-4977-8aec-a9fe7938ebf5");
        social = new Category(2, "Social", "https://firebasestorage.googleapis.com/v0/b/voting-2c85b.appspot.com/o/Main%20Category%20Name%2Fsocial.png?alt=media&token=41323d8f-67aa-4f36-ae6c-4a35ae9004f5");
        entertainment = new Category(3, "Entertainment", "https://firebasestorage.googleapis.com/v0/b/voting-2c85b.appspot.com/o/Main%20Category%20Name%2Fent.png?alt=media&token=c9e965a7-4f08-4c25-bbf2-ed49b6594636");
        local = new Category(4, "Local", "https://firebasestorage.googleapis.com/v0/b/voting-2c85b.appspot.com/o/Main%20Category%20Name%2Ffamily.png?alt=media&token=f00fc3a3-f33b-4bc0-95ef-a6dc01b7248e");

        categoriesList.add(education);
        categoriesList.add(political);
        categoriesList.add(social);
        categoriesList.add(entertainment);
        categoriesList.add(local);

        myAdapter.setCategoriesList(categoriesList);


    }

    @Override
    public void setOnItemClick(int position) {

//        This code pass the user to choice list category ( Container Activity )
        Category cat = categoriesList.get(position);

        cat.setId(position);
        cat.setCategoryName(categoriesList.get(position).getCategoryName());

        Intent intent = new Intent(getApplicationContext(), ContainerActivity.class);

        intent.putExtra(MyApp.CAT_KEY_NAME, cat);

        startActivity(intent);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.miAdministration:

                startActivity(new Intent(getApplicationContext(), AdministrationActivity.class));

                return true;
            case R.id.miLogout:

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}