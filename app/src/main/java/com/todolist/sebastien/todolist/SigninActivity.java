package com.todolist.sebastien.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.todolist.sebastien.todolist.helper.JsonParser;
import com.todolist.sebastien.todolist.helper.NetworkHelper;
import com.todolist.sebastien.todolist.helper.PreferenceHelper;
import com.todolist.sebastien.todolist.model.HttpResult;
import com.todolist.sebastien.todolist.session.Session;
import com.todolist.sebastien.todolist.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {

    EditText username;
    EditText pwd;
    ProgressBar pg;
    Button btn;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_signin);
        username = (EditText) findViewById(R.id.signin_username);
        pwd = (EditText) findViewById(R.id.signin_pwd);
        pg = (ProgressBar) findViewById(R.id.signin_pg);
        btn = (Button) findViewById(R.id.signin_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading(true);
                new SigninAsyncTask(v.getContext()).execute(username.getText().toString(), pwd.getText().toString());
            }
        });
        findViewById(R.id.signin_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), SignupActivity.class);
                startActivity(i);
            }
        });

    }

    private void loading(boolean loading) {
        if(loading){
            pg.setVisibility(View.VISIBLE);
            btn.setVisibility(View.INVISIBLE);
        } else {
            pg.setVisibility(View.INVISIBLE);
            btn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * AsyncTask for sign-in
     */
    protected class SigninAsyncTask extends AsyncTask<String, Void, String[]> {

        Context context;

        public SigninAsyncTask(final Context context) {
            this.context = context;
        }

        @Override
        protected String[] doInBackground(String... params) {
            if(!NetworkHelper.isInternetAvailable(context)){
                return null;
            }

            try {

                Map<String, String> p = new HashMap<>();
                p.put("username", params[0]);
                p.put("pwd", params[1]);

                HttpResult result = NetworkHelper.doPost(context.getString(R.string.url_signin), p, null);

                if(result.code == 200) {
                    // Convert the InputStream into a string
                    Log.i("Connection OK", JsonParser.getToken(result.json));
                    return new String[]{JsonParser.getToken(result.json), params[0]};
                }
                return null;
            } catch (Exception e){
                Log.d(Constants.TAG, "Error occured in your AsyncTask : ", e);
                return null;
            }
        }

        @Override
        public void onPostExecute(final String prefs[]){
            loading(false);

            if (null != prefs) {

                String token = prefs[0];
                String username = prefs[1];

                if (token != null) {
                    PreferenceHelper.setToken(SigninActivity.this, token);
                    PreferenceHelper.setUsername(SigninActivity.this, username);
                    Session.getInstance().setToken(token);
                    startActivity(new Intent(context, TodoListActivity.class));
                } else {
                    Toast.makeText(context, context.getString(R.string.error_login), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, context.getString(R.string.error_login), Toast.LENGTH_LONG).show();
            }
        }
    }

}
