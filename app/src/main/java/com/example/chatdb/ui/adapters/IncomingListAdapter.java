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

public class IncomingListAdapter extends RecyclerView.Adapter<IncomingListAdapter.IncomingsListViewHolder> {
    private final Context context;
    private List<User> incomings;

    public IncomingListAdapter(Context context, List<User> incomings) {
        this.context = context;
        this.incomings = incomings;
    }

    /**
     * Update the whole list
     */
    public void updateChatList(View view) {
        AppDatabase db = AppDatabase.getInstance(context);
        incomings = db.requestListDao().getIncomings(db.getCurrentUserId());
        int visibility = incomings.isEmpty() ? View.VISIBLE : View.GONE;
        view.findViewById(R.id.incomings_list_placeholder).setVisibility(visibility);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IncomingsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IncomingsListViewHolder(LayoutInflater.from(context).inflate(R.layout.incoming_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IncomingsListViewHolder holder, int position) {
        holder.userName.setText(incomings.get(position).getName());
        holder.acceptButton.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            AppDatabase db = AppDatabase.getInstance(v.getContext());
            long curId = db.getCurrentUserId();
            long incomingId = incomings.get(pos).getId();
            db.requestListDao().deleteFriendRequest(incomingId, curId);
            db.friendListDao().addFriend(incomingId, curId);
            incomings.remove(pos);
            notifyItemRemoved(pos);
        });
        holder.declineButton.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            AppDatabase db = AppDatabase.getInstance(v.getContext());
            long curId = db.getCurrentUserId();
            long incomingId = incomings.get(pos).getId();
            db.requestListDao().deleteFriendRequest(incomingId, curId);
            incomings.remove(pos);
            notifyItemRemoved(pos);
        });
    }

    @Override
    public int getItemCount() {
        return incomings.size();
    }

    public static class IncomingsListViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final Button acceptButton;
        private final Button declineButton;

        public IncomingsListViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.incoming_item_name);
            acceptButton = itemView.findViewById(R.id.incoming_accept_button);
            declineButton = itemView.findViewById(R.id.incoming_decline_button);
        }
    }
}