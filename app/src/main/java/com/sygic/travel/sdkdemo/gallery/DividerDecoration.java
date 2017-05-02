package com.sygic.travel.sdkdemo.gallery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import android.support.v7.widget.RecyclerView;

// Puts grey dividers between recycler view's items
public class DividerDecoration extends RecyclerView.ItemDecoration {
	private Drawable divider;

	public DividerDecoration(Context context, int dividerId) {
		divider = ContextCompat.getDrawable(context, dividerId);
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		outRect.set(0, 0, 0, divider.getIntrinsicHeight());
	}

	@Override
	public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
		final int left = parent.getPaddingLeft();
		final int right = parent.getWidth() - parent.getPaddingRight();
		final int childCount = parent.getChildCount();

		for (int i = 0; i < childCount; i++) {
			View child = parent.getChildAt(i);

			RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

			int top = child.getBottom() + params.bottomMargin;
			int bottom = top + divider.getIntrinsicHeight();

			divider.setBounds(left, top, right, bottom);
			divider.draw(canvas);
		}
	}
}
