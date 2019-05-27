package com.rud.news.view.newslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rud.domain.models.DomainArticle
import com.rud.news.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.news_list_fragment.*
import javax.inject.Inject

private val TAG = NewsListFragment::class.java.name

class NewsListFragment : Fragment(), NewsListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: NewsListViewModel
    private val groupAdapter = GroupAdapter<ViewHolder>()
    private var isLoading = false

    companion object {
        fun newInstance() = NewsListFragment()
    }

    // state observer
    private val stateObserver = Observer<NewsListState> { state ->
        state?.let {
            when (state) {
                is DefaultState -> {
                    isLoading = false
                    loading.isRefreshing = false

                    it.data.map {
                        Log.d(TAG, "data -> ${it.title} ${it.sourceName}")
                    }

                    // add data to adapter
                    it.data.map {
                        Section().apply {
                            add(NewsItem(it, this@NewsListFragment))
                            groupAdapter.add(this)
                        }
                    }

                }
                is LoadingState -> {
                    Log.d(TAG, "loading state")
                    loading.isRefreshing = true
                    isLoading = true
                }

                is ErrorState -> {
                    Log.e(TAG, "error state")
                }
            }
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsListViewModel::class.java)

        setupRv()
        loading.setOnRefreshListener(this)

        observerViewModel()

        savedInstanceState?.let {
            viewModel.restoreNewsList()
        } ?: viewModel.updateNewsList()
    }

    private fun setupRv() {
        rvNews.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = groupAdapter
        }
    }

    private fun observerViewModel() {
        viewModel.stateLiveData.observe(this, stateObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stateLiveData.removeObserver(stateObserver)
    }

    override fun onNewsClick(domainArticle: DomainArticle) {
        Toast.makeText(activity, domainArticle.title, Toast.LENGTH_SHORT).show()
    }

    override fun onRefresh() {
        groupAdapter.clear()
        viewModel.refreshNewsList()
    }

}
