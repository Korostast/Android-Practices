package com.example.linkopener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.linkopener.database.AppDatabase;
import com.example.linkopener.database.LinkCard;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Open link dialog
 */
public class OpenLinkDialogFragment extends DialogFragment {
    private final String KEY_LINK = "key";
    private Uri url;
    AppDatabase db;

    public OpenLinkDialogFragment() {
    }

    public OpenLinkDialogFragment(Uri url) {
        this.url = url;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        setRetainInstance(true);  // deprecated
        if (savedInstanceState != null)
            url = Uri.parse(savedInstanceState.getString(KEY_LINK));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.open_link) + url)
                .setPositiveButton(getString(R.string.open), (dialogInterface, i) -> {
                    Intent browser = new Intent(Intent.ACTION_VIEW);
                    URL resolvedURL = null;
                    try {
                        resolvedURL = new URL(url.toString());
                    } catch (MalformedURLException e) {
                        Toast.makeText(getContext(), getString(R.string.invalid_url), Toast.LENGTH_SHORT).show();
                    }
                    if (resolvedURL != null) {
                        browser.setData(url);
                        startActivity(browser);
                        AppDatabase db = AppDatabase.getInstance(this.getContext());
                        LinkCard card = new LinkCard();
                        card.link = url.toString();
                        db.linkCardDao().insertAll(card);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> {
                });

        return builder.create();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_LINK, url.toString());
    }
}
