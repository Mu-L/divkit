package androidx.recyclerview.widget

import android.view.View
import com.yandex.div.core.view2.BindingContext
import com.yandex.div.core.view2.divs.gallery.DivGalleryAdapter
import com.yandex.div.core.view2.divs.gallery.DivGalleryItemHelper
import com.yandex.div.core.view2.divs.gallery.ScrollPosition
import com.yandex.div2.DivGallery

internal class DivLinearLayoutManager(
    override val bindingContext: BindingContext,
    override val view: RecyclerView,
    override val div: DivGallery,
    @RecyclerView.Orientation orientation: Int = RecyclerView.HORIZONTAL
) : LinearLayoutManager(view.context, orientation, false), DivGalleryItemHelper {

    override val childrenToRelayout = HashSet<View>()

    override fun getItemDiv(position: Int) = (view.adapter as DivGalleryAdapter).visibleItems.getOrNull(position)

    override fun toLayoutManager() = this

    override fun layoutDecorated(child: View, left: Int, top: Int, right: Int, bottom: Int) {
        super.layoutDecorated(child, left, top, right, bottom)
        _layoutDecorated(child, left, top, right, bottom)
    }

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        _onLayoutCompleted(state)
        super.onLayoutCompleted(state)
    }

    override fun layoutDecoratedWithMargins(child: View, left: Int, top: Int, right: Int, bottom: Int) {
        _layoutDecoratedWithMargins(child, left, top, right, bottom)
    }

    override fun removeAndRecycleAllViews(recycler: RecyclerView.Recycler) {
        _removeAndRecycleAllViews(recycler)
        super.removeAndRecycleAllViews(recycler)
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        _onAttachedToWindow(view)
    }

    override fun onDetachedFromWindow(view: RecyclerView, recycler: RecyclerView.Recycler) {
        super.onDetachedFromWindow(view, recycler)
        _onDetachedFromWindow(view, recycler)
    }

    override fun detachView(child: View) {
        super.detachView(child)
        _detachView(child)
    }

    override fun detachViewAt(index: Int) {
        super.detachViewAt(index)
        _detachViewAt(index)
    }

    override fun removeView(child: View) {
        super.removeView(child)
        _removeView(child)
    }

    override fun removeViewAt(index: Int) {
        super.removeViewAt(index)
        _removeViewAt(index)
    }

    override fun superLayoutDecoratedWithMargins(
        child: View,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.layoutDecoratedWithMargins(child, left, top, right, bottom)
    }

    override fun firstCompletelyVisibleItemPosition(): Int = findFirstCompletelyVisibleItemPosition()

    override fun lastCompletelyVisibleItemPosition(): Int = findLastCompletelyVisibleItemPosition()

    override fun firstVisibleItemPosition(): Int = findFirstVisibleItemPosition()

    override fun lastVisibleItemPosition(): Int = findLastVisibleItemPosition()

    override fun _getChildAt(index: Int): View? = getChildAt(index)

    override fun _getPosition(child: View): Int = getPosition(child)

    override fun width(): Int = width

    override fun getLayoutManagerOrientation(): Int = orientation

    override fun instantScrollToPosition(
        position: Int,
        scrollPosition: ScrollPosition
    ) {
        instantScroll(position, scrollPosition)
    }

    override fun instantScrollToPositionWithOffset(
        position: Int,
        offset: Int,
        scrollPosition: ScrollPosition
    ) {
        instantScroll(position, scrollPosition, offset)
    }
}
