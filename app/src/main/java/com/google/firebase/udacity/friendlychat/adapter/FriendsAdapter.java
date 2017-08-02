package com.google.firebase.udacity.friendlychat.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.google.firebase.udacity.friendlychat.model.Friends;

import java.util.List;

/**
 * Created by mtz-5555-transp on 02/08/17.
 */

public class FriendsAdapter  extends ArrayAdapter<Friends> {
    public FriendsAdapter(Context context, int resource, List<Friends> objects) {
        super(context, resource, objects);
    }
}
