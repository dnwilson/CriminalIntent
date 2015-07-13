package com.dnwilson.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by dwilson on 6/30/15.
 */
public class TimePickerFragment extends DialogFragment{
    public static final String EXTRA_TIME = "com.dnwilson.criminalintent.time";
    private final String TAG = "TimePickerFragment";
    private Date mTime;

    public static TimePickerFragment newInstance(Date time){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, time);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void sendResult(int resultCode){
        if (getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_TIME, mTime);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mTime = (Date)getArguments().getSerializable(EXTRA_TIME);
        Log.d(TAG, "onCreateDialog: mTime = " + mTime.toString());

        // Create a Calendar to get the hour, minutes and second
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTime);

        int hour   = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create time based on dialog_time layout
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker)v.findViewById(R.id.dialog_time_timePicker);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //retrieving the original crime date from the mTime value with a calendar
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mTime);
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                // Translating hourOfDay & minute into a Date object using a calendar, date keeps the same
                mTime = calendar.getTime();

                //Update argument to preserve selected value on rotation
                getArguments().putSerializable(EXTRA_TIME, mTime);
                Log.d(TAG, "Time set is: " + mTime.toString());
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK);
                            }
                        }
                )
                .create();
    }
}
