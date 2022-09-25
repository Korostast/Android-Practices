package com.example.linkopener.history;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linkopener.R;
import com.example.linkopener.database.LinkCard;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
    Context context;
    List<LinkCard> items;

    public HistoryAdapter(Context context, List<LinkCard> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.history_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.linkText.setText(items.get(position).link);
        holder.itemView.setOnClickListener(view -> {
            TextView linkText = view.findViewById(R.id.link);
            Intent browser = new Intent(Intent.ACTION_VIEW);
            browser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            browser.setData(Uri.parse(String.valueOf(linkText.getText())));
            context.startActivity(browser);
        });

        holder.itemView.setOnLongClickListener(view -> false);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}