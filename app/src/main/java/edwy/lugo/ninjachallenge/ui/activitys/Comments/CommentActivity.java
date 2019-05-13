package edwy.lugo.ninjachallenge.ui.activitys.Comments;


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

import edwy.lugo.ninjachallenge.ui.activitys.BaseActivity;
import edwy.lugo.ninjachallenge.entidades.Comment;
import edwy.lugo.ninjachallenge.ui.adapters.CommentAdapter;
import edwy.lugo.ninjachallenge.helper.Constants;
import edwy.lugo.ninjachallenge.interfaces.DataListener;
import edwy.lugo.ninjachallenge.R;
import edwy.lugo.ninjachallenge.helper.RESTCall;
import edwy.lugo.ninjachallenge.helper.Utils;

public class CommentActivity extends BaseActivity implements DataListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView listView;
    private CommentAdapter commentAdapter;
    private ArrayList<Comment> comments;
    private int postId;


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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        if (Utils.isNetworkAvailable(this)) {
            getCommentList();
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            onIssues(this, getString(R.string.problems_network));
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        /* Action Bar preparation */

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        /* */

        this.listView = (ListView) findViewById(R.id.comments_listView);
        this.mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_comments);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);

        Intent intent = getIntent();
        this.postId = intent.getIntExtra(Constants.POST_ID, 0);

        if (comments == null && savedInstanceState != null && savedInstanceState.containsKey(Constants.LOADED_COMMENTS)) {
            this.comments = (ArrayList<Comment>) savedInstanceState.getSerializable(Constants.LOADED_COMMENTS);
            //this.postId = comments.get(0).getPostId();
            actionBar.setSubtitle(getString(R.string.post) + " " + postId);
            showCommentList();
        } else if (comments == null)
            if (Utils.isNetworkAvailable(this)) {
                getCommentList();
            } else onIssues(this, getString(R.string.problems_network));

        else showCommentList();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.comments != null)
            outState.putSerializable(Constants.LOADED_COMMENTS, this.comments);
    }


    public void getCommentList() {
        showProgressDialog(this);
        ContentValues params = new ContentValues();
        params.put(Constants.POST_ID, postId);
        new RESTCall(this, RESTCall.GET_COMMENTS_ACTION, params).execute();
    }


    private void showCommentList() {
        if (commentAdapter == null) {
            this.commentAdapter = new CommentAdapter(this, this.comments);
            this.listView.setAdapter(commentAdapter);
        } else commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDataReady(Bundle bundle) {
        int responseCode = bundle.getInt(Constants.RESPONSE_CODE);
        String data = bundle.getString(Constants.DATA);
        Type listType;
        listType = new TypeToken<ArrayList<Comment>>() {
        }.getType();
        dismissProgressDialog();
        switch (responseCode) {
            case 200:
                try {
                    ArrayList<Comment> comments = new Gson().fromJson(data, listType);
                    this.comments = comments;
                    this.postId = bundle.getInt(Constants.POST_ID);
                    showCommentList();
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


