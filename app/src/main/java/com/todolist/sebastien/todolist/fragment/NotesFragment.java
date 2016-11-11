package com.todolist.sebastien.todolist.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.BoolRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.todolist.sebastien.todolist.R;
import com.todolist.sebastien.todolist.adapter.NoteAdapter;
import com.todolist.sebastien.todolist.helper.JsonParser;
import com.todolist.sebastien.todolist.helper.NetworkHelper;
import com.todolist.sebastien.todolist.listener.RecyclerItemClickListener;
import com.todolist.sebastien.todolist.model.HttpResult;
import com.todolist.sebastien.todolist.model.Note;
import com.todolist.sebastien.todolist.session.Session;
import com.todolist.sebastien.todolist.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {

    //UI
    SwipeRefreshLayout swipeLayout;
    RecyclerView recyclerView;
    NoteAdapter adapter;

    List<Note> notes;

    GetNotesAsyncTask notesLoader;

    boolean isRefreshing = false;
    Handler handler = new Handler();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(
                R.layout.fragment_notes, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.notes_list);
        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.messages_swiperefresh);

        setupRefreshLayout();
        setupRecyclerView();

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        //loading();
        handler.post(refreshing);

    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(refreshing);
    }

    public void refresh() {
        loading();
    }

    /**
     * Load messages
     */
    private void loading() {
        if (!isRefreshing) {
            isRefreshing = true;
            swipeLayout.setRefreshing(true);
            new GetNotesAsyncTask(NotesFragment.this.getActivity()).execute();
        }
    }

    private final Runnable refreshing = new Runnable(){
        public void run(){
            Log.i("Runnable", "run");
            try {
            /* TODO : isRefreshing should be attached to your data request status */
                if(!isRefreshing){
                    refresh();
                    // re run the verification after 10 second
                    handler.postDelayed(this, 10000);
                }else{
                    // stop the animation after the data is fully loaded
                    //swipeRefreshLayout.setRefreshing(false);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * Setup refresh layout
     */
    private void setupRefreshLayout() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.post(refreshing);
                //loading();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark, R.color.colorPrimary);
    }

    /**
     * Setup recycler view.
     */
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new NoteAdapter(this.getActivity());
        recyclerView.setAdapter(adapter);

        // Add this.
        // Two scroller could have problem.
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipeLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(NotesFragment.this.getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.i("Click", "Click item " + position);

                        if (null != notes && position < notes.size()) {
                            Note note = notes.get(position);
                            new UpdateNoteAsyncTask(NotesFragment.this.getActivity()).execute(note);
                            Log.i("Note", notes.get(position).getId());
                        }


                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    /**
     * AsyncTask for sign-in
     */
    protected class GetNotesAsyncTask extends AsyncTask<String, Void, List<Note>> {

        Context context;

        public GetNotesAsyncTask(final Context context) {
            this.context = context;
        }

        @Override
        protected List<Note> doInBackground(String... params) {
            Log.i("GetNotesAsyncTask", "GetNotesAsyncTask load");
            if(!NetworkHelper.isInternetAvailable(context)){
                return null;
            }

            try {
                HttpResult result = NetworkHelper.doGet(context.getString(R.string.url_notes), null, Session.getInstance().getToken());

                if (result.code == 200) {
                    // Convert the InputStream into a string
                    return JsonParser.getNotes(result.json);
                }
                return null;
            } catch (Exception e){
                Log.d(Constants.TAG, "Error occured in your AsyncTask : ", e);
                return null;
            }
        }

        @Override
        public void onPostExecute(final List<Note> note){
            if (note != null) {
                adapter.addNote(note);
                notes = note;
            }
            swipeLayout.setRefreshing(false);
            /*Toast.makeText(context, "loaded nb notes: " +
                    notes.size(), Toast.LENGTH_LONG).show();*/

            isRefreshing = false;
        }
    }

    protected class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Note> {

        Context context;

        public UpdateNoteAsyncTask(final Context context) {
            this.context = context;
        }

        @Override
        protected Note doInBackground(Note... params) {
            if(!NetworkHelper.isInternetAvailable(context)){
                return null;
            }

            try {

                Note n = params[0];
                Map<String, String> p = new HashMap<>();
                p.put("done", n.isDone() ? "false" : "true");

                HttpResult result = NetworkHelper.doPost(context.getString(R.string.url_notes) + "/" + n.getId(), p, Session.getInstance().getToken());

                if (result.code == 200) {
                    // Convert the InputStream into a string
                    return n;
                }
                return null;
            } catch (Exception e){
                Log.d(Constants.TAG, "Error occured in your AsyncTask : ", e);
                return null;
            }
        }

        @Override
        public void onPostExecute(final Note note){
            if (note != null) {
                adapter.updateNote(note);
                notes = adapter.getNotes();
            }
            swipeLayout.setRefreshing(true);
            new GetNotesAsyncTask(NotesFragment.this.getActivity()).execute();
        }
    }
}
