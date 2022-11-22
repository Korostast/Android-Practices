package com.example.chatdb.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.chatdb.R;
import com.example.chatdb.database.Entities.User;
import com.example.chatdb.ui.adapters.MainPagerAdapter;
import com.example.chatdb.database.AppDatabase;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Activity with chat list and menu
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 pager = findViewById(R.id.view_pager);
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(this);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
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

        Objects.requireNonNull(tabLayout.getTabAt(1)).select(); // Chats tab
    }

    @Override
    protected void onResume() {
        super.onResume();

        // We return in this activity from another activity and want to make sure that login has not changed
        TextView username = findViewById(R.id.menu_name);
        if (username != null) {
            username.setText(AppDatabase.getInstance(getApplicationContext()).getCurrentUserName());
        }
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
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}