package com.example.talkto;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MessageItemHolder extends RecyclerView.ViewHolder {
    public TextView senderName;
    public TextView messageText;
    public TextView messageTimestamp;
    public ImageView imageView;

    public MessageItemHolder(View messageView) {
        super(messageView);
        senderName = (TextView) messageView.findViewById(R.id.senderNameTextView);
        messageText = (TextView) messageView.findViewById(R.id.messageTextView);
        messageTimestamp = (TextView) messageView.findViewById(R.id.messageTimestampTextView);
        imageView = (ImageView) messageView.findViewById(R.id.imageView);
    }
}
