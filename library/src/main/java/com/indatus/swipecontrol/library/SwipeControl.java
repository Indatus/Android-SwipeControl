package com.indatus.swipecontrol.library;

//  Created by jonstaff on 11/26/13.

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class SwipeControl {

	public static final String TAG = SwipeControl.class.getSimpleName();

	private boolean mSwiping = false;
	private boolean mItemPressed = false;
	private boolean mFadeOutEnabled = false;
	private boolean mRightSwipeEnabled = true;
	private boolean mLeftSwipeEnabled = true;

	private Context mContext;

	private float mSwipeDistance;

	private static final int SWIPE_DURATION = 350;

	private OnSwipeListener mSwipeListener;

	//     ____                _                   _
	//    / ___|___  _ __  ___| |_ _ __ _   _  ___| |_ ___  _ __ ___
	//   | |   / _ \| '_ \/ __| __| '__| | | |/ __| __/ _ \| '__/ __|
	//   | |__| (_) | | | \__ \ |_| |  | |_| | (__| || (_) | |  \__ \
	//    \____\___/|_| |_|___/\__|_|   \__,_|\___|\__\___/|_|  |___/

	/**
	 * Creates an empty SwipeControl. The view will move left and right on swipe, but no actions will be assigned to the completion of the swipe until
	 * setOnSwipeListener(OnSwipeListener listener) is called. By default, the swiping distance is set to 0.4, or 40% of the view. This can be changed through
	 * setSwipeDistance(float swipeDistance). Additionally, left and right swipe are both enabled.
	 * 
	 * @param ctx
	 *            The Context the view is running in, through which it can access the current theme, resources, etc.
	 */
	public SwipeControl(Context ctx) {
		this(ctx, null);
	}

	/**
	 * Creates an instance of SwipeControl with the specified OnSwipeListener. The view will move left and right on swipe, and the actions called on completion
	 * will be determined by the OnSwipeListener passed in. By default, the swiping distance is set to 0.4, or 40% of the view. This can be changed through
	 * setSwipeDistance(float swipeDistance). Additionally, left and right swipe are both enabled.
	 * 
	 * @param ctx
	 *            The Context the view is running in, through which it can access the current theme, resources, etc.
	 * @param onSwipeListener
	 *            The OnSwipeListener being set
	 */
	public SwipeControl(Context ctx, OnSwipeListener onSwipeListener) {
		this(ctx, onSwipeListener, (float) .4);
	}

	/**
	 * Creates an instance of SwipeControl with the specified OnSwipeListener. The view will move left and right on swipe, and the actions called on completion
	 * will be determined by the OnSwipeListener passed in. The swiping distance is set to the float value passed into the constructor. This can be changed
	 * through setSwipeDistance(float swipeDistance). Additionally, left and right swipe are both enabled.
	 * 
	 * @param ctx
	 *            The Context the view is running in, through which it can access the current theme, resources, etc.
	 * @param onSwipeListener
	 *            The OnSwipeListener being set
	 * @param swipeDistance
	 *            Float value for the swipe distance before completion. This should be a value between 0 and 1 representing a percentage of the view.
	 */
	public SwipeControl(Context ctx, OnSwipeListener onSwipeListener, float swipeDistance) {
		mContext = ctx;
		mSwipeListener = onSwipeListener;
		mSwipeDistance = swipeDistance;
	}

	//        _
	//       / \   ___ ___ ___  ___ ___  ___  _ __ ___
	//      / _ \ / __/ __/ _ \/ __/ __|/ _ \| '__/ __|
	//     / ___ \ (_| (_|  __/\__ \__ \ (_) | |  \__ \
	//    /_/   \_\___\___\___||___/___/\___/|_|  |___/

	/**
	 * Sets the OnSwipeListener for the instance of SwipeControl. This should be done before calling getTouchListener() to avoid NullPointerExceptions unless
	 * defined in the constructor.
	 * 
	 * @param listener
	 *            the OnSwipeListener being set
	 */
	public void setOnSwipeListener(OnSwipeListener listener) {
		mSwipeListener = listener;
	}

	/**
	 * Returns the state of rightSwipeEnabled.
	 * 
	 * @return If the view is able to be swiped to the right, returns true; otherwise, returns false.
	 */
	public boolean isRightSwipeEnabled() {
		return mRightSwipeEnabled;
	}

	/**
	 * Sets the state of rightSwipeEnabled. Use this to enable or disable swiping functionality of the view in the right (---->) direction. If disabled, the
	 * view will not swipe to the right, nor will it respond to any actions as a result of swiping right.
	 * 
	 * @param rightSwipeEnabled
	 *            To enable right swiping, pass in true; to disable, false.
	 */
	public void setRightSwipeEnabled(boolean rightSwipeEnabled) {
		mRightSwipeEnabled = rightSwipeEnabled;
	}

	/**
	 * Returns the state of leftSwipeEnabled.
	 * 
	 * @return If the view is able to be swiped to the left, returns true; otherwise, returns false.
	 */
	public boolean isLeftSwipeEnabled() {
		return mLeftSwipeEnabled;
	}

	/**
	 * Sets the state of leftSwipeEnabled. Use this to enable or disable swiping functionality of the view in the left (<----) direction. If disabled, the view
	 * will not swipe to the left, nor will it respond to any actions as a result of swiping left.
	 * 
	 * @param leftSwipeEnabled
	 *            To enable left swiping, pass in true; to disable, false.
	 */
	public void setLeftSwipeEnabled(boolean leftSwipeEnabled) {
		mLeftSwipeEnabled = leftSwipeEnabled;
	}

	/**
	 * Returns the state of fadeOutEnabled.
	 * 
	 * @return If the view will fade out as it swipes, returns true; otherwise, false.
	 */
	public boolean isFadeOutEnabled() {
		return mFadeOutEnabled;
	}

	/**
	 * Sets the state of fadeOutEnabled. If fade out is enabled, the view being moved will fade from full opacity to full transparency as the user swipes the
	 * row. The opacity (alpha) is determined by the distance the view has moved. A larger distance will result in more transparency.
	 * 
	 * @param fadeOutEnabled
	 *            To enable fade out, pass in true; to disable, false.
	 */
	public void setFadeOutEnabled(boolean fadeOutEnabled) {
		mFadeOutEnabled = fadeOutEnabled;
	}

	//     _                _
	//    | |    ___   __ _(_) ___
	//    | |   / _ \ / _` | |/ __|
	//    | |__| (_) | (_| | | (__
	//    |_____\___/ \__, |_|\___|
	//                |___/

	/**
	 * This method provides all the swiping functionality. The other attributes should be set using the accessor methods prior to generating a
	 * View.OnTouchListener via this method, since a new instance of the OnTouchListener will be returned each time. It is highly recommended that the
	 * SwipeControl.OnSwipeListener be set before calling this method if it is not defined in the constructor.
	 * 
	 * @return a new instance of the View.OnTouchListener
	 */
	public View.OnTouchListener getTouchListener() {
		return new View.OnTouchListener() {

			float mDownX;
			private int mSwipeSlop = -1;

			@Override
			public boolean onTouch(final View v, MotionEvent event) {
				if (mSwipeSlop < 0) {
					mSwipeSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
				}

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: {

						if (mItemPressed) {
							// multi-item swipes not handled
							return false;
						}

						mItemPressed = true;
						mDownX = event.getX();
						break;
					}
					case MotionEvent.ACTION_CANCEL: {

						v.setAlpha(1);
						v.setTranslationX(0);
						mItemPressed = false;
						mSwiping = false;

						try {
							mSwipeListener.onSwipeCanceled(v);
						}
						catch (NullPointerException e) {
							e.printStackTrace();
						}

						break;
					}
					case MotionEvent.ACTION_MOVE: {

						float x = event.getX() + v.getTranslationX();
						float deltaX = x - mDownX;
						float deltaXAbs = Math.abs(deltaX);

						if (!mSwiping) {

							if (deltaXAbs > mSwipeSlop) {
								mSwiping = true;

								v.getParent().requestDisallowInterceptTouchEvent(true);
								try {
									mSwipeListener.onSwipeStarted(v);
								}
								catch (NullPointerException e) {
									e.printStackTrace();
								}
							}
						}

						if (mSwiping) {

							try {
								if (mRightSwipeEnabled && deltaX > 0) {
									v.setTranslationX(deltaX);
									mSwipeListener.onSwipeRight(v, deltaXAbs);
								} else if (mLeftSwipeEnabled && deltaX < 0) {
									v.setTranslationX(deltaX);
									mSwipeListener.onSwipeLeft(v, deltaXAbs);
								}
							}
							catch (NullPointerException e) {
								e.printStackTrace();
							}

							if (mFadeOutEnabled) {
								v.setAlpha(1 - deltaXAbs / v.getWidth());
							}
						}

						break;
					}
					case MotionEvent.ACTION_UP: {

						// user let go of the row - figure out what to do next
						if (mSwiping) {
							float x = event.getX() + v.getTranslationX();
							float deltaX = x - mDownX;
							float deltaXAbs = Math.abs(deltaX);
							float fractionCovered;
							float endX;
							float endAlpha;
							final boolean completed;

							if (deltaXAbs > v.getWidth() * mSwipeDistance) {
								// if greater than a mSwipeDistance, animate it out
								fractionCovered = deltaXAbs / v.getWidth();
								endX = (x - mDownX) < 0 ? -v.getWidth() : v.getWidth();
								endAlpha = 0;

								if ((mRightSwipeEnabled && deltaX > 0) || (mLeftSwipeEnabled && deltaX < 0)) {
									completed = true;
								} else {
									completed = false;
								}
							} else {
								// wasn't moved far enough, move it back
								fractionCovered = 1 - (deltaXAbs / v.getWidth());
								endX = 0;
								endAlpha = 1;
								completed = false;

							}

							try {
								mSwipeListener.onSwipeCanceled(v);
							}
							catch (NullPointerException e) {
								e.printStackTrace();
							}
							// Animate the position and alpha of swiped item
							// NOTE: This is a simplified version of swipe behavior, for the purposes of this demo about animation. A real version should use
							// velocity (via VelocityTracker class)
							// to send the item off or back at an appropriate speed.

							long duration = (int) (Math.abs(1 - fractionCovered) * SWIPE_DURATION);

							final boolean swipingRight;

							if (deltaX > 0) {
								swipingRight = true;
							} else {
								swipingRight = false;
							}

							if ((mRightSwipeEnabled && deltaX > 0) || (mLeftSwipeEnabled && deltaX < 0)) {
								if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
									v.animate().setDuration(duration).alpha(endAlpha).translationX(endX).withEndAction(new Runnable() {
										@Override
										public void run() {
											// restore animated values
											v.setAlpha(1);
											v.setTranslationX(0);

											try {
												if (completed) {
													if (swipingRight) {
														mSwipeListener.onSwipeRightFinished(v, 0);
													} else {
														mSwipeListener.onSwipeLeftFinished(v, 0);
													}
												}
											}
											catch (NullPointerException e) {
												e.printStackTrace();
											}
										}
									});
								} else { // TODO: this should probably be tested much more thoroughly... I'm just hoping this works
									v.animate().setDuration(duration).alpha(endAlpha).translationX(endX);

									Handler handler = new Handler();
									handler.postDelayed(new Runnable() {
										@Override
										public void run() {
											// restore animated values
											v.setAlpha(1);
											v.setTranslationX(0);

											try {
												if (completed) {
													if (swipingRight) {
														mSwipeListener.onSwipeRightFinished(v, 0);
													} else {
														mSwipeListener.onSwipeLeftFinished(v, 0);
													}
												}
											}
											catch (NullPointerException e) {
												e.printStackTrace();
											}
										}
									}, duration);
								}
							}

							mSwiping = false;
						}

						mItemPressed = false;
						break;
					}
					default:
						return false;
				}

				return true;
			}
		};
	}
}
