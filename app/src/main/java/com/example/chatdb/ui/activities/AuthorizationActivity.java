package com.example.chatdb.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatdb.R;
import com.example.chatdb.database.AppDatabase;
import com.example.chatdb.database.Entities.User;

import java.util.List;
import java.util.Objects;

/**
 * Starting authorization activity
 */
public class AuthorizationActivity extends AppCompatActivity {
    private EditText phoneEdit;
    private EditText passwordEdit;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        TextView registrationSwitch = findViewById(R.id.reg_switch);
        registrationSwitch.setOnClickListener(v -> {
            startActivity(new Intent(v.getContext(), RegistrationActivity.class));
            finish();
        });

        error = findViewById(R.id.authErrorMessage);

        phoneEdit = findViewById(R.id.phone_or_login_auth_edit);
        passwordEdit = findViewById(R.id.password_auth_edit);
    }

    /**
     * Check input fields, authorize a user and start MainActivity
     */
    public void onAuthButtonClick(View v) {
        String input = phoneEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String errorMsg = "";

        if (input.isEmpty()) {
            errorMsg = getString(R.string.enter_phone_name_err);
        }

        if (password.isEmpty()) {
            errorMsg = getString(R.string.enter_password_err);
        }

        // Find user in database
        List<User> userExist = null;
        if (errorMsg.isEmpty()) {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            userExist = db.userDao().findUsersByName(input);

            if (userExist.isEmpty()) {
                userExist = db.userDao().findUsersByPhoneNumber(input.replaceAll("[ \\-+]", ""));
                if (userExist.isEmpty()) {
                    errorMsg = getString(R.string.user_not_found_err);
                }
            }
        }

        if (errorMsg.isEmpty() && !Objects.equals(userExist.get(0).getPassword(), password)) {
            errorMsg = getString(R.string.incorrect_password_err);
        }

        if (!errorMsg.isEmpty()) {
            error.setVisibility(View.VISIBLE);
            error.setText(errorMsg);
            return;
        }

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        User curUser = userExist.get(0);
        db.setCurrentUserId(curUser.getId());
        db.setCurrentUserName(curUser.getName());
        db.setCurrentPhone(curUser.getPhoneNumber());

        startActivity(new Intent(v.getContext(), MainActivity.class));
        finish();
    }
}