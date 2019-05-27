package com.rud.news.view.newslist

import com.rud.domain.models.DomainArticle

sealed class NewsListState {
    abstract val data: List<DomainArticle>
    abstract val loadedAllItems: Boolean
}

data class DefaultState(override val data: List<DomainArticle>, override val loadedAllItems: Boolean) : NewsListState()
data class LoadingState(override val data: List<DomainArticle>, override val loadedAllItems: Boolean) : NewsListState()
data class ErrorState(val errorMessage: String, override val data: List<DomainArticle>, override val loadedAllItems: Boolean) : NewsListState()