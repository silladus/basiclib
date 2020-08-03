package com.example.mvvm_hilt.ui.main

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * create by silladus 2020/8/3
 * github:https://github.com/silladus
 * des:
 */
class RecyclerViewPaging(
        private val recyclerView: RecyclerView,
        private val isLoading: () -> Boolean,
        private val loadMore: (Int) -> Unit,
        private val onLast: () -> Boolean = { true }
) : RecyclerView.OnScrollListener() {

    var threshold = 10
    var currentPage: Int = 0

    var endWithAuto = false

    init {
        recyclerView.addOnScrollListener(this)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        recyclerView.layoutManager?.let {
            val visibleItemCount = it.childCount
            val totalItemCount = it.itemCount
            val firstVisibleItemPosition = when (it) {
                is LinearLayoutManager -> it.findLastVisibleItemPosition()
                is GridLayoutManager -> it.findLastVisibleItemPosition()
                else -> return
            }

            if (onLast() || isLoading()) return

            if (endWithAuto) {
                if (visibleItemCount + firstVisibleItemPosition == totalItemCount) return
            }

            if ((visibleItemCount + firstVisibleItemPosition + threshold) >= totalItemCount) {
                loadMore(++currentPage)
            }
        }
    }

    fun resetCurrentPage() {
        this.currentPage = 0
    }
}