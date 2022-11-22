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

import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder> {
    private final Context context;
    private List<User> friends;

    public FriendListAdapter(Context context, List<User> friends) {
        this.context = context;
        this.friends = friends;
    }

    /**
     * Update the whole list
     */
    public void updateChatList(View view) {
        AppDatabase db = AppDatabase.getInstance(context);
        friends = db.friendListDao().getFriends(db.getCurrentUserId());
        int visibility = friends.isEmpty() ? View.VISIBLE : View.GONE;
        view.findViewById(R.id.friends_list_placeholder).setVisibility(visibility);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FriendListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendListViewHolder(LayoutInflater.from(context).inflate(R.layout.friend_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListViewHolder holder, int position) {
        holder.friendName.setText(friends.get(position).getName());
        holder.deleteButton.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            AppDatabase db = AppDatabase.getInstance(v.getContext());
            db.friendListDao().deleteFriend(db.getCurrentUserId(), friends.get(pos).getId());
            friends.remove(pos);
            notifyItemRemoved(pos);
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public static class FriendListViewHolder extends RecyclerView.ViewHolder {
        private final TextView friendName;
        private final Button deleteButton;

        public FriendListViewHolder(@NonNull View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.friend_item_name);
            deleteButton = itemView.findViewById(R.id.delete_friend_button);
        }
    }
}
