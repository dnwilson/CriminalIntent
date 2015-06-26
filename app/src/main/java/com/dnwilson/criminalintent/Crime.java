package com.dnwilson.criminalintent;

import android.content.Context;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by dwilson on 6/23/15.
 */
public class Crime {
    private UUID mID;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime(){
        mID = UUID.randomUUID();
        mDate = new Date();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public UUID getID() {
        return mID;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        this.mSolved = solved;
    }

    public String getFormattedDate(Context context) {
        String mFormatted_Date = android.text.format.DateFormat.getMediumDateFormat(context).format(mDate);
        return mFormatted_Date;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
