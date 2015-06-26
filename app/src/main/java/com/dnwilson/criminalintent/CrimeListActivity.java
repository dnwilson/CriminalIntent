package com.dnwilson.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by dwilson on 6/25/15.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
