package com.gigasecintl.michaelvons.gigazonemain3;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike2 on 3/17/2017.
 */

public class AdapterTimelineActivity extends ArrayAdapter<Report> {

    private TextView textviewTitle;
    private TextView textviewType;
    private TextView textViewRelativeTime;
    private TextView textViewNoReport;

    public AdapterTimelineActivity(@NonNull Context context, @LayoutRes int resource, @NonNull List<Report> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item_timeline, parent, false);
        }

        textviewTitle = (TextView) convertView.findViewById(R.id.textviewTitle);
        textviewType = (TextView) convertView.findViewById(R.id.textviewType);
        textViewRelativeTime = (TextView) convertView.findViewById(R.id.textviewRelativeTime);

        Report retrieveReport = getItem(position);
        textviewTitle.setText(retrieveReport.getTitle());
        textviewType.setText(retrieveReport.getType());
        textViewRelativeTime.setText(retrieveReport.getRelativeTime());
        return convertView;
    }


}
