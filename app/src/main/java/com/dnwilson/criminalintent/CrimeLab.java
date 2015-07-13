package com.dnwilson.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by dwilson on 6/25/15.
 */
public class CrimeLab {
    private static final String TAG = "CrimeLab";
    private static final String FILE_NAME = "crimes.json";

    private ArrayList<Crime> mCrimes;
    private CriminalIntentJSONSerializer mSerializer;

    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    public CrimeLab(Context appContext){
        mAppContext = appContext;
        mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILE_NAME);
        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (Exception e){
            mCrimes = new ArrayList<>();
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    public static CrimeLab get(Context c){
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public void addCrime(Crime c){
        mCrimes.add(c);
    }

    public void deleteCrime(Crime c){
        mCrimes.remove(c);
    }

    public boolean saveCrimes(){
        try{
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e){
            Log.e(TAG, "Error saving crimes: ", e);
            return false;
        }
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime c : mCrimes){
            if (c.getID().equals(id))
                return c;
        }
        return null;
    }
}
