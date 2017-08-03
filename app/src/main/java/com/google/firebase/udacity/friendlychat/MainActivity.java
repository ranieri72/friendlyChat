/**
 * Copyright Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.udacity.friendlychat.adapter.FriendsAdapter;
import com.google.firebase.udacity.friendlychat.model.Friends;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;
    private static final String TAG = "MainActivity";

    @BindView(R.id.friendsListView)
    ListView mFriendsListView;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.newFriendEditText)
    EditText mNewFriendEditText;

    private Friends mSender;
    private FriendsAdapter mFriendsAdapter;
    private FirebaseAuth mFirebaseAuth;
    private AuthStateListener mAuthStateListener;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mFriendsDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSender = new Friends(ANONYMOUS);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFriendsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("friends");

        // Initialize message ListView and its adapter
        List<Friends> friendsList = new ArrayList<>();
        mFriendsAdapter = new FriendsAdapter(this, friendsList);
        mFriendsListView.setAdapter(mFriendsAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        mAuthStateListener = new AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                }

            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_SIGN_IN:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "Signed in", Toast.LENGTH_LONG).show();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Signed in canceled", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseReadListener();
        mFriendsAdapter.clear();
    }

    private void onSignedInInitialize(String userName) {
        mSender.setName(userName);
        attachDatabaseReadListener();
    }

    private void onSignedOutCleanup() {
        mSender.setName(ANONYMOUS);
        detachDatabaseReadListener();
        mFriendsAdapter.clear();
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mFriendsDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Friends friend = dataSnapshot.getValue(Friends.class);
                    mFriendsAdapter.add(friend);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mFriendsDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    @OnClick(R.id.newFriendButton)
    public void submit(View view) {
        switch (view.getId()) {
            case R.id.newFriendButton:
                newFriend(new Friends(mNewFriendEditText.getText().toString()));
                mNewFriendEditText.setText("");
                break;
        }
    }

    @OnItemClick(R.id.friendsListView)
    void onItemSelected(int position) {
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_LONG).show();
        Intent it = new Intent(this, MessageActivity.class);
        it.putExtra("friends", Parcels.wrap(mFriendsAdapter.getItem(position)));
        startActivity(it);
    }

    private void newFriend(Friends friend) {
        Toast.makeText(this, friend.getName(), Toast.LENGTH_LONG).show();
        //mFriendsDatabaseReference.push().setValue(friend);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}