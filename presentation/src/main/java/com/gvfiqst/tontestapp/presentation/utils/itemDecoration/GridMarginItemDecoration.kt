package com.gvfiqst.tontestapp.presentation.utils.itemDecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class GridMarginItemDecoration(
    private val spanCount: Int,
    private val margin: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        val left = if (position % spanCount == 0) 0 else margin
        val top = if (position < spanCount) 0 else margin
        val right = if ((position + 1) % spanCount == 0) 0 else margin

        val row = if (position == 0) 1 else (position.toDouble() / spanCount.toDouble()).toInt() + 1
        val lastRow = (itemCount.toDouble() / spanCount.toDouble()).toInt() + 1
        val bottom = if (lastRow == row) 0 else margin

        outRect.set(left, top, right, bottom)
    }

}
