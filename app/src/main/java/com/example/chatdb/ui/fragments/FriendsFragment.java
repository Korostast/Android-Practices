package com.example.chatdb.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatdb.ui.adapters.FriendListAdapter;
import com.example.chatdb.R;
import com.example.chatdb.database.Entities.User;

import java.util.List;

public class FriendsFragment extends Fragment {
    private final List<User> friends;
    private FriendListAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        if (view != null) {
            adapter.updateChatList(view);
        }
    }

    public FriendsFragment(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        // If no chats
        if (friends.isEmpty()) {
            view.findViewById(R.id.friends_list_placeholder).setVisibility(View.VISIBLE);
        }

        // Fill list
        RecyclerView list = view.findViewById(R.id.friends_list);
        list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new FriendListAdapter(view.getContext(), friends);
        list.setAdapter(adapter);

        return view;
    }
}