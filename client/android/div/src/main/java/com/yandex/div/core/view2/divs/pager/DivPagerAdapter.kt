package com.yandex.div.core.view2.divs.pager

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.viewpager2.widget.ViewPager2
import com.yandex.div.core.state.DivStatePath
import com.yandex.div.core.view2.BindingContext
import com.yandex.div.core.view2.DivBinder
import com.yandex.div.core.view2.DivViewCreator
import com.yandex.div.core.view2.divs.DivCollectionAdapter
import com.yandex.div.core.view2.divs.widgets.DivPagerView
import com.yandex.div.internal.core.DivItemBuilderResult
import com.yandex.div2.DivPager

internal class DivPagerAdapter(
    items: List<DivItemBuilderResult>,
    private val bindingContext: BindingContext,
    private val divBinder: DivBinder,
    private val pageTranslations: SparseArray<Float>,
    private val viewCreator: DivViewCreator,
    path: DivStatePath,
    private val pagerView: DivPagerView,
) : DivCollectionAdapter<DivPagerViewHolder>(bindingContext, path, items) {

    val itemsToShow = object : AbstractList<DivItemBuilderResult>() {
        override val size get() = visibleItems.size + if (infiniteScrollEnabled) OFFSET_TO_REAL_ITEM * 2 else 0

        override fun get(index: Int): DivItemBuilderResult {
            if (!infiniteScrollEnabled) return visibleItems[index]

            val position = (visibleItems.size + index - OFFSET_TO_REAL_ITEM).mod(visibleItems.size)
            return visibleItems[position]
        }
    }

    val currentRealItem get() = pagerView.currentItem - offsetToRealItem

    private val offsetToRealItem get() = if (infiniteScrollEnabled) OFFSET_TO_REAL_ITEM else 0

    var orientation = ViewPager2.ORIENTATION_HORIZONTAL

    var crossAxisAlignment = DivPager.ItemAlignment.START

    var infiniteScrollEnabled = false
        set(value) {
            if (field == value) return
            field = value
            notifyItemRangeChanged(0, itemCount)
            pagerView.currentItem += if (value) OFFSET_TO_REAL_ITEM else -OFFSET_TO_REAL_ITEM
        }

    private var removedItems = 0
    private var currentItem = NO_POSITION

    fun getPosition(visibleItemIndex: Int) = visibleItemIndex + offsetToRealItem

    fun getRealPosition(rawPosition: Int) = rawPosition - offsetToRealItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DivPagerViewHolder {
        val view = DivPagerPageLayout(bindingContext.divView.context) { isHorizontal }
        return DivPagerViewHolder(
            bindingContext,
            view,
            divBinder,
            viewCreator,
            { isHorizontal },
            { crossAxisAlignment },
        )
    }

    private val isHorizontal get() = orientation == ViewPager2.ORIENTATION_HORIZONTAL

    override fun getItemCount() = itemsToShow.size

    override fun onBindViewHolder(holder: DivPagerViewHolder, position: Int) {
        super.onBindViewHolder(holder, (getRealPosition(position) + visibleItems.size) % visibleItems.size)
        pageTranslations[position]?.let {
            if (isHorizontal) {
                holder.itemView.translationX = it
            } else {
                holder.itemView.translationY = it
            }
        }
    }

    override fun setItems(newItems: List<DivItemBuilderResult>) {
        val oldSize = items.size
        removedItems = 0
        val oldCurrentItem = pagerView.currentItem
        currentItem = oldCurrentItem
        super.setItems(newItems)
        pagerView.currentItem = if (removedItems == oldSize) oldCurrentItem else currentItem
    }

    override fun notifyRawItemRemoved(position: Int) {
        removedItems++
        if (!infiniteScrollEnabled) {
            super.notifyRawItemRemoved(position)
            if (currentItem > position) {
                currentItem--
            }
            return
        }

        super.notifyRawItemRemoved(position + OFFSET_TO_REAL_ITEM)
        notifyVirtualItemsChanged(position)
        if (currentItem > position + OFFSET_TO_REAL_ITEM) {
            currentItem--
        }
    }

    override fun notifyRawItemInserted(position: Int) {
        if (!infiniteScrollEnabled) {
            super.notifyRawItemInserted(position)
            if (currentItem >= position) {
                currentItem++
            }
            return
        }

        super.notifyRawItemInserted(position + OFFSET_TO_REAL_ITEM)
        notifyVirtualItemsChanged(position)
        if (currentItem >= position + OFFSET_TO_REAL_ITEM) {
            currentItem++
        }
    }

    override fun notifyRawItemChanged(position: Int) {
        if (!infiniteScrollEnabled) {
            super.notifyRawItemChanged(position)
            return
        }

        super.notifyRawItemChanged(position + OFFSET_TO_REAL_ITEM)
        notifyVirtualItemsChanged(position)
    }

    private fun notifyVirtualItemsChanged(originalPosition: Int) {
        when (originalPosition) {
            in 0 until OFFSET_TO_REAL_ITEM -> {
                notifyItemRangeChanged(
                    visibleItems.size + originalPosition,
                    OFFSET_TO_REAL_ITEM - originalPosition
                )
            }
            in visibleItems.size - OFFSET_TO_REAL_ITEM  until visibleItems.size -> {
                notifyItemRangeChanged(
                    originalPosition - visibleItems.size + OFFSET_TO_REAL_ITEM,
                    OFFSET_TO_REAL_ITEM
                )
            }
        }
    }

    companion object {
        const val OFFSET_TO_REAL_ITEM = 2
    }
}
