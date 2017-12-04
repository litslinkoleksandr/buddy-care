package com.lits.buddycare.ui.feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.lits.buddycare.R;
import com.lits.buddycare.data.DataManager;
import com.lits.buddycare.data.model.Wish;
import com.lits.buddycare.ui.profile.ProfileActivity;

import io.realm.Realm;
import io.realm.RealmResults;

public class FeedActivity extends AppCompatActivity implements OnItemClickListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);

        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        setAdapter(recyclerView);

        DataManager dataManager = DataManager.getInstance();
        dataManager.loadWishes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_account:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
        }
        return false;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.ARG_USER_ID, adapter.getItem(position).getUser().getId());
        startActivity(intent);
    }

    private void setAdapter(RecyclerView recyclerView) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Wish> wishes = realm.where(Wish.class).findAll();
        adapter = new FeedAdapter(this, wishes);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

}
