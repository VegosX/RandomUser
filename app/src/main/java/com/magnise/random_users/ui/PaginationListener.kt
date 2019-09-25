//package com.magnise.random_users.ui
//
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import org.jetbrains.annotations.NotNull
//import kotlin.text.Typography.amp
//
//abstract class PaginationListener(@NotNull layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener() {
//
//    private companion object PAGE_START = 1
//
//    @NotNull
//    private var layoutManager: LinearLayoutManager = layoutManager
//
//    companion object val PAGE_SIZE = 10
//
//    override fun onScrolled(@NotNull recyclerView: RecyclerView, dx: Int, dy: Int){
//        super.onScrolled(recyclerView, dx, dy)
//        var visibleItemCount = layoutManager.childCount
//        var totalItemCount = layoutManager.itemCount
//        var firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//
//        if (!isLoading() && !isLastPage()){
//            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount)
//                amp;amp; firstVisibleItemPosition >= 0
//            amp
//        }
//    }
//
//    protected abstract fun loadMoreItems()
//    abstract fun isLastPage(): Boolean
//    abstract fun isLoading(): Boolean
//}