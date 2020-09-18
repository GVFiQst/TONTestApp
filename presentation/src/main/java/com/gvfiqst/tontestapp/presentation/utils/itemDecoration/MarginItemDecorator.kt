package com.gvfiqst.tontestapp.presentation.utils.itemDecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecorator(
    private val marginTop: Int = 0,
    private val marginBottom: Int = 0,
    private val marginLeft: Int = 0,
    private val marginRight: Int = 0
) : RecyclerView.ItemDecoration() {

    constructor(margin: Int) : this(margin, margin, margin, margin)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(marginLeft, marginTop, marginRight, marginBottom)
    }

}
