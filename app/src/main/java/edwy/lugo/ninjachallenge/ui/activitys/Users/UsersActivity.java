package edwy.lugo.ninjachallenge.ui.activitys.Users;


import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import edwy.lugo.ninjachallenge.helper.Constants;
import edwy.lugo.ninjachallenge.interfaces.DataListener;
import edwy.lugo.ninjachallenge.R;
import edwy.lugo.ninjachallenge.helper.RESTCall;
import edwy.lugo.ninjachallenge.entidades.Users;
import edwy.lugo.ninjachallenge.ui.activitys.BaseActivity;
import edwy.lugo.ninjachallenge.ui.adapters.UsersAdapter;
import edwy.lugo.ninjachallenge.helper.Utils;

public class UsersActivity extends BaseActivity implements DataListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView listView;
    private UsersAdapter usersAdapter;
    private ArrayList<Users> users;
    private int usersId;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                onRefresh();

            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

      @Override
    public void onRefresh() {
        if (Utils.isNetworkAvailable(this)) {
            getUsersList();
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            onIssues(this, getString(R.string.problems_network));
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        /* Action Bar preparation */

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        /* */

        this.listView = (ListView) findViewById(R.id.users_listView);
        this.mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_users);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);

        Intent intent = getIntent();
        this.usersId = intent.getIntExtra(Constants.USERS_ID, 0);

        if (users == null && savedInstanceState != null && savedInstanceState.containsKey(Constants.LOADED_USERS)) {
            this.users = (ArrayList<Users>) savedInstanceState.getSerializable(Constants.LOADED_USERS);
            //this.postId = comments.get(0).getPostId();
            actionBar.setSubtitle(getString(R.string.users_list) + " " + usersId);
            showUsersList();
        } else if (users == null)
            if (Utils.isNetworkAvailable(this)) {
                getUsersList();
            } else onIssues(this, getString(R.string.problems_network));

        else showUsersList();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.users != null)
            outState.putSerializable(Constants.LOADED_USERS, this.users);
    }


    public void getUsersList() {
        showProgressDialog(this);
        ContentValues params = new ContentValues();
        params.put(Constants.USERS_ID, usersId);
        new RESTCall(this, RESTCall.GET_USERS_ACTION, params).execute();
    }


    private void showUsersList() {
        if (usersAdapter == null) {
            this.usersAdapter = new UsersAdapter(this, this.users);
            this.listView.setAdapter(usersAdapter);
        } else usersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDataReady(Bundle bundle) {
        int responseCode = bundle.getInt(Constants.RESPONSE_CODE);
        String data = bundle.getString(Constants.DATA);
        Type listType;
        listType = new TypeToken<ArrayList<Users>>() {
        }.getType();
        dismissProgressDialog();
        switch (responseCode) {
            case 200:
                try {
                    ArrayList<Users> users = new Gson().fromJson(data, listType);
                    this.users = users;
                    this.usersId = bundle.getInt(Constants.USERS_ID);
                    showUsersList();
                } catch (JsonSyntaxException e) {
                    String errorMessage = getString(R.string.response_code) + " 200" +
                            "\n" + getString(R.string.problems_parsing);
                    onIssues(this, errorMessage);
                }
                break;
            default:
                String errorMessage = getString(R.string.unsuccessful_request) + "\n" + getString(R.string.response_code) + responseCode;
                onIssues(this, errorMessage);
                break;
        }

    }


}


