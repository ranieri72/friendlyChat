package com.google.firebase.udacity.friendlychat.model;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mtz-5555-transp on 02/08/17.
 */

@Parcel(Parcel.Serialization.BEAN)
public class User {

    private String id;
    private String name;
    private List<User> friends;

    public User() {
        friends = new ArrayList<>();
    }

    public User(String name) {
        friends = new ArrayList<>();
        this.setName(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
