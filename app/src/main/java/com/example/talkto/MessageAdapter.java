package com.example.talkto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageAdapter extends RecyclerView.Adapter<MessageItemHolder> {

    private final List<Message> messagesList;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_IMAGE_SENT = 3;
    private static final int VIEW_TYPE_IMAGE_RECEIVED = 4;


    public MessageAdapter(ArrayList messages) {
        messagesList = messages;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messagesList.get(position);
        if(message.isSentByUser()){
            if (!Objects.equals(message.getMessageText(), "")) {
                return VIEW_TYPE_MESSAGE_SENT;
            } else {
                return VIEW_TYPE_IMAGE_SENT;
            }
        } else {
            if (!Objects.equals(message.getMessageText(), "")) {
                return VIEW_TYPE_MESSAGE_RECEIVED;
            } else {
                return VIEW_TYPE_IMAGE_RECEIVED;
            }
        }
    }

    @NonNull
    @Override
    public MessageItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;

        if(viewType == VIEW_TYPE_MESSAGE_SENT){
            itemView = inflater.inflate(R.layout.sent_message_view, parent, false);
        } else if(viewType == VIEW_TYPE_MESSAGE_RECEIVED){
            itemView = inflater.inflate(R.layout.received_message_view, parent, false);
        } else if (viewType == VIEW_TYPE_IMAGE_SENT) {
            itemView = inflater.inflate(R.layout.sent_image_view, parent, false);
        } else {
            itemView = inflater.inflate(R.layout.received_image_view, parent, false);
        }

        return new MessageItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageItemHolder holder, int position) {
        Message message = messagesList.get(position);
        if(!Objects.equals(message.getMessageText(), "")) {
            Log.d("message adapter", "onBindViewHolder:text ");
            holder.senderName.setText(messagesList.get(position).getSenderName());
            holder.messageText.setText(messagesList.get(position).getMessageText());
            holder.messageTimestamp.setText(messagesList.get(position).getMessageTimestamp());
        } else {
            Log.d("message adapter", "onBindViewHolder:image ");

            holder.senderName.setText(message.getSenderName());
            holder.messageTimestamp.setText(messagesList.get(position).getMessageTimestamp());
            byte[] imageBytes = Base64.decode(message.getImage(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imageView.setImageBitmap(bitmap);
        }

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