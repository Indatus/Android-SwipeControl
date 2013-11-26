package com.indatus.swipecontrol.library;

//  Created by jonstaff on 11/26/13.

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class BackgroundContainer extends FrameLayout {

	public static final String TAG = BackgroundContainer.class.getSimpleName();

	private boolean mShowing = false;
	private boolean mUpdateBounds = false;

	private Drawable mRowBackground;

	private int mOpenAreaTop, mOpenAreaBottom, mOpenAreaHeight;

	//      ____                _                   _
	//     / ___|___  _ __  ___| |_ _ __ _   _  ___| |_ ___  _ __ ___
	//    | |   / _ \| '_ \/ __| __| '__| | | |/ __| __/ _ \| '__/ __|
	//    | |__| (_) | | | \__ \ |_| |  | |_| | (__| || (_) | |  \__ \
	//     \____\___/|_| |_|___/\__|_|   \__,_|\___|\__\___/|_|  |___/

	public BackgroundContainer(Context context) {
		this(context, null);
	}

	public BackgroundContainer(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BackgroundContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	//        _
	//       / \   ___ ___ ___  ___ ___  ___  _ __ ___
	//      / _ \ / __/ __/ _ \/ __/ __|/ _ \| '__/ __|
	//     / ___ \ (_| (_|  __/\__ \__ \ (_) | |  \__ \
	//    /_/   \_\___\___\___||___/___/\___/|_|  |___/

	public Drawable getRowBackground() {
		return mRowBackground;
	}

	public void setRowBackground(Drawable rowBackground) {
		mRowBackground = rowBackground;
		invalidate();
	}

	public int getRowBackgroundColor() {
		return (mRowBackground instanceof ColorDrawable) ? ((ColorDrawable) mRowBackground).getColor() : -999;
	}

	public void setRowBackgroundColor(int color) {
		mRowBackground = new ColorDrawable(color);
		invalidate();
	}

	//     _                _
	//    | |    ___   __ _(_) ___
	//    | |   / _ \ / _` | |/ __|
	//    | |__| (_) | (_| | | (__
	//    |_____\___/ \__, |_|\___|
	//                |___/

	public void showBackground(int top, int bottom) {
		setWillNotDraw(false);
		mOpenAreaTop = top;
		mOpenAreaHeight = bottom;
		mShowing = true;
		mUpdateBounds = true;
	}

	public void hideBackground() {
		setWillNotDraw(true);
		mShowing = false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mShowing) {
			if (mUpdateBounds) {
				mRowBackground.setBounds(0, 0, getWidth(), mOpenAreaHeight);
			}

			canvas.save();
			canvas.translate(0, mOpenAreaTop);
			mRowBackground.draw(canvas);
			canvas.restore();
		}
	}
}
