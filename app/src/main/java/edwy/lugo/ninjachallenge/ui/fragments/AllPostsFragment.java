package edwy.lugo.ninjachallenge.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import edwy.lugo.ninjachallenge.helper.Constants;
import edwy.lugo.ninjachallenge.interfaces.DataListener;
import edwy.lugo.ninjachallenge.interfaces.DialogInterface;
import edwy.lugo.ninjachallenge.entidades.Post;
import edwy.lugo.ninjachallenge.ui.adapters.PostAdapter;
import edwy.lugo.ninjachallenge.R;
import edwy.lugo.ninjachallenge.helper.RESTCall;
import edwy.lugo.ninjachallenge.interfaces.ToastInterface;
import edwy.lugo.ninjachallenge.ui.activitys.Users.UsersActivity;
import edwy.lugo.ninjachallenge.helper.Utils;

public class AllPostsFragment extends Fragment implements DataListener, SwipeRefreshLayout.OnRefreshListener {

    private DialogInterface mDialogInterface;
    private ToastInterface mToastInterface;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FrameLayout parentLayout;
    private ListView listView;
    private PostAdapter postAdapter;
    private String urlToProcess;
    private ArrayList<Post> posts;
    private Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mDialogInterface = (DialogInterface) context;
        this.mToastInterface = (ToastInterface) context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    /* Make a REST call to download all posts */

    public void getPostList() {
        mDialogInterface.showProgressDialog(this.mContext);
        new RESTCall(this, RESTCall.GET_POSTS_ACTION, null).execute();
    }


    /* Set up a ListAdapter with the posts in memory and put it in the layout */

    public void showPostList() {
        if (postAdapter == null) {
            this.postAdapter = new PostAdapter(getContext(), this.posts);
            this.listView.setAdapter(postAdapter);
            this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Post post = (Post) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getActivity().getApplicationContext(), UsersActivity.class);
                    intent.putExtra(Constants.USERS_ID, post.getUserId());
                    startActivity(intent);
                }
            });
        }else{
            this.postAdapter.notifyDataSetChanged();
        }


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
        if (Utils.isNetworkAvailable(mContext)) {
            getPostList();
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            this.mToastInterface.onIssues(mContext, getString(R.string.problems_network));
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        this.parentLayout = (FrameLayout) inflater.inflate(R.layout.fragment_all_posts, container, false);
        this.mSwipeRefreshLayout = (SwipeRefreshLayout) this.parentLayout.findViewById(R.id.swiperefresh_posts);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);
        this.listView = (ListView) this.parentLayout.findViewById(R.id.posts_listView);
        this.urlToProcess = Constants.BASE_URL + Constants.POSTS;


        if (posts == null && savedInstanceState != null && savedInstanceState.containsKey(Constants.LOADED_POSTS)) {
            this.posts = (ArrayList<Post>) savedInstanceState.getSerializable(Constants.LOADED_POSTS);
            showPostList();
        }else if (this.posts == null){
            if (Utils.isNetworkAvailable(mContext)) {
                getPostList();
            }else this.mToastInterface.onIssues(mContext, getString(R.string.problems_network));
        }else{
            showPostList();
        }

        return parentLayout;
    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.posts != null)
            outState.putSerializable(Constants.LOADED_POSTS, this.posts);
    }





    @Override
    public void onDataReady(Bundle bundle) {
        int responseCode = bundle.getInt(Constants.RESPONSE_CODE);
        String data = bundle.getString(Constants.DATA);
        Type listType;
        listType = new TypeToken<ArrayList<Post>>() {
        }.getType();
        mDialogInterface.dismissProgressDialog();
        switch (responseCode) {
            case 200:
                try {
                    ArrayList<Post> posts = new Gson().fromJson(data, listType);
                    this.posts = posts;
                    showPostList();

                } catch (JsonSyntaxException e) {
                    String errorMessage = getString(R.string.response_code) + " 200" +
                            "\n" + getString(R.string.problems_parsing);
                    this.mToastInterface.onIssues(mContext, errorMessage);
                }
                break;
            default:
                String errorMessage = getString(R.string.unsuccessful_request) + "\n" + getString(R.string.response_code) + responseCode;
                this.mToastInterface.onIssues(mContext, errorMessage);
                break;
        }

    }



}