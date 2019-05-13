package edwy.lugo.ninjachallenge.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edwy.lugo.ninjachallenge.entidades.Comment;
import edwy.lugo.ninjachallenge.R;

class CommentViewHolderItem {
    public TextView commentIdView;
    public TextView nameView;
    public TextView emailView;
    public TextView bodyView;
}

public class CommentAdapter extends BaseAdapter {

    CommentViewHolderItem commentViewHolderItem;
    LayoutInflater layoutInflater = null;
    private Context context;
    private List<Comment> comments;

    public CommentAdapter () {}
    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.comment_row, null);

            commentViewHolderItem = new CommentViewHolderItem();
            commentViewHolderItem.commentIdView = (TextView) convertView.findViewById(R.id.commentId);
            commentViewHolderItem.nameView = (TextView) convertView.findViewById(R.id.name);
            commentViewHolderItem.emailView = (TextView) convertView.findViewById(R.id.email);
            commentViewHolderItem.bodyView = (TextView) convertView.findViewById(R.id.comments_body);
            convertView.setTag(commentViewHolderItem);
        }
        else {


            commentViewHolderItem = (CommentViewHolderItem) convertView.getTag();
        }
        Comment comment = (Comment) getItem(position);

        if (comment != null) {
            commentViewHolderItem.commentIdView.setText(String.valueOf(comment.getCommentId()));
            commentViewHolderItem.nameView.setText(comment.getName());
            commentViewHolderItem.emailView.setText(comment.getEmail());
            commentViewHolderItem.bodyView.setText(comment.getBody());
        }
        return convertView;
    }
}

