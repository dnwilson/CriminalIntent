package com.dnwilson.criminalintent;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by dwilson on 6/23/15.
 */
public class Crime {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_TIME = "time";
    private static final String JSON_PHOTO = "photo";

    private UUID mID;
    private String mTitle;
    private Date mDate;
    private Date mTime;
    private boolean mSolved;
    private Photo mPhoto;

    public Crime(){
        mID = UUID.randomUUID();
        mDate = new Date();
        mTime = mDate;
    }

    public Crime(JSONObject json) throws JSONException{
        mID = UUID.fromString(json.getString(JSON_ID));
        if (json.has(JSON_TITLE)){
            mTitle = json.getString(JSON_TITLE);
        }
        mSolved = json.getBoolean(JSON_SOLVED);
        mDate = new Date(json.getLong(JSON_DATE));
        mTime = new Date(json.getLong(JSON_TIME));
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mID.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_SOLVED, mSolved);
        json.put(JSON_DATE, mDate.getTime());
        json.put(JSON_TIME, mTime.getTime());
        if (mPhoto != null)
            json.put(JSON_PHOTO, mPhoto.toJSON());
        return json;
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

    public Date getTime() { return mTime; }

    public void setTime(Date time) { this.mTime = time; }

    public boolean isSolved() { return mSolved; }

    public void setSolved(boolean solved) { this.mSolved = solved; }

    public String getPrettyDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("ccc MMM dd, yyyy");
        String mPrettyDate = sdf.format(mDate);
        return mPrettyDate;
    }

    public String getPrettyTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String mPrettyTime = sdf.format(mTime);
        return mPrettyTime;
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public void setPhoto(Photo p) {
        this.mPhoto = p;
    }

    @Override
    public String toString() { return mTitle; }
}
