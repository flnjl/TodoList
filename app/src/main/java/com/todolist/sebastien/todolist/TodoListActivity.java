package com.todolist.sebastien.todolist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.todolist.sebastien.todolist.fragment.NotesFragment;
import com.todolist.sebastien.todolist.fragment.UsersFragment;
import com.todolist.sebastien.todolist.fragment.WriteNoteDialog;
import com.todolist.sebastien.todolist.helper.PreferenceHelper;
import com.todolist.sebastien.todolist.session.Session;

import java.util.ArrayList;
import java.util.List;

public class TodoListActivity extends AppCompatActivity
                        implements WriteNoteDialog.OnWriteNoteDialogSendNoteListener {

    DrawerLayout mDrawerLayout;
    NotesFragment notesFragment;

    TextView menuUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        String token = PreferenceHelper.getToken(TodoListActivity.this); //this.getIntent().getExtras().getString("token");
        if (null == token){
            Toast.makeText(this, "Erreur token", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(TodoListActivity.this, SigninActivity.class));
            finish();
        }
        Log.i("TodoListActivity", "token " + token);
        Session.getInstance().setToken(token);

        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        if(pager != null){
            Adapter adapter = new Adapter(getSupportFragmentManager());

            notesFragment = new NotesFragment();

            adapter.addFragment(notesFragment, "Notes");
            adapter.addFragment(new UsersFragment(), "Users");

            pager.setAdapter(adapter);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if(null != tabLayout){
            tabLayout.setupWithViewPager(pager);
        }



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WriteNoteDialog.getInstance(Session.getInstance().getToken()).show(TodoListActivity.this.getSupportFragmentManager(), "write");

            }
        });
    }

    public void onWriteNoteDialogSendNoteListener() {
        // nouvelle note : update de la liste
        if (null != notesFragment) {
            notesFragment.refresh();
        }

    }


    /**
     * setup drawer.
     * @param navigationView
     */
    private void setupDrawerContent(NavigationView navigationView) {

        String username = PreferenceHelper.getUsername(TodoListActivity.this);
        Log.i("Username", username);
        if (null != username) {
            View header = navigationView.getHeaderView(0);
            menuUsername = (TextView) header.findViewById(R.id.menu_username);
            menuUsername.setText(username);
        }

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.todo_disconnect){
                            Session.getInstance().setToken(null);
                            PreferenceHelper.setToken(TodoListActivity.this, null); // delete auto login
                            TodoListActivity.this.finish();
                        } else {
                            menuItem.setChecked(true);
                            mDrawerLayout.closeDrawers();
                        }
                        return true;
                    }
                });
    }


    static class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentTitle = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position){
            return fragmentTitle.get(position);
        }
    }
}
