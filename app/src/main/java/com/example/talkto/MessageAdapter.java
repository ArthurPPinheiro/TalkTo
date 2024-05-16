package com.example.talkto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageItemHolder> {

    private final List<Message> messagesList;

    public MessageAdapter(ArrayList messages) {
        messagesList = messages;
    }

    @Override
    public int getItemViewType(int position) {
        if(messagesList.get(position).isSentByUser()){
            return 1;
        } else {
            return 2;
        }
    }

    @NonNull
    @Override
    public MessageItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;

        if(viewType == 1){
            itemView = inflater.inflate(R.layout.sent_message_view, parent, false);
        } else {
            itemView = inflater.inflate(R.layout.received_message_view, parent, false);
        }

        return new MessageItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageItemHolder holder, int position) {
        holder.senderName.setText(messagesList.get(position).getSenderName());
        holder.messageText.setText(messagesList.get(position).getMessageText());
        holder.messageTimestamp.setText(messagesList.get(position).getMessageTimestamp());
    }

    @Override
    public int getItemCount() {
        return messagesList != null ? messagesList.size() : 0;
    }

    public void updateList(Message message) {
        insertItem(message);
    }

    private void insertItem(Message message) {
        messagesList.add(message);
        notifyItemInserted(getItemCount());
    }

}