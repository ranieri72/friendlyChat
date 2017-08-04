package com.google.firebase.udacity.friendlychat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.udacity.friendlychat.model.User;

import java.util.List;

/**
 * Created by mtz-5555-transp on 02/08/17.
 */

public class FriendsAdapter extends ArrayAdapter<User> {

    public FriendsAdapter(Context context, List<User> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //1)
        User user = getItem(position);

        //2)
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, null);
            vh = new ViewHolder();

            //3)
            vh.txtFriend = (TextView) convertView.findViewById(android.R.id.text1);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.txtFriend.setText(user.getName());

        //4)
        return convertView;
    }

    private static class ViewHolder {
        TextView txtFriend;
    }
}
