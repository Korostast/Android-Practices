package com.example.chatdb.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.chatdb.ui.adapters.SearchUserListAdapter;
import com.example.chatdb.R;
import com.example.chatdb.database.AppDatabase;
import com.example.chatdb.database.Entities.User;
import com.example.chatdb.enums.UserState;

import java.util.Arrays;
import java.util.List;

public class SearchPeopleFragment extends Fragment {
    private final List<User> allUsers;
    private final List<User> friends;
    private final List<User> incomings;
    private final List<User> outcomings;
    private SearchUserListAdapter peopleListAdapter;

    public SearchPeopleFragment(List<User> allUsers, List<User> friends, List<User> incomings, List<User> outcomings) {
        this.allUsers = allUsers;
        this.friends = friends;
        this.incomings = incomings;
        this.outcomings = outcomings;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_people, container, false);

        // Fill list
        UserState[] isFriend = getUserStates();

        RecyclerView list = view.findViewById(R.id.users_list);
        list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        peopleListAdapter = new SearchUserListAdapter(view.getContext(), allUsers, isFriend);
        list.setAdapter(peopleListAdapter);

        EditText searchText = view.findViewById(R.id.search_people_edit);
        view.findViewById(R.id.search_people_button).setOnClickListener(v -> {
            String input = searchText.getText().toString();
            AppDatabase db = AppDatabase.getInstance(v.getContext());
            List<User> users = db.userDao().findUsersByPatternExceptSelf(db.getCurrentUserId(), input);
            peopleListAdapter.setUsersList(users);
            peopleListAdapter.setIsFriend(getUserStates());
            peopleListAdapter.notifyDataSetChanged();
        });

        return view;
    }

    @NonNull
    private UserState[] getUserStates() {
        UserState[] isFriend = new UserState[allUsers.size()];
        Arrays.fill(isFriend, UserState.NOTHING);
        int i = 0;
        for (User u : allUsers) {
            long curId = u.getId();
            for (User f : friends)
                if (curId == f.getId())
                    isFriend[i++] = UserState.FRIEND;
            for (User in : incomings)
                if (curId == in.getId())
                    isFriend[i++] = UserState.INCOMING_REQUEST;
            for (User out : outcomings)
                if (curId == out.getId())
                    isFriend[i++] = UserState.OUTGOING_REQUEST;
        }
        return isFriend;
    }
}