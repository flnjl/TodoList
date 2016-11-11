package com.todolist.sebastien.todolist.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.todolist.sebastien.todolist.R;
import com.todolist.sebastien.todolist.helper.DateHelper;
import com.todolist.sebastien.todolist.model.User;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sebastien on 10/11/16.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<User> users = new LinkedList<User>();
    Context context;

    public UserAdapter(Context ctx){
        this.context = ctx;
    }

    public void setUser(List<User> users){
        this.users = users;
        notifyDataSetChanged();
    }


    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View convertView = inflater.inflate(R.layout.item_user, parent, false);
        ViewHolder vh = new ViewHolder(convertView);
        return vh;
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder vh, int position) {
        vh.username.setText(users.get(position).getUsername());

        try {
            vh.date.setText(DateHelper.getFormattedDate(users.get(position).getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(users == null){
            return 0;
        }
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView date;

        public ViewHolder(final View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.user_name);
            date = (TextView) itemView.findViewById(R.id.user_date);
        }
    }
}
