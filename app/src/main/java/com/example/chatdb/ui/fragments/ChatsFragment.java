package com.example.chatdb.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatdb.R;
import com.example.chatdb.ui.adapters.ChatListAdapter;
import com.example.chatdb.database.AppDatabase;
import com.example.chatdb.database.Entities.User;

import java.util.List;

public class ChatsFragment extends Fragment {
    private ChatListAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        if (view != null) {
            adapter.updateChatList(view);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        AppDatabase db = AppDatabase.getInstance(getContext());
        List<User> friends = db.friendListDao().getFriends(db.getCurrentUserId());

        // If no chats
        if (friends.isEmpty()) {
            view.findViewById(R.id.chats_list_placeholder).setVisibility(View.VISIBLE);
        }

        // Fill list
        RecyclerView list = view.findViewById(R.id.chats_list);
        list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new ChatListAdapter(view.getContext(), friends);
        list.setAdapter(adapter);

        return view;
    }
}