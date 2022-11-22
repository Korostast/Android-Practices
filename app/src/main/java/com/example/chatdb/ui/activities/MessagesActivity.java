package com.example.chatdb.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatdb.R;
import com.example.chatdb.ui.adapters.MessageListAdapter;
import com.example.chatdb.database.AppDatabase;
import com.example.chatdb.database.Entities.Message;

import java.util.List;

/**
 * Chat activity
 */
public class MessagesActivity extends AppCompatActivity {
    private EditText inputField;
    private long userId;
    private MessageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        // Get messages
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        long curId = db.getCurrentUserId();
        userId = getIntent().getExtras().getLong("USER_ID");
        String userName = getIntent().getExtras().getString("USER_NAME");
        List<Message> messages = db.messageDao().getMessagesWithUser(curId, userId);

        // Fill list
        RecyclerView list = findViewById(R.id.messages_list);
        TextView chatName = findViewById(R.id.messages_chat_name);
        inputField = findViewById(R.id.messages_input_edit);
        chatName.setText(userName);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new MessageListAdapter(getApplicationContext(), messages);
        list.setAdapter(adapter);
    }

    /**
     * Send message in database, update recyclerview and clear input field
     */
    public void onSendButtonClick(View v) {
        String input = inputField.getText().toString();
        if (!input.isEmpty()) {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            Message mes = new Message(db.getCurrentUserId(), userId, input);
            db.messageDao().sendMessage(mes);
            adapter.addMessage(mes);
            inputField.getText().clear();
        }
    }
}