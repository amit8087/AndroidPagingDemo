package com.example.pagingdemo.retrofit

import com.example.pagingdemo.models.QuoteList
import retrofit2.http.GET
import retrofit2.http.Query

interface QuotesAPI {

    @GET("quotes")
    suspend fun getQuotes(@Query("page") page : Int): QuoteList
}