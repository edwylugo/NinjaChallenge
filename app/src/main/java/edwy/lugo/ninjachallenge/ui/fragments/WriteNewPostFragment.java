package edwy.lugo.ninjachallenge.ui.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import edwy.lugo.ninjachallenge.helper.Constants;
import edwy.lugo.ninjachallenge.interfaces.DataListener;
import edwy.lugo.ninjachallenge.ui.activitys.Posts.PostCreatedActivity;
import edwy.lugo.ninjachallenge.R;
import edwy.lugo.ninjachallenge.helper.RESTCall;

public class WriteNewPostFragment extends Fragment implements View.OnClickListener, DataListener {

    private FrameLayout parentLayout;
    private String urlToProcess;
    private String postTitle;
    private String postBody;


    public WriteNewPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        this.parentLayout = (FrameLayout) inflater.inflate(R.layout.fragment_write_new_post, container, false);
        this.urlToProcess = Constants.BASE_URL;
        Button button = (Button) parentLayout.findViewById(R.id.button_submit);
        button.setOnClickListener(this);
        return parentLayout;
    }

    @Override
    public void onClick(View v) {
        this.postTitle = parentLayout.findViewById(R.id.titleForm).toString();
        this.postBody = parentLayout.findViewById(R.id.bodyForm).toString();
        ContentValues params = new ContentValues();
        params.put(Constants.POST_TITLE, postTitle);
        params.put(Constants.POST_BODY, postBody);
        new RESTCall(this, RESTCall.NEW_POST_ACTION, params).execute();
    }

    @Override
    public void onDataReady(Bundle bundle) {
        Intent intent = new Intent(this.getContext(), PostCreatedActivity.class);
        startActivity(intent);
    }
}
