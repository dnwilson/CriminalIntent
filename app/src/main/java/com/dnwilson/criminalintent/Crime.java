package com.dnwilson.criminalintent;

import java.util.UUID;

/**
 * Created by dwilson on 6/23/15.
 */
public class Crime {
    private UUID mID;
    private String mTitle;

    public Crime(){
        mID = UUID.randomUUID();
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String title) {
        this.mTitle = title;
    }

    public UUID getmID() {
        return mID;
    }

}
