package com.gigasecintl.michaelvons.gigazonemain3;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    // private DatabaseReference myRef;
    private ValueEventListener mValueEventListener;
    private ListView mListViewPost;
    private AdapterTimelineActivity mAdapterTimelineActivity;
    private SwipeRefreshLayout swipeContainer;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        retrieveReport();

        mListViewPost = (ListView) findViewById(R.id.listViewPost);
        mListViewPost.setEmptyView(findViewById(R.id.empty_timeline));
        List<Report> reports = new ArrayList<>();

        mAdapterTimelineActivity = new AdapterTimelineActivity(this, R.layout.list_item_timeline, reports);
        mListViewPost.setAdapter(mAdapterTimelineActivity);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.listViewPostSwipe);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveReport();
            }
        });
    }


    private void retrieveReport() {

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("reports");
        if (myRef == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);

            myRef = database.getReference("reports");
            myRef.keepSynced(true);
        } else {
            // ...


            // FirebaseDatabase database = FirebaseDatabase.getInstance();
            // DatabaseReference myRef = database.getReference("reports");

            myRef.orderByChild("timestamp").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    mAdapterTimelineActivity.clear();

                    for (DataSnapshot reportSnapshot : dataSnapshot.getChildren()) {
                        //  Report retrieveReport = reportSnapshot.getValue(Report.class);
                        //  long relativeTime = (getTime() + retrieveReport.timestamp);
                        Log.i(TAG, "onChildAdded: EACH IS " + reportSnapshot.getValue());

                        //CharSequence relativePrettyTime = DateUtils.getRelativeTimeSpanString(retrieveReport.timestamp,getTime(),DateUtils.MINUTE_IN_MILLIS);
                        //  CharSequence relativeAllTime = getTimeAgo(retrieveReport.timestamp, getTime());
                        // Log.i(TAG, "onDataChange: Relative Time in epoch Unix is  " + relativeTime);
                        // Log.i(TAG, "onDataChange: Relative Pretty Time is " + relativePrettyTime);
                        //  Log.i(TAG, "onDataChange: Relative All Time is " + relativeAllTime);
                        //retrieveReport.setTitle("edited title");
                        //  retrieveReport.setRelativeTime(String.valueOf(relativeAllTime));
                        //mAdapterTimelineReportActivity.addAll(retrieveReport);
                        //  mAdapterTimelineActivity.addAll(retrieveReport);
                    }
                    swipeContainer.setRefreshing(false);

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
            });


            myRef.orderByChild("timestamp").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    mAdapterTimelineActivity.clear();

                    for (DataSnapshot reportSnapshot : dataSnapshot.getChildren()) {
                        Report retrieveReport = reportSnapshot.getValue(Report.class);
                        //  long relativeTime = (getTime() + retrieveReport.timestamp);

                        //CharSequence relativePrettyTime = DateUtils.getRelativeTimeSpanString(retrieveReport.timestamp,getTime(),DateUtils.MINUTE_IN_MILLIS);
                        CharSequence relativeAllTime = getTimeAgo(retrieveReport.timestamp, getTime());
                        // Log.i(TAG, "onDataChange: Relative Time in epoch Unix is  " + relativeTime);
                        // Log.i(TAG, "onDataChange: Relative Pretty Time is " + relativePrettyTime);
                        Log.i(TAG, "onDataChange: Relative All Time is " + relativeAllTime);
                        //retrieveReport.setTitle("edited title");
                        retrieveReport.setRelativeTime(String.valueOf(relativeAllTime));
                        //mAdapterTimelineReportActivity.addAll(retrieveReport);
                        mAdapterTimelineActivity.addAll(retrieveReport);
                    }
                    swipeContainer.setRefreshing(false);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            getTime();
        }

        // myRef = FirebaseDatabase.getInstance().getReference();
    }

    private long getTime() {
        long invertedTimestamp = -1 * (System.currentTimeMillis());
        long Timestamp = System.currentTimeMillis();
        Log.i(TAG, "retrieveReport: Inverted time is " + invertedTimestamp);
        Log.i(TAG, "retrieveReport: Real time is " + Timestamp);
        return invertedTimestamp;
    }

    public static CharSequence getTimeAgo(long time, long now) {


        // TODO: localize
        final long diff = time - now;
        Log.i(TAG, "getTimeAgo: Time diif is " + diff);
/*        if (diff < MINUTE_MILLIS) {
            return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.SECOND_IN_MILLIS);
        } else if (diff <  HOUR_MILLIS) {
            return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
        } else if (diff < DAY_MILLIS) {
            return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.HOUR_IN_MILLIS);
        } else {
            return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.HOUR_IN_MILLIS);
        }*/

        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }

    }


}
