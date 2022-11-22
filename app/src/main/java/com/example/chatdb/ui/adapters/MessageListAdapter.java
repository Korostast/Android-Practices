package com.example.chatdb.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatdb.R;
import com.example.chatdb.database.AppDatabase;
import com.example.chatdb.database.Entities.Message;

import java.util.Arrays;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<Message> messages;
    private final int SENT_MESSAGE = 0;
    private final int RECEIVED_MESSAGE = 1;

    public MessageListAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    /**
     * Add new message in database and view
     */
    public void addMessage(Message newMessage) {
        messages.add(newMessage);
        notifyItemInserted(messages.size() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        long senderId = messages.get(position).getSenderId();
        long curId = AppDatabase.getInstance(context).getCurrentUserId();
        return senderId == curId ? SENT_MESSAGE : RECEIVED_MESSAGE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case SENT_MESSAGE: {
                View view = LayoutInflater.from(context).inflate(R.layout.sent_message_item, parent, false);
                return new SentMessageListViewHolder(view);
            }
            case RECEIVED_MESSAGE:
            default: {
                View view = LayoutInflater.from(context).inflate(R.layout.received_message_item, parent, false);
                return new ReceivedMessageListViewHolder(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case SENT_MESSAGE: {
                SentMessageListViewHolder viewHolder = (SentMessageListViewHolder) holder;
                viewHolder.content.setText(messages.get(position).getContent());
                break;
            }
            case RECEIVED_MESSAGE: {
                ReceivedMessageListViewHolder viewHolder = (ReceivedMessageListViewHolder) holder;
                viewHolder.content.setText(messages.get(position).getContent());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class SentMessageListViewHolder extends RecyclerView.ViewHolder {
        private final TextView content;

        public SentMessageListViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.sent_message_content);
        }
    }

    public static class ReceivedMessageListViewHolder extends RecyclerView.ViewHolder {
        private final TextView content;

        public ReceivedMessageListViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.received_message_content);
        }
    }
}
