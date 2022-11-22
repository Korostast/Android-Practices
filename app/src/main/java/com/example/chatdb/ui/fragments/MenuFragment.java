package com.example.chatdb.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chatdb.ui.activities.FriendsActivity;
import com.example.chatdb.R;
import com.example.chatdb.ui.activities.SettingsActivity;
import com.example.chatdb.database.AppDatabase;

public class MenuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        view.findViewById(R.id.menu_friends).setOnClickListener(v -> startActivity(new Intent(v.getContext(), FriendsActivity.class)));
        view.findViewById(R.id.menu_settings).setOnClickListener(v -> startActivity(new Intent(v.getContext(), SettingsActivity.class)));
        TextView name = view.findViewById(R.id.menu_name);
        name.setText(AppDatabase.getInstance(getContext()).getCurrentUserName());
        return view;
    }

}