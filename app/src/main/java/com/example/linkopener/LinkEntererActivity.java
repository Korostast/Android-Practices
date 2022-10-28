package com.example.linkopener;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Class represents the page with enter input field for the web link
 */
public class LinkEntererActivity extends AppCompatActivity {
    private final String KEY_LINK = "link";
    private Integer viewSizeWithKeyboard = null;
    private Boolean keyboardOpened = false;

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_enterer);
        EditText edit = findViewById(R.id.edit_text_link);

        // Activity tracks the (possible) appearance and hiding of the keyboard on the screen
        View activityRootView = findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();

            // The screen has changed the size and the keyboard was opened? Maybe we hide the keyboard
            if (keyboardOpened && viewSizeWithKeyboard != null && viewSizeWithKeyboard - heightDiff != 0) {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                Toast.makeText(LinkEntererActivity.this, "Keyboard hidden?", Toast.LENGTH_SHORT).show();
                keyboardOpened = false;
            }

            // If more than 200 dp, it's probably a keyboard opening
            if (!keyboardOpened && heightDiff > dpToPx(activityRootView.getContext(), 200)) {
                Toast.makeText(LinkEntererActivity.this, "Keyboard opened?", Toast.LENGTH_SHORT).show();
                viewSizeWithKeyboard = heightDiff;
                keyboardOpened = true;
            }
        });

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

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }

        /*
          About newConfig.keyboardHidden:
          Stackoverflow: "No, onConfigurationChange() doesn't catch soft keyboard events: it's not a configuration change."
         */
    }
}