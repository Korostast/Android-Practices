package com.example.chatdb.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.chatdb.ui.fragments.MenuFragment;
import com.example.chatdb.ui.fragments.ChatsFragment;

public class MainPagerAdapter extends FragmentStateAdapter {
    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MenuFragment();
            case 1:
            default:
                return new ChatsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
