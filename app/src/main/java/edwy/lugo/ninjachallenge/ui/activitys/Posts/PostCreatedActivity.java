package edwy.lugo.ninjachallenge.ui.activitys.Posts;

import android.os.Bundle;

import edwy.lugo.ninjachallenge.R;
import edwy.lugo.ninjachallenge.ui.activitys.BaseActivity;


public class PostCreatedActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_created);
        onIssues(this, getString(R.string.post_request_ok));
    }



}
