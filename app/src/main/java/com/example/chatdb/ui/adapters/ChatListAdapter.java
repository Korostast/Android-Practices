package com.example.chatdb.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatdb.ui.activities.MessagesActivity;
import com.example.chatdb.R;
import com.example.chatdb.database.AppDatabase;
import com.example.chatdb.database.Entities.User;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
    private final Context context;
    private List<User> chats;
    private RecyclerView list;

    public ChatListAdapter(Context context, List<User> chats) {
        this.context = context;
        this.chats = chats;
    }

    /**
     * Update the whole list
     */
    public void updateChatList(View view) {
        AppDatabase db = AppDatabase.getInstance(context);
        chats = db.friendListDao().getFriends(db.getCurrentUserId());
        int visibility = chats.isEmpty() ? View.VISIBLE : View.GONE;
        view.findViewById(R.id.chats_list_placeholder).setVisibility(visibility);
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        list = recyclerView;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false);
        view.setOnClickListener(v -> {
            int pos = list.getChildLayoutPosition(v);
            User chat = chats.get(pos);
            Intent intent = new Intent(context, MessagesActivity.class);
            intent.putExtra("USER_ID", chat.getId());
            intent.putExtra("USER_NAME", chat.getName());
            context.startActivity(intent);
        });

        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        holder.chatName.setText(chats.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class ChatListViewHolder extends RecyclerView.ViewHolder {
        private final TextView chatName;

        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            chatName = itemView.findViewById(R.id.chat_item_name);
        }
    }
}