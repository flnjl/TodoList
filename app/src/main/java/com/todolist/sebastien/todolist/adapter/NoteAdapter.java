package com.todolist.sebastien.todolist.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.todolist.sebastien.todolist.R;
import com.todolist.sebastien.todolist.helper.DateHelper;
import com.todolist.sebastien.todolist.model.Note;

import java.text.ParseException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sebastien on 10/11/16.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private final Context context;

    public NoteAdapter(Context ctx) {
        this.context = ctx;
    }

    List<Note> notes = new LinkedList<Note>();

    public void addNote(List<Note> notes) {
        // inverse l'ordre, pour avoir les plus récent en haut
        Collections.reverse(notes);
        this.notes = notes;
        this.notifyDataSetChanged();
    }

    public void updateNote(Note note) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).equals(note)) {
                notes.get(i).setDone(!note.isDone());
            }
        }
    }

    public List<Note> getNotes() {
        return  notes;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int i) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View convertView = inflater.inflate(R.layout.item_note, parent, false);
        ViewHolder vh = new ViewHolder(convertView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final NoteAdapter.ViewHolder vh, final int position) {
        vh.username.setText(notes.get(position).getUsername());
        vh.note.setText(notes.get(position).getNote());
        try {
            vh.date.setText(DateHelper.getFormattedDate(notes.get(position).getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (notes.get(position).isDone()) {
            vh.done.setVisibility(View.VISIBLE);
        }
        else {
            vh.done.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (notes == null) {
            return 0;
        }
        return notes.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView note;
        TextView date;
        ImageView done;

        public ViewHolder(final View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.note_user);
            note = (TextView) itemView.findViewById(R.id.note_note);
            date = (TextView) itemView.findViewById(R.id.note_date);
            done = (ImageView) itemView.findViewById(R.id.note_done);
        }
    }

}
