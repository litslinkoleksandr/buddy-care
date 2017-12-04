package com.lits.buddycare.ui.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lits.buddycare.R;
import com.lits.buddycare.data.DataManager;
import com.lits.buddycare.data.model.User;

public class ProfileActivity extends AppCompatActivity {

    public static final String ARG_USER_ID = "arg_user_id";

    private Toolbar toolbar;
    private ImageView imgAvatar;
    private RecyclerView recyclerView;

    private WishAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar);
        imgAvatar = findViewById(R.id.img_avatar);
        recyclerView = findViewById(R.id.recycler_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        adapter = new WishAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int userId = extras.getInt(ARG_USER_ID);
            loadUser(userId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadUser(int userId) {
        DataManager.getInstance().loadUser(userId)
                .subscribe(user -> {
                    if (user != null) {
                        showUser(user);
                    }
                }, throwable -> Log.e(ProfileActivity.class.getSimpleName(), throwable.getMessage()));
    }

    private void showUser(User user) {
        setTitle(user.getName());
        Glide.with(this).load(user.getPhoto())
                .apply(new RequestOptions())
                .into(this.imgAvatar);
        adapter.setItems(user.getWishes());
        adapter.notifyDataSetChanged();
    }

}
