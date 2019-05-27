package com.rud.news.view.newslist

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.rud.domain.NewsRepository
import com.rud.domain.models.DomainArticle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private val TAG = NewsListViewModel::class.java.name

class NewsListViewModel @Inject constructor(private val repo: NewsRepository) : ViewModel() {

    // declare state for news list
    val stateLiveData = MutableLiveData<NewsListState>()

    // initiate state for news list
    init {
        stateLiveData.value = LoadingState(emptyList(), false)
    }

    fun updateNewsList() {
        d { "update news list" }
        getNewsList()
    }

    fun restoreNewsList() {
        d { "restore news list" }
        stateLiveData.value = DefaultState(obtainCurrentData(), true)
    }

    fun refreshNewsList() {
        stateLiveData.value = LoadingState(emptyList(), false)
        getNewsList()
    }

    @SuppressLint("CheckResult")
    private fun getNewsList() {
        repo.getTopNews(country = "ua", category = "technology")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNewsReceived, this::onError)
    }

    private fun onError(error: Throwable) {
        e { "error ${error.localizedMessage}" }
        stateLiveData.value = ErrorState(error.localizedMessage, obtainCurrentData(), false)
    }

    private fun onNewsReceived(news: List<DomainArticle>) {
        d { "data received ${news.size}" }
        val currentNews = obtainCurrentData().toMutableList()
        currentNews.addAll(news)
        d { "current news size ${currentNews.size}" }
        stateLiveData.value = DefaultState(currentNews, true)
    }

    private fun obtainCurrentData() = stateLiveData.value?.data ?: emptyList()

    private fun obtainCurrentLoadedAllItems() = stateLiveData.value?.loadedAllItems ?: false
}
