package com.example.katerynastorozh.newsapi.profile;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;


import com.example.katerynastorozh.newsapi.login.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfilePresenter {

    private static final String LOG_TAG = ProfilePresenter.class.getSimpleName();
    UserModel curUser;
    IProfileView profileView;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference userIdRefer;

    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = auth.getCurrentUser();
            if (firebaseUser != null)
            {
                Log.d(LOG_TAG, "onAuthStateChanged, user != null");
                profileView.toLoginpage();
            }
            else
            {
                Log.d(LOG_TAG, "onAuthStateChanged, user == null");
            }
        }
    };



    public ProfilePresenter(final IProfileView view) {
        this.profileView = view;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userIdRefer = database.getReference().child("users").child(auth.getCurrentUser().getUid());
        Log.d(LOG_TAG, userIdRefer.toString());

        if (userIdRefer == null)
        {
            Log.d(LOG_TAG, "User ref is null");
        }
        userIdRefer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               curUser = dataSnapshot.getValue(UserModel.class);
                if (curUser.getFirstName() != null)
                {
                    view.goToMainActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(LOG_TAG, databaseError.getMessage());
            }
        });

    }

    public void fillCurrentUserInfo(HashMap<String, String> info)
    {
        UserModel newUser = new UserModel.UserBuilder(curUser)
                .setFirstName(info.get("firstname"))
                .setLastName(info.get("lastname"))
                .setCity(info.get("city"))
                .setCountry(info.get("country"))
                .setDob(info.get("dob"))
                .setPhone(info.get("phone"))
                .build();
        curUser = newUser;
        userIdRefer.setValue(curUser);

    }


    public void singOut()
    {
        auth.signOut();
    }
}
