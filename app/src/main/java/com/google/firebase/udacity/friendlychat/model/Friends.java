package com.google.firebase.udacity.friendlychat.model;

/**
 * Created by mtz-5555-transp on 02/08/17.
 */

public class Friends {

    private String name;

    public Friends(){ }

    public Friends (String name){
        this.setName(name);
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
