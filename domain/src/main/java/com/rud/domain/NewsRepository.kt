package com.rud.domain

import com.rud.domain.models.DomainArticle
import io.reactivex.Single

interface NewsRepository {
    fun getTopNews(country: String, category: String): Single<List<DomainArticle>>

    fun getLocalNews(): Single<List<DomainArticle>>
}