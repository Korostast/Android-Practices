package com.example.chatdb.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatdb.R;
import com.example.chatdb.database.AppDatabase;
import com.example.chatdb.database.Entities.User;

/**
 * Starting registration activity
 */
public class RegistrationActivity extends AppCompatActivity {
    private EditText loginEdit;
    private EditText phoneEdit;
    private EditText passwordEdit;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextView authSwitch = findViewById(R.id.auth_switch);
        authSwitch.setOnClickListener(v -> {
            startActivity(new Intent(v.getContext(), AuthorizationActivity.class));
            finish();
        });

        error = findViewById(R.id.reg_error_message);

        loginEdit = findViewById(R.id.login_reg_edit);
        phoneEdit = findViewById(R.id.phone_reg_edit);
        passwordEdit = findViewById(R.id.password_reg_edit);
    }

    /**
     * Check input fields, register new user and start MainActivity
     */
    public void onRegisterButtonClick(View v) {
        String name = loginEdit.getText().toString();
        String phone = phoneEdit.getText().toString().replaceAll("[ \\-+]", "");
        String password = passwordEdit.getText().toString();

        String errorMsg = checkLogin(name);

        if (errorMsg.isEmpty())
            errorMsg = checkPassword(password);

        if (errorMsg.isEmpty())
            errorMsg = checkPhone(phone);

        // Set error message
        if (!errorMsg.isEmpty()) {
            error.setVisibility(View.VISIBLE);
            error.setText(errorMsg);
            return;
        }

        // Create new user and start Main Activity
        User newUser = new User();
        newUser.setName(name);
        newUser.setPhoneNumber(phone);
        newUser.setPassword(password);
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        long id = db.userDao().insert(newUser);
        db.setCurrentUserId(id);
        db.setCurrentUserName(name);
        db.setCurrentPhone(phone);

        startActivity(new Intent(v.getContext(), MainActivity.class));
        finish();
    }

    /**
     * Validates login
     *
     * @param login input username
     * @return error message or empty string
     */
    private String checkLogin(String login) {
        String errorMsg = "";
        if (login.length() < 4) {
            errorMsg = getString(R.string.login_length_err);
        } else if (!(login.matches("[\\p{IsLatin}]+$"))) {
            errorMsg = getString(R.string.login_latin_err);
        } else {
            int userExist = AppDatabase.getInstance(getApplicationContext()).userDao().findUsersByName(login).size();
            if (userExist != 0) {
                errorMsg = getString(R.string.user_exist_err);
            }
        }

        return errorMsg;
    }

    /**
     * Validates password
     *
     * @param password input user password
     * @return error message or empty string
     */
    private String checkPassword(String password) {
        String errorMsg = "";
        if (password.length() < 5) {
            errorMsg = getString(R.string.password_length_err);
        } else if (!(password.matches("[0-9a-zA-Z&$_]+$"))) {
            errorMsg = getString(R.string.password_content_err);
        }

        return errorMsg;
    }

    /**
     * Validates phone number
     *
     * @param phone input phone number
     * @return error message or empty string
     */
    private String checkPhone(String phone) {
        String errorMsg = "";

        int userExist = AppDatabase.getInstance(getApplicationContext()).userDao().findUsersByPhoneNumber(phone).size();
        if (phone.length() == 0) {
            errorMsg = getString(R.string.phone_empty_err);
        } else if (!(phone.matches("[0-9]+$"))) {
            errorMsg = getString(R.string.phone_restrictions);
        } else if (userExist != 0) {
            errorMsg = getString(R.string.user_phone_exist_err);
        }

        return errorMsg;
    }
}