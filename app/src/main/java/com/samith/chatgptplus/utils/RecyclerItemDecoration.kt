package com.samith.chatgptplus.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Adds spacing to the top and bottom of the items in a RecyclerView.
 * The last item will have bottom spacing, and the first item will have top spacing.
 *
 * Created by samithchow on 3/25/2023.
 * website - samith.dev
 */
class RecyclerItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // Add bottom spacing to last item
        if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
            outRect.bottom = 30
        }

        // Add top spacing to first item
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = 30
        }
    }
}