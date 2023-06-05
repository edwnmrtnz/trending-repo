package com.edwnmrtnz.trendingrepo.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemDecoration(
    context: Context,
    resId: Int
) : RecyclerView.ItemDecoration() {

    companion object {
        private const val DEFAULT_MARGIN = 64
    }

    private var divider: Drawable = ContextCompat.getDrawable(context, resId)!!

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val dividerLeft = DEFAULT_MARGIN
        val dividerRight: Int = parent.width

        for (i in 0 until parent.childCount) {
            if (i != parent.childCount - 1) {
                val child: View = parent.getChildAt(i)

                val params = child.layoutParams as RecyclerView.LayoutParams

                val dividerTop: Int = child.bottom + params.bottomMargin
                val dividerBottom: Int = dividerTop + divider.intrinsicHeight

                divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                divider.draw(c)
            }
        }
    }
}
