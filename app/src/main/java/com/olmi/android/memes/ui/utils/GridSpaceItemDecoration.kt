package com.olmi.android.memes.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class GridSpaceItemDecoration(space: Int, columns: Int) : RecyclerView.ItemDecoration() {

    private var mSpace: Int = space
    private var mColumns: Int = columns

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        val lp = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        val spanIndex = lp.spanIndex

        outRect.bottom = mSpace
        if (position < mColumns) {
            outRect.top = mSpace
        }
        if (spanIndex % 2 == 0) {
            outRect.left = mSpace
            outRect.right = mSpace / 2
        } else {
            outRect.right = mSpace
            outRect.left = mSpace / 2
        }
    }
}
