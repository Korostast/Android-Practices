package com.example.linkopener;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Class represents the page with enter input field for the web link
 */
public class LinkEntererActivity extends AppCompatActivity {
    private final String KEY_LINK = "link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_enterer);
        EditText edit = findViewById(R.id.edit_text_link);

        // Add listener for 'done' button tap (confirmation)
        edit.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                findViewById(R.id.close_enterer_button).performClick();
            }
            return false;
        });
    }

    /**
     * Close button callback
     */
    public void closeEnterer(View view) {
        EditText edit = findViewById(R.id.edit_text_link);
        String url = edit.getText().toString();
        if (url.length() != 0) {
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            setResult(RESULT_OK, new Intent().setData(Uri.parse(url)));
        } else {
            setResult(RESULT_CANCELED, new Intent());
        }
        finish();
    }
}