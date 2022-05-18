package com.fyp.propertydealerapp.adapter;

import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.fyp.propertydealerapp.R;
import com.fyp.propertydealerapp.activities.chat.ChatMessageActivity;
import com.fyp.propertydealerapp.model.Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private ChatMessageActivity chatMessageActivity;
    private List<Message> messageList;
    private String userId;

    public ChatAdapter(ArrayList<Message> messageList2, ChatMessageActivity chatActivity2, String userId2) {
        this.messageList = messageList2;
        this.chatMessageActivity = chatActivity2;
        this.userId = userId2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_message_layout, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = this.messageList.get(holder.getAdapterPosition());
        long previousTs = 0;
        if (holder.getAdapterPosition() > 1) {
            previousTs = this.messageList.get(holder.getAdapterPosition() - 1).getTime();
        }
        setTimeTextVisibility(message.getTime(), previousTs, holder.textViewDate);
        if (message.getFrom().equals(this.userId)) {
            holder.sender_text_message.setVisibility(View.VISIBLE);
            holder.receiver_text_message.setVisibility(View.INVISIBLE);
            holder.user_profile_image.setVisibility(View.INVISIBLE);
            holder.sender_text_message.setTextColor(Color.BLACK);
            holder.sender_text_message.setGravity(GravityCompat.START);
            holder.sender_text_message.setText(message.getMessage());
            return;
        }
        holder.sender_text_message.setVisibility(View.INVISIBLE);
        holder.receiver_text_message.setVisibility(View.VISIBLE);
        holder.user_profile_image.setVisibility(View.VISIBLE);
        holder.receiver_text_message.setBackgroundResource(R.drawable.single_message_text_background);
        holder.receiver_text_message.setTextColor(Color.BLACK);
        holder.receiver_text_message.setGravity(GravityCompat.START);
        holder.receiver_text_message.setText(message.getMessage());
    }

    private void setTimeTextVisibility(long ts1, long ts2, TextView timeText) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTimeInMillis(ts1);
        cal2.setTimeInMillis(ts2);
        boolean sameDate = true;
        if (!(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH))) {
            sameDate = false;
        }
        if (sameDate) {
            timeText.setVisibility(View.GONE);
            timeText.setText("");
            return;
        }
        Log.e("tag", cal1.get(Calendar.DAY_OF_MONTH) + "  = = = date = = = =   " + cal2.get(Calendar.DAY_OF_MONTH));
        timeText.setVisibility(View.VISIBLE);
        timeText.setText(getFormattedDate(ts1));
    }

    public static String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.DAY_OF_MONTH) == smsTime.get(Calendar.DAY_OF_MONTH)) {
            return "Today ";
        }
        if (now.get(Calendar.DAY_OF_MONTH) - smsTime.get(Calendar.DAY_OF_MONTH) == 1) {
            return "Yesterday";
        }
        if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format("MMMM d", smsTime).toString();
        }
        return DateFormat.format("MMMM dd yyyy", smsTime).toString();
    }

    public int getItemCount() {
        return this.messageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView receiver_text_message;
        TextView sender_text_message;
        TextView textViewDate;
        ImageView user_profile_image;

        ViewHolder(View view) {
            super(view);
            this.sender_text_message = (TextView) view.findViewById(R.id.senderMessageText);
            this.receiver_text_message = (TextView) view.findViewById(R.id.receiverMessageText);
            this.user_profile_image = (ImageView) view.findViewById(R.id.messageUserImage);
            this.textViewDate = (TextView) view.findViewById(R.id.date_text);
        }
    }
}
