package com.example.talkto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatActivity extends AppCompatActivity {

    private ListView chatListView;
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerView;
    private ImageButton menuButton;
    private ImageButton sendButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        messageAdapter = new MessageAdapter(new ArrayList<>(0));
        recyclerView.setAdapter(messageAdapter);

        populateMessages();

        menuButton = findViewById(R.id.menuButton);
        sendButton = findViewById(R.id.sendButton);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message("Test User", "message test text", "14:35", true);
                messageAdapter.updateList(message);
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
            }
        });

    }

    private void populateMessages() {
        Message message1 = new Message("Usuario 3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam porttitor facilisis.", "14:35", false);
        messageAdapter.updateList(message1);
        Message message2 = new Message("Usuário 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam porttitor facilisis.", "14:35", false);
        messageAdapter.updateList(message2);
        Message message3 = new Message("Usuário 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam porttitor facilisis.", "14:35", true);
        messageAdapter.updateList(message3);
        Message message4 = new Message("Usuário 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam porttitor facilisis.", "14:35", false);
        messageAdapter.updateList(message4);
        Message message5 = new Message("Usuário 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam porttitor facilisis.", "14:35", true);
        messageAdapter.updateList(message5);
    }

}