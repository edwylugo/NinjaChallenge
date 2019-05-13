package edwy.lugo.ninjachallenge.ui.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edwy.lugo.ninjachallenge.R;
import edwy.lugo.ninjachallenge.entidades.Users;


class UsersViewHolderItem {
    public TextView idView;
    public TextView nameView;
    public TextView usernameView;
    public TextView emailView;
    public TextView phoneView;
    public TextView websiteView;
}

public class UsersAdapter extends BaseAdapter {

    UsersViewHolderItem usersViewHolderItem;
    LayoutInflater layoutInflater = null;
    private Context context;
    private List<Users> users;

    public UsersAdapter () {}
    public UsersAdapter(Context context, List<Users> users) {
        this.context = context;
        this.users = users;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.users_row, null);

            usersViewHolderItem = new UsersViewHolderItem();
            usersViewHolderItem.idView = (TextView) convertView.findViewById(R.id.usersId);
            usersViewHolderItem.nameView = (TextView) convertView.findViewById(R.id.usersName);
            usersViewHolderItem.usernameView = (TextView) convertView.findViewById(R.id.usersUsername);
            usersViewHolderItem.emailView = (TextView) convertView.findViewById(R.id.usersEmail);
            usersViewHolderItem.phoneView = (TextView) convertView.findViewById(R.id.usersPhone);
            usersViewHolderItem.websiteView = (TextView) convertView.findViewById(R.id.usersWebsite);
            convertView.setTag(usersViewHolderItem);

        } else

        {
            usersViewHolderItem = (UsersViewHolderItem) convertView.getTag();
        }
        Users users = (Users) getItem(position);

        if (users != null) {
            usersViewHolderItem.idView.setText(String.valueOf(users.getId()));
            usersViewHolderItem.nameView.setText(users.getName());
            usersViewHolderItem.usernameView.setText(users.getUsername());
            usersViewHolderItem.emailView.setText(users.getEmail());
            usersViewHolderItem.phoneView.setText(users.getPhone());
            usersViewHolderItem.websiteView.setText(users.getWebsite());

        }

        return convertView;
    }
}
