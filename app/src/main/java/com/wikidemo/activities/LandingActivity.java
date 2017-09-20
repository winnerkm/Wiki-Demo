package com.wikidemo.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.wikidemo.R;
import com.wikidemo.fragments.WikiFragment;

import butterknife.ButterKnife;

/**
 * Created by winnerkm on 19/09/17.
 */

public class LandingActivity extends Activity {
    private static final String TAG = LandingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);
        openFragment();
    }

    private void openFragment() {
        Fragment fragment = new WikiFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, fragment.getTag()).commit();
    }
}
