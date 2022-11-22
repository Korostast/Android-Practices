package com.example.chatdb.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.chatdb.R;
import com.example.chatdb.ui.adapters.FriendsPagerAdapter;
import com.example.chatdb.database.AppDatabase;
import com.example.chatdb.database.Entities.User;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;

/**
 * Activity with people list, friend list, incomings and outgoings
 */
public class FriendsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        ViewPager2 pager = findViewById(R.id.friends_view_pager);

        // SQL requests for adapter
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        long curId = db.getCurrentUserId();
        List<User> allUsers = db.userDao().getAllUsersExceptSelf(curId);
        List<User> friends = db.friendListDao().getFriends(curId);
        List<User> incomings = db.requestListDao().getIncomings(curId);
        List<User> outcomings = db.requestListDao().getOutgoings(curId);

        FriendsPagerAdapter adapter = new FriendsPagerAdapter(this, allUsers, friends, incomings, outcomings);
        pager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.friends_tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Objects.requireNonNull(tabLayout.getTabAt(position)).select();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Logout
        if (item.getItemId() == R.id.logout_menu_button) {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            db.setCurrentUserId(null);
            db.setCurrentUserName(null);
            db.setCurrentPhone(null);
            startActivity(new Intent(getApplicationContext(), AuthorizationActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}