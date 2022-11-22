package com.example.chatdb.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatdb.R;
import com.example.chatdb.ui.adapters.IncomingListAdapter;
import com.example.chatdb.database.Entities.User;

import java.util.List;

public class IncomingsFragment extends Fragment {
    private final List<User> incomings;
    private IncomingListAdapter adapter;

    public IncomingsFragment(List<User> incomings) {
        this.incomings = incomings;
    }

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
        View view = inflater.inflate(R.layout.fragment_incomings, container, false);

        // If no chats
        if (incomings.isEmpty()) {
            view.findViewById(R.id.incomings_list_placeholder).setVisibility(View.VISIBLE);
        }

        // Fill list
        RecyclerView list = view.findViewById(R.id.incomings_list);
        list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new IncomingListAdapter(view.getContext(), incomings);
        list.setAdapter(adapter);

        return view;
    }
}