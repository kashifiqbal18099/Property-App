package com.fyp.propertydealerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.fyp.propertydealerapp.R;
import com.fyp.propertydealerapp.activities.chat.ChatActivity;
import com.fyp.propertydealerapp.activities.chat.ChatMessageActivity;
import com.fyp.propertydealerapp.fragment.ChatListFragment;
import com.fyp.propertydealerapp.model.ChatUser;
import com.fyp.propertydealerapp.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private ArrayList<ChatUser> chatUsers;
    /* access modifiers changed from: private */
    public ChatListFragment messageFragment;

    public MessageAdapter(ChatListFragment messageFragment2, ArrayList<ChatUser> strings) {
        this.messageFragment = messageFragment2;
        this.chatUsers = strings;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.messge_list_item, parent, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final User user = this.chatUsers.get(viewHolder.getAdapterPosition()).getUser();
        viewHolder.name.setText(chatUsers.get(position).getFeedItem().getUser_name());
        viewHolder.status.setText(getDate(chatUsers.get(position).getFeedItem().getLast_message_time(),"dd/MM/yyyy hh:mm:ss.SSS"));

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent  = new Intent(messageFragment.getActivity(), ChatMessageActivity.class);
                intent.putExtra("visitUserId", user.getId());
                intent.putExtra("userName", user.getFirstName()  + " "  +  user.getLastName());
                messageFragment.getActivity().startActivity(intent);

/*                Intent chatIntent = new Intent(MessageAdapter.this.messageFragment.getContext(), ChatActivity.class);
                chatIntent.putExtra("visitUserId", user.getUserId());
                chatIntent.putExtra("userName", user.getName());
                messageFragment.previodfeeditem  = null;
                ((FragmentActivity) Objects.requireNonNull(MessageAdapter.this.messageFragment.getActivity())).startActivityForResult(chatIntent,100);*/
            }
        });
    }

    public int getItemCount() {
        return this.chatUsers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView status;
        View view;

        ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.name = (TextView) itemView.findViewById(R.id.all_user_name);
            this.status = (TextView) itemView.findViewById(R.id.all_user_status);
            this.imageView = (ImageView) itemView.findViewById(R.id.all_user_profile_img);
        }
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


}
