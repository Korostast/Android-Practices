package com.example.chatdb.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatdb.R;
import com.example.chatdb.database.AppDatabase;
import com.example.chatdb.database.Entities.User;
import com.example.chatdb.enums.UserState;

import java.util.List;

public class SearchUserListAdapter extends RecyclerView.Adapter<SearchUserListAdapter.UserListViewHolder> {
    private final Context context;
    private List<User> users;
    private UserState[] isFriend;

    public SearchUserListAdapter(Context context, List<User> users, UserState[] isFriend) {
        this.context = context;
        this.users = users;
        this.isFriend = isFriend;
    }

    @NonNull
    @Override
    public SearchUserListAdapter.UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchUserListAdapter.UserListViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUserListAdapter.UserListViewHolder holder, int position) {
        holder.userName.setText(users.get(position).getName());
        switch (isFriend[position]) {
            case NOTHING:
                holder.addButton.setOnClickListener(v -> {
                    int pos = holder.getBindingAdapterPosition();
                    AppDatabase db = AppDatabase.getInstance(v.getContext());
                    db.requestListDao().sendFriendRequest(db.getCurrentUserId(), users.get(pos).getId());
                    updateUserCards(holder, View.INVISIBLE, R.string.outgoing_request);
                });
                break;
            case FRIEND:
                updateUserCards(holder, View.GONE, R.string.already_friend);
                break;
            case INCOMING_REQUEST:
                updateUserCards(holder, View.GONE, R.string.incoming_request);
                break;
            case OUTGOING_REQUEST:
                updateUserCards(holder, View.GONE, R.string.outgoing_request);
                break;
        }
    }

    /**
     * Change view of user cards based on user status (see UserState)
     */
    private void updateUserCards(@NonNull UserListViewHolder holder, int visibility, int status) {
        holder.addButton.setVisibility(visibility);
        holder.statusText.setText(status);
        holder.statusText.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsersList(List<User> users) {
        this.users = users;
    }

    public void setIsFriend(UserState[] isFriend) {
        this.isFriend = isFriend;
    }

    public static class UserListViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final Button addButton;
        private final TextView statusText;

        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_item_name);
            addButton = itemView.findViewById(R.id.add_friend_button);
            statusText = itemView.findViewById(R.id.status_text);
        }
    }
}