package com.github.teocci.recyclercodesample.ui.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.teocci.recyclercodesample.R;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017/Apr/11
 *
 * ItemDecoration implementation that applies and inset margin
 * around each child of the RecyclerView. It also draws item dividers
 * that are expected from a vertical list implementation, such as
 * ListView.
 */
public class GridDividerDecoration extends RecyclerView.ItemDecoration
{
    private static final int[] ATTRS = {android.R.attr.listDivider};

    private Drawable divider;
    private int insets;

    public GridDividerDecoration(Context context)
    {
        TypedArray a = context.obtainStyledAttributes(ATTRS);
        divider = a.getDrawable(0);
        a.recycle();

        insets = context.getResources().getDimensionPixelSize(R.dimen.card_insets);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        drawVertical(c, parent);
        drawHorizontal(c, parent);
    }

    /**
     * Draw dividers at each expected grid interval
     */
    public void drawVertical(Canvas c, RecyclerView parent)
    {
        if (parent.getChildCount() == 0) return;

        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();

            final int left = child.getLeft() - params.leftMargin - insets;
            final int right = child.getRight() + params.rightMargin + insets;
            final int top = child.getBottom() + params.bottomMargin + insets;
            final int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    /**
     * Draw dividers to the right of each child view
     */
    public void drawHorizontal(Canvas c, RecyclerView parent)
    {
        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();

            final int left = child.getRight() + params.rightMargin + insets;
            final int right = left + divider.getIntrinsicWidth();
            final int top = child.getTop() - params.topMargin - insets;
            final int bottom = child.getBottom() + params.bottomMargin + insets;
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        // We can supply forced insets for each item view here in the Rect
        outRect.set(insets, insets, insets, insets);
    }
}
