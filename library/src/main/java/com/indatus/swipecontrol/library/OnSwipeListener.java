package com.indatus.swipecontrol.library;

//  Created by jonstaff on 11/26/13.

import android.view.View;

/**
 * Provides the interface for overriding methods that are called when the row is swiped past the the set percentage of the row - use setSwipeDistance(float) to
 * change this.
 */
public interface OnSwipeListener {

	/**
	 * Method called when the row is swiped to the right. This gets called each time the row moves and where delta > 0.
	 * 
	 * @param v
	 *            the view (row) that is moving
	 * @param delta
	 *            float value for the distance the row has been moved
	 */
	public void onSwipeRight(View v, float delta);

	/**
	 * Method called when the row is swiped to the left. This gets called each time the row moves and where delta < 0.
	 * 
	 * @param v
	 *            the view (row) that is moving
	 * @param delta
	 *            float value for the distance the row has been moved
	 */
	public void onSwipeLeft(View v, float delta);

	/**
	 * Method called when the row swiping begins. Direction does not matter.
	 * 
	 * @param v
	 *            the view (row) that is moving
	 */
	public void onSwipeStarted(View v);

	/**
	 * Method called when the row has finished swiping to the right. This is accomplished after the delta is greater than the specified portion of the row.
	 * 
	 * @param v
	 *            the view (row) that is moving
	 * @param position
	 *            the view's position in the listview
	 */
	public void onSwipeRightFinished(View v, int position);

	/**
	 * Method called when the row has finished swiping to the left. This is accomplished after the delta is greater than the specified portion of the row.
	 * 
	 * @param v
	 *            the view (row) that is moving
	 * @param position
	 *            the view's position in the listview
	 */
	public void onSwipeLeftFinished(View v, int position);

	/**
	 * Method called when the row movement is canceled or finished. This occurs whenever ACTION_UP or ACTION_CANCEL are the motion events.
	 * 
	 * @param v
	 *            the view (row) that is moving
	 */
	public void onSwipeCanceled(View v);
}
