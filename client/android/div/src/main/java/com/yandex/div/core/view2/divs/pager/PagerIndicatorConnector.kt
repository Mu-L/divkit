package com.yandex.div.core.view2.divs.pager

import com.yandex.div.core.dagger.DivScope
import com.yandex.div.core.view2.divs.widgets.DivPagerIndicatorView
import com.yandex.div.core.view2.divs.widgets.DivPagerView
import javax.inject.Inject

@DivScope
internal class PagerIndicatorConnector @Inject constructor() {
    private val divPagers = mutableMapOf<String, DivPagerView>()
    private val divIndicators = mutableMapOf<String, MutableList<DivPagerIndicatorView>>()

    internal fun submitPager(pagerId: String, divPagerView: DivPagerView) {
        divPagers[pagerId] = divPagerView
    }

    internal fun submitIndicator(pagerId: String, divPagerIndicatorView: DivPagerIndicatorView) {
        divIndicators.getOrPut(pagerId, ::mutableListOf).add(divPagerIndicatorView)
    }

    internal fun attach() {
        divPagers.forEach { (pagerId, pager) ->
            pager.clearChangePageCallbackForIndicators()
            divIndicators[pagerId]?.forEach {
                it.attachPager(pager)
            }
        }
        divPagers.clear()
        divIndicators.clear()
    }
}
