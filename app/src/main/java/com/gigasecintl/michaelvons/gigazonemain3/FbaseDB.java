package com.gigasecintl.michaelvons.gigazonemain3;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mike2 on 3/25/2017.
 */

public class FbaseDB {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
}
