package com.example.chatdb.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.chatdb.ui.fragments.FriendsFragment;
import com.example.chatdb.ui.fragments.IncomingsFragment;
import com.example.chatdb.ui.fragments.OutgoingsFragment;
import com.example.chatdb.ui.fragments.SearchPeopleFragment;
import com.example.chatdb.database.Entities.User;

import java.util.List;

public class FriendsPagerAdapter extends FragmentStateAdapter {
    private final List<User> allUsers;
    private final List<User> friends;
    private final List<User> incomings;
    private final List<User> outgoings;

    public FriendsPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<User> allUsers, List<User> friends, List<User> incomings, List<User> outgoings) {
        super(fragmentActivity);
        this.allUsers = allUsers;
        this.friends = friends;
        this.incomings = incomings;
        this.outgoings = outgoings;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SearchPeopleFragment(allUsers, friends, incomings, outgoings);
            case 1:
                return new FriendsFragment(friends);
            case 2:
                return new IncomingsFragment(incomings);
            case 3:
            default:
                return new OutgoingsFragment(outgoings);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
