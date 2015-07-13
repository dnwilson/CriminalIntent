package com.dnwilson.criminalintent;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by dwilson on 7/3/15.
 */
public class CriminalIntentJSONSerializer {
    private static final String TAG = "JSONSerializer";
    private Context mContext;
    private String mFileName;

    public CriminalIntentJSONSerializer(Context c, String f){
        mContext = c;
        mFileName = f;
    }

    public ArrayList<Crime> loadCrimes() throws IOException, JSONException{
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;

        // see if the sd card is mounted and also check to see if the crimes file exists
        // find out if the SD card is mounted
        Boolean isSDPresent = android.os.Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);

        // set variable pointing to file so we can check if it exists
        File extCrimeFile = new File(mContext.getExternalFilesDir(null), mFileName);
        try {
            // Open and read the file into a StringBuilder
            if (isSDPresent && extCrimeFile.exists()) {
                FileInputStream extFileInputStream = new FileInputStream(extCrimeFile);
                reader = new BufferedReader(new InputStreamReader(extFileInputStream));
            } else {
                InputStream in = mContext.openFileInput(mFileName);
                reader = new BufferedReader(new InputStreamReader(in));
            }

            StringBuilder jsonString = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null){
                // Line breaks are omitted and irrelevant
                jsonString.append(line);
            }

            // Parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            // Build the array of crimes for JSONObjects
            for (int i = 0; i < array.length(); i++){
                crimes.add(new Crime(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e){
            // Ignore this one; it happens when starting fresh
        } finally {
            if (reader != null){
                reader.close();
            }
        }
        return crimes;
    }

    public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException{
        // Build an array in JSON
        JSONArray array = new JSONArray();
        // find out if the SD card is mounted
        Boolean isSDPresent = android.os.Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
        for (Crime c : crimes)
            array.put(c.toJSON());
        // Write the file to disk
        Writer writer = null;
        try {
            if (isSDPresent){
                // yes SD-card is present
                // get the external files dir
                File extDataDir = new File(mContext.getExternalFilesDir(null), mFileName);
                File extCrimeFile = new File(extDataDir.toString());
                FileOutputStream extFOS = new FileOutputStream(extCrimeFile);
                writer = new OutputStreamWriter(extFOS);
            } else {
                OutputStream out = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
                writer = new OutputStreamWriter(out);
            }
            writer.write(array.toString());
        }finally {
            if(writer != null){
                writer.close();
            }
        }
    }
}
