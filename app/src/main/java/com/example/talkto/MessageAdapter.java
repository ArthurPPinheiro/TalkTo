package com.example.talkto;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    private List<Message> messages;

    public MessageAdapter(Context context, List<Message> messages) {
        super(context, 0, messages);
        this.messages = messages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            // Determine o tipo de mensagem (enviada ou recebida)
            Message message = messages.get(position);
            if (message.isSentByUser()) {
                // Mensagem enviada pelo usuário
                view = inflater.inflate(R.layout.list_item_sent_message, parent, false);
            } else {
                // Mensagem recebida (do outro usuário)
                view = inflater.inflate(R.layout.list_item_received_message, parent, false);
            }
        }

        TextView senderNameTextView = view.findViewById(R.id.senderNameTextView);

        // Obtenha a mensagem atual
        Message message = messages.get(position);

        // Encontre o TextView apropriado no layout inflado
        TextView messageTextView;
        TextView messageTimeTextView;
        if (message.isSentByUser()) {
            messageTextView = view.findViewById(R.id.sentMessageTextView);
            messageTimeTextView = view.findViewById(R.id.sentMessageTimeTextView);
            senderNameTextView.setText("Usuario 1");
            messageTimeTextView.setText("02:30 pm");
            messageTextView.setGravity(Gravity.START);
        } else {
            messageTextView = view.findViewById(R.id.receivedMessageTextView);
            messageTimeTextView = view.findViewById(R.id.receivedMessageTimeTextView);
            senderNameTextView.setText("Usuario 2");
            messageTimeTextView.setText("04:30 pm");
            messageTextView.setGravity(Gravity.END);
        }

        // Defina o texto da mensagem no TextView
        messageTextView.setText(message.getText());

        return view;
    }
}
