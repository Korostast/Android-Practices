package com.example.linkopener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.linkopener.history.HistoryActivity;

/**
 * Launcher activity
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Launch activity and expect the URL as the result
     */
    ActivityResultLauncher<Intent> getURL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case Activity.RESULT_OK:
                        Intent data = result.getData();
                        if (data != null) {
                            OpenLinkDialogFragment dialog = new OpenLinkDialogFragment(data.getData());
                            dialog.show(getSupportFragmentManager(), "Tag");
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    default:
                        Toast.makeText(this, getString(R.string.link_handling_error), Toast.LENGTH_SHORT).show();
                }
            }
    );

    /**
     * Open link enterer page callback
     */
    public void switchToLinkEnterer(View view) {
        Intent openLinkEnterer = new Intent(this, LinkEntererActivity.class);
        getURL.launch(openLinkEnterer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_history) {
            Intent history = new Intent(this, HistoryActivity.class);
            startActivity(history);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}