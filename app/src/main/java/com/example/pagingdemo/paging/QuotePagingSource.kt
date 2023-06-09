package com.example.pagingdemo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingdemo.retrofit.QuotesAPI
import com.example.pagingdemo.models.Result

class QuotePagingSource(private val quotesAPI: QuotesAPI) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            // If we hv key we'll load that position or else we'll load 'page 1'
            val position = params.key ?: 1

            // Calling the API based on key
            val response = quotesAPI.getQuotes(position)

            // Creating page object
            LoadResult.Page(
                data = response.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.totalPages) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        // anchorPosition - Most recently accessed index in the list
        return state.anchorPosition?.let {
            // closestPageToPosition(anchorPosition: Int) - fun called with anchorPosition to fetch
            // the loaded page that is closet to the last accessed index in the list.
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }

//        if(state.anchorPosition != null){
//            val anchorPage = state.closestPageToPosition(state.anchorPosition!!)
//            if(anchorPage?.prevKey != null){
//                return anchorPage.prevKey!!.plus(1)
//            }
//            else if (anchorPage?.nextKey != null){
//                return anchorPage.nextKey!!.minus(1)
//            }
//        }
//        else {
//            return null
//        }
    }
}

// Official doc for PagingSource
// https://developer.android.com/reference/kotlin/androidx/paging/PagingSource