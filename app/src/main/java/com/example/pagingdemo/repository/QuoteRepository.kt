package com.example.pagingdemo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.pagingdemo.paging.QuotePagingSource
import com.example.pagingdemo.retrofit.QuotesAPI
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val quotesAPI: QuotesAPI){

    fun getQuotes() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { QuotePagingSource(quotesAPI)}
    ).liveData
}
