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

public class OutgoingListAdapter extends RecyclerView.Adapter<OutgoingListAdapter.OutcomingListViewHolder> {
    private final Context context;
    private List<User> outgoings;

    public OutgoingListAdapter(Context context, List<User> outcomings) {
        this.context = context;
        this.outgoings = outcomings;
    }

    /**
     * Update the whole list
     */
    public void updateChatList(View view) {
        AppDatabase db = AppDatabase.getInstance(context);
        outgoings = db.requestListDao().getOutgoings(db.getCurrentUserId());
        int visibility = outgoings.isEmpty() ? View.VISIBLE : View.GONE;
        view.findViewById(R.id.outgoings_list_placeholder).setVisibility(visibility);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OutgoingListAdapter.OutcomingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OutgoingListAdapter.OutcomingListViewHolder(LayoutInflater.from(context).inflate(R.layout.outgoing_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OutgoingListAdapter.OutcomingListViewHolder holder, int position) {
        holder.outgoingUserName.setText(outgoings.get(position).getName());
        holder.cancelButton.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            AppDatabase db = AppDatabase.getInstance(v.getContext());
            long curId = db.getCurrentUserId();
            long userId = outgoings.get(pos).getId();
            db.requestListDao().deleteFriendRequest(curId, userId);
            outgoings.remove(pos);
            notifyItemRemoved(pos);
        });
    }

    @Override
    public int getItemCount() {
        return outgoings.size();
    }

    public static class OutcomingListViewHolder extends RecyclerView.ViewHolder {
        private final TextView outgoingUserName;
        private final Button cancelButton;

        public OutcomingListViewHolder(@NonNull View itemView) {
            super(itemView);
            outgoingUserName = itemView.findViewById(R.id.outgoing_item_name);
            cancelButton = itemView.findViewById(R.id.outgoing_cancel_button);
        }
    }
}