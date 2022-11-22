package com.example.chatdb.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatdb.ui.adapters.OutgoingListAdapter;
import com.example.chatdb.R;
import com.example.chatdb.database.Entities.User;

import java.util.List;

public class OutgoingsFragment extends Fragment {
    private final List<User> outgoings;
    private OutgoingListAdapter adapter;

    public OutgoingsFragment(List<User> outgoings) {
        this.outgoings = outgoings;
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
        View view = inflater.inflate(R.layout.fragment_outgoings, container, false);

        // If no chats
        if (outgoings.isEmpty()) {
            view.findViewById(R.id.outgoings_list_placeholder).setVisibility(View.VISIBLE);
        }

        // Fill list
        RecyclerView list = view.findViewById(R.id.outgoing_list);
        list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new OutgoingListAdapter(view.getContext(), outgoings);
        list.setAdapter(adapter);

        return view;
    }
}