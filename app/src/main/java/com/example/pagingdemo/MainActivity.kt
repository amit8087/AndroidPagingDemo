package com.example.pagingdemo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagingdemo.databinding.ActivityMainBinding
import com.example.pagingdemo.paging.QuotePagingAdapter
import com.example.pagingdemo.viewmodels.QuoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: QuotePagingAdapter
    private lateinit var quoteViewModel: QuoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quoteViewModel = ViewModelProvider(this).get(QuoteViewModel::class.java)

        adapter = QuotePagingAdapter()
        binding.rvQuoteList.layoutManager = LinearLayoutManager(this)
        binding.rvQuoteList.setHasFixedSize(true)
//        binding.rvQuoteList.adapter = adapter.withLoadStateHeaderAndFooter(
//            header = LoaderAdapter(),
//            footer = LoaderAdapter()
//        )

        binding.rvQuoteList.adapter = adapter
        adapter.addLoadStateListener { loadStates ->
            if (loadStates.refresh is LoadState.Loading || loadStates.append is LoadState.Loading) {
                /*Display progress dialog*/
                binding.pbLoadingQuotes.visibility = View.VISIBLE
            } else {
                binding.pbLoadingQuotes.visibility = View.GONE
                // If we have an error, show a toast
                val errorState = when {
                    loadStates.append is LoadState.Error -> loadStates.append as LoadState.Error
                    loadStates.prepend is LoadState.Error -> loadStates.prepend as LoadState.Error
                    loadStates.refresh is LoadState.Error -> loadStates.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        quoteViewModel.list.observe(this) {
            adapter.submitData(lifecycle, it)
        }

//        quoteViewModel.list.observe(this, Observer {
//            adapter.submitData(lifecycle, it)
//        })

    }
}