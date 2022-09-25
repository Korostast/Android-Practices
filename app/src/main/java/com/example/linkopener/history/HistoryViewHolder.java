package com.example.linkopener.history;

import static com.example.linkopener.history.HistoryActivity.CTX_REMOVE;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linkopener.R;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * History view holder
 */
public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    public TextView linkText;

    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        linkText = itemView.findViewById(R.id.link);
        itemView.setLongClickable(true);
        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(this.getAdapterPosition(), CTX_REMOVE, Menu.NONE, R.string.remove);
    }
}