package edwy.lugo.ninjachallenge.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edwy.lugo.ninjachallenge.entidades.Post;
import edwy.lugo.ninjachallenge.R;

class PostViewHolderItem {
    public TextView userIdView;
    public TextView postIdView;
    public TextView titleView;
    public TextView bodyView;
}

public class PostAdapter extends BaseAdapter {

    PostViewHolderItem postViewHolderItem;
    LayoutInflater layoutInflater = null;
    private Context context;
    private List<Post> posts;

    public PostAdapter () {}
    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }


    /* metodo che cicla fino alla fine dell'ArrayList di post */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.post_row, null);

            /* introduco un ViewHolder che contenga tutte le TextView relative ai campi di Post */

            postViewHolderItem = new PostViewHolderItem();
            postViewHolderItem.userIdView = (TextView) convertView.findViewById(R.id.userId);
            postViewHolderItem.postIdView = (TextView) convertView.findViewById(R.id.posts_postId);
            postViewHolderItem.titleView = (TextView) convertView.findViewById(R.id.title);
            postViewHolderItem.bodyView = (TextView) convertView.findViewById(R.id.posts_body);
            convertView.setTag(postViewHolderItem);
        }
        else {
            /* uso la stessa istanza di viewHolder per tutti gli elementi dell'array */

            /* prende la struttura di una post_row e la ricicla per ogni elemento, una volta mostrati
             * i suoi dati a video */

            postViewHolderItem = (PostViewHolderItem) convertView.getTag();
        }
        Post post = (Post) getItem(position);

        if (post != null) {
            postViewHolderItem.userIdView.setText(String.valueOf(post.getUserId()));
            postViewHolderItem.postIdView.setText(String.valueOf(post.getPostId()));
            postViewHolderItem.titleView.setText(post.getTitle());
            postViewHolderItem.bodyView.setText(post.getBody());
        }
        return convertView;
    }
}
