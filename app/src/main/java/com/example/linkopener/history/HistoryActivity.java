package com.example.linkopener.history;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linkopener.R;
import com.example.linkopener.database.AppDatabase;
import com.example.linkopener.database.LinkCard;

import java.util.List;

/**
 * History of opened links page
 */
public class HistoryActivity extends AppCompatActivity {
    public static final int CTX_REMOVE = 0;

    private List<LinkCard> cards;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        cards = AppDatabase.getInstance(this.getApplicationContext()).linkCardDao().getAll();
        if (cards.isEmpty())
            Toast.makeText(this, getString(R.string.empty_history), Toast.LENGTH_SHORT).show();

        RecyclerView view = findViewById(R.id.history_list);
        view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(getApplicationContext(), cards);
        view.setAdapter(adapter);
        registerForContextMenu(view);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = item.getGroupId();
        LinkCard link = cards.get(position);

        if (item.getItemId() == CTX_REMOVE) {
            AppDatabase.getInstance(this.getApplicationContext()).linkCardDao().delete(link);
            cards.remove(position);
            adapter.notifyItemRemoved(position);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clear_history) {
            AppDatabase.getInstance(this.getApplicationContext()).linkCardDao().deleteAll();
            adapter.notifyItemRangeRemoved(0, cards.size());
            cards.clear();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
