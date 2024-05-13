package com.example.talkto;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChatActivity extends AppCompatActivity {

    private ListView chatListView;
    private MessageAdapter messageAdapter;
    private List<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageButton menuButton = findViewById(R.id.menuButton);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Inicialize a lista de mensagens
        messages = new ArrayList<>();
        messages.add(new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam porttitor facilisis.", false));
        messages.add(new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam porttitor facilisis.", false));
        messages.add(new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam porttitor facilisis.", true));
        messages.add(new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam porttitor facilisis.", false));
        messages.add(new Message("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam porttitor facilisis.", true));

        // Inicialize e defina o adapter na ListView
        messageAdapter = new MessageAdapter(this, messages);
        chatListView = findViewById(R.id.chatListView);
        chatListView.setAdapter(messageAdapter);
    }
}