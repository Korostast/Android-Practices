package com.example.chatdb.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatdb.R;
import com.example.chatdb.database.AppDatabase;
import com.example.chatdb.database.Entities.RequestList;
import com.example.chatdb.database.Entities.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {
    private EditText login;
    private TextView loginError;
    private TextView contactSyncError;

    private void setError(TextView label, int error, int color, Object... params) {
        label.setText(getString(error, params));
        label.setVisibility(View.VISIBLE);
        label.setTextColor(ContextCompat.getColor(getApplicationContext(), color));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        login = findViewById(R.id.login_edit);
        login.setText(AppDatabase.getInstance(getApplicationContext()).getCurrentUserName());

        loginError = findViewById(R.id.login_change_error);
        contactSyncError = findViewById(R.id.contact_sync_error);
    }

    public void onChangeLoginButtonClick(View v) {
        AppDatabase db = AppDatabase.getInstance(v.getContext());

        String newLogin = login.getText().toString();
        String oldLogin = db.getCurrentUserName();
        int color = com.google.android.material.R.color.design_default_color_error;
        if (newLogin.equals(oldLogin)) {
            setError(loginError, R.string.same_login_error, color);
        } else if (newLogin.isEmpty()) {
            setError(loginError, R.string.empty_login_error, color);
        } else if (newLogin.length() < 4) {
            setError(loginError, R.string.login_length_err, color);
        } else if (!(newLogin.matches("[\\p{IsLatin}]+$"))) {
            setError(loginError, R.string.login_latin_err, color);
        } else if (!newLogin.equalsIgnoreCase(oldLogin) && !db.userDao().findUsersByName(newLogin).isEmpty()) {
            setError(loginError, R.string.user_exist_err, color);
        } else {
            color = R.color.success_color;
            db.userDao().updateUsername(db.getCurrentUserId(), newLogin);
            db.setCurrentUserName(newLogin);
            setError(loginError, R.string.login_change_success, color);
        }
    }

    private void sendFriendRequestToContacts() {
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String numberCol = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Set<String> numbers = new HashSet<>();
        try (Cursor cursor = resolver.query(uri, null, numberCol, null, null)) {
            while (cursor.moveToNext()) {
                int colIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                if (colIndex != -1) {
                    numbers.add(cursor.getString(colIndex).replaceAll("[ \\-+]", ""));
                }
            }
        }

        if (!numbers.isEmpty()) {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            long curId = db.getCurrentUserId();

            // It would be nice, if I cached it
            List<User> friends = db.friendListDao().getFriends(curId);
            List<User> incomings = db.requestListDao().getIncomings(curId);
            List<User> outgoings = db.requestListDao().getOutgoings(curId);
            List<User> contacts = db.userDao().findByPhoneNumbers(numbers);

            contacts.removeIf(user -> user.getPhoneNumber().equals(db.getCurrentPhone()));
            contacts.removeAll(friends);
            contacts.removeAll(incomings);
            contacts.removeAll(outgoings);

            if (!contacts.isEmpty()) {
                List<RequestList> requests = new ArrayList<>();
                for (User user : contacts) {
                    requests.add(new RequestList(curId, user.getId()));
                }

                db.requestListDao().insert(requests);
                setError(contactSyncError, R.string.contact_sync_success, R.color.success_color, requests.size());
            } else {
                int color = com.google.android.material.R.color.design_default_color_error;
                setError(contactSyncError, R.string.no_contact_users, color);
            }
        }
    }

    public void onContactSyncClick(View v) {
        if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 0);
        } else {
            sendFriendRequestToContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendFriendRequestToContacts();
            } else {
                int color = com.google.android.material.R.color.design_default_color_error;
                setError(contactSyncError, R.string.contact_permission_denied, color);
            }
        }
    }
}