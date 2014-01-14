package com.indatus.swipecontrol.samples;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.Toast;

import com.indatus.swipecontrol.library.OnSwipeListener;
import com.indatus.swipecontrol.library.SwipeControl;

public class MainActivity extends Activity implements OnSwipeListener {
	private ImageView mSampleOne, mSampleTwo, mSampleThree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSampleOne = (ImageView) findViewById(R.id.sampleOne_imgView);
		SwipeControl swipeControl1 = new SwipeControl(this, this);
		swipeControl1.setFadeOutEnabled(true);
		mSampleOne.setOnTouchListener(swipeControl1.buildTouchListener());

		mSampleTwo = (ImageView) findViewById(R.id.sampleTwo_imgView);
		SwipeControl swipeControl2 = new SwipeControl(this, this);
		swipeControl2.setFadeOutEnabled(false);
		swipeControl2.setLeftSwipeEnabled(false);
		mSampleTwo.setOnTouchListener(swipeControl2.buildTouchListener());

		mSampleThree = (ImageView) findViewById(R.id.sampleThree_imgView);
		SwipeControl swipeControl3 = new SwipeControl(this, this);
		swipeControl3.setFadeOutEnabled(true);
		swipeControl3.setRightSwipeEnabled(false);
		mSampleThree.setOnTouchListener(swipeControl3.buildTouchListener());

	}

	//     ___       _             __                  ___                 _                           _        _   _
	//    |_ _|_ __ | |_ ___ _ __ / _| __ _  ___ ___  |_ _|_ __ ___  _ __ | | ___ _ __ ___   ___ _ __ | |_ __ _| |_(_) ___  _ __  ___
	//     | || '_ \| __/ _ \ '__| |_ / _` |/ __/ _ \  | || '_ ` _ \| '_ \| |/ _ \ '_ ` _ \ / _ \ '_ \| __/ _` | __| |/ _ \| '_ \/ __|
	//     | || | | | ||  __/ |  |  _| (_| | (_|  __/  | || | | | | | |_) | |  __/ | | | | |  __/ | | | || (_| | |_| | (_) | | | \__ \
	//    |___|_| |_|\__\___|_|  |_|  \__,_|\___\___| |___|_| |_| |_| .__/|_|\___|_| |_| |_|\___|_| |_|\__\__,_|\__|_|\___/|_| |_|___/
	//                                                              |_|

	@Override
	public void onSwipeRight(View v, float delta) {
		// TODO: whatever action you would like done
	}

	@Override
	public void onSwipeLeft(View v, float delta) {
		// TODO: whatever action you would like done
	}

	@Override
	public void onSwipeStarted(View v) {
		// TODO: whatever action you would like done
	}

	@Override
	public void onSwipeRightFinished(View v, int position) {
		// TODO: whatever action you would like done (it's usually good to animate out the view)

        Toast.makeText(this, "Swipe right finished!", Toast.LENGTH_LONG).show();
    }

	@Override
	public void onSwipeLeftFinished(View v, int position) {
		// TODO: whatever action you would like done (it's usually good to animate out the view)

        Toast.makeText(this, "Swipe left finished!", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onSwipeCanceled(View v) {
		// TODO: whatever action you would like done
	}
}
