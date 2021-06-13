package com.dsoft.liverpooldirectory.ui.social

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration : RecyclerView.ItemDecoration() {

    private lateinit var mDivider: Drawable

    fun dividerItemDecoration(drawable: Drawable) {
        mDivider = drawable
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        drawVerticalDividers(canvas, parent)
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) == 0) {
            return
        }
        outRect.top = mDivider.intrinsicHeight
    }

    /**
     * Adds dividers to a RecyclerView with a LinearLayoutManager or its
     * subclass oriented horizontally
     *
     * @param canvas The {@link Canvas} onto which horizontal dividers will be drawm
     *
     * @param parent The RecyclerView onto which horizontal dividers are being added
     */
    private fun drawVerticalDividers(canvas: Canvas, parent: RecyclerView) {
        val parentLeft = parent.paddingLeft
        val parentRight = parent.width - parent.paddingRight
        val childCount = parent.childCount

        for (item in 0..childCount) {
            val child = parent.getChildAt(item)
            if (child != null) {
                val params = child.layoutParams as RecyclerView.LayoutParams
                val parentTop = child.bottom + params.bottomMargin
                val parentBottom = parentTop + mDivider.intrinsicHeight
                mDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom)
                mDivider.draw(canvas)
            }
        }
    }
}