package com.abhilash.chatmqtt;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Abhilash on 17/06/2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    List<ChatMessage> messages;

    public ChatAdapter(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == 0)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_right,parent,false);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_left,parent,false);
        }
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        holder.author.setText(message.getUsername());
        holder.message.setText(message.getMessage());
        /*if(message.getUsername().equals(SettingsActivity.getChatUsername()))
        {
            holder.linearLayout.setGravity(Gravity.END);
        }
        else
        {
            holder.linearLayout.setGravity(Gravity.START);
        }*/
    }

    @Override
    public int getItemViewType(int position) {
        if(messages.get(position).getUsername().equals(SettingsActivity.getChatUsername()))
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder
    {
        TextView author,message;
        LinearLayout linearLayout;
        public ChatViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.username);
            message = (TextView) itemView.findViewById(R.id.message);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
