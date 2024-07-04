package com.example.talkto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import org.apache.commons.io.IOUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;

public class ChatActivity extends AppCompatActivity {

    private static final String SERVER_URL = "http://10.0.2.2:3000";
    private static final String userId = UUID.randomUUID().toString();
    private static final int PICK_IMAGE_REQUEST = 1;
    private MessageAdapter messageAdapter;
    private EditText messageEditText;
    private RecyclerView recyclerView;
    private ImageButton menuButton;
    private ImageButton sendButton;
    private ImageButton attachButton;

    private String name;

    private static io.socket.client.Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        menuButton = findViewById(R.id.menuButton);
        sendButton = findViewById(R.id.sendButton);
        messageEditText = findViewById(R.id.messageEditText);
        attachButton = findViewById(R.id.attachButton);

        Intent intent = getIntent();
        name = (String)intent.getStringExtra("NICKNAME");

        IO.Options options = new IO.Options();
        options.transports = new String[]{WebSocket.NAME};
        try {
            socket = IO.socket(SERVER_URL, options);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.on("receiveMessage", onNewMessage);
        socket.on("receiveImage", onNewMessage);
        socket.connect();
        sendButton.setOnClickListener(v -> sendMessage());

        attachButton.setOnClickListener(v -> openFileChooser());


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        messageAdapter = new MessageAdapter(new ArrayList<>(0));
        recyclerView.setAdapter(messageAdapter);

        menuButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(ChatActivity.this, MenuActivity.class);
            startActivity(intent1);
        });
    }

    private Emitter.Listener onConnect = args -> runOnUiThread(() -> {
        Log.d("on connect", "connected ");
    });

    private Emitter.Listener onConnectError = args -> runOnUiThread(() -> {
        if (args.length > 0) {
            Exception e = (Exception) args[0];
            Log.e("on connect error", "Erro ao conectar: " + e.getMessage(), e);
        } else {
            Log.e("on connect error", "Erro ao conectar: detalhes desconhecidos");
        }
    });

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                try {
                    String senderName = data.getString("senderName");
                    String messageText = data.getString("messageText");
                    String messageImage = data.getString("image");
                    String messageTimestamp = data.getString("messageTimestamp");
                    String messageUserId = data.getString("userId");
                    boolean messageIsSentByUser = (messageUserId.equals(userId));

                    Message message = new Message(senderName, messageText, messageTimestamp, messageIsSentByUser, userId, messageImage);
                    messageAdapter.updateList(message);
                    messageAdapter.notifyItemInserted(messageAdapter.getItemCount() - 1);
                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }
    };

    private void sendMessage() {
        String messageText = messageEditText.getText().toString().trim();
        if (!messageText.isEmpty()) {
            messageEditText.setText("");
            JSONObject message = new JSONObject();
            try {
                message.put("senderName", name);
                message.put("messageText", messageText);
                message.put("messageTimestamp", getTimeStamp());
                message.put("image", "");
                message.put("userId", userId);
                socket.emit("sendMessage", message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
        socket.off(Socket.EVENT_CONNECT, onConnect);
        socket.off("receiveMessage", onNewMessage);
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            sendImage(imageUri);
        }
    }

    private void sendImage(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            JSONObject message = new JSONObject();
            message.put("senderName", name);
            message.put("messageText", "");
            message.put("messageTimestamp", getTimeStamp());
            message.put("image", base64Image);
            message.put("userId", userId);
            socket.emit("sendImage", message);
            Log.d("chat activity", "Imagem enviada: " + message);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String currentDateTime = dateFormat.format(new Date());

        return currentDateTime;
    }

}