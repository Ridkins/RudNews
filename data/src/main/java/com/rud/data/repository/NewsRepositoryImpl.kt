package com.rud.data.repository

import android.content.Context
import com.github.ajalt.timberkt.d
import com.rud.data.NewsServices
import com.rud.data.local.ArticleDao
import com.rud.data.model.Article
import com.rud.data.utils.isNetworkStatusAvailable
import com.rud.domain.NewsRepository
import com.rud.domain.models.DomainArticle
import io.reactivex.Single

class NewsRepositoryImpl(
    private val service: NewsServices,
    private val articleDao: ArticleDao,
    private val context: Context
) : NewsRepository {

    override fun getTopNews(country: String, category: String): Single<List<DomainArticle>> {
        if (context.isNetworkStatusAvailable()) {
            return service.getTopHeadlines(country = country, category = category)
                .flattenAsObservable { it.articles }
                .map {
                    val article = DomainArticle(
                        id = 0,
                        title = it.title,
                        author = it.author ?: "",
                        image = it.urlToImage ?: "",
                        publishedAt = it.publishedAt,
                        sourceId = it.source.id ?: "",
                        sourceName = it.source.name,
                        url = it.url
                    )
                    articleDao.insert(Article(
                        id = 0,
                        title = it.title,
                        author = it.author ?: "",
                        image = it.urlToImage ?: "",
                        publishedAt = it.publishedAt,
                        sourceId = it.source.id ?: "",
                        sourceName = it.source.name,
                        url = it.url
                    ))
                    article
                }.toList()
        } else {
            return articleDao.getArticles()
                .flattenAsObservable { it }
                .map {
                    val article = DomainArticle(
                        id = 0,
                        title = it.title,
                        author = it.author ?: "",
                        image = it.image ?: "",
                        publishedAt = it.publishedAt,
                        sourceId = it.sourceId ?: "",
                        sourceName = it.sourceName,
                        url = it.url
                    )
                    d { "insert article ${article.title}" }

                    article
                }.toList()
        }
    }

    override fun getLocalNews(): Single<List<DomainArticle>> {
        return articleDao.getArticles()
            .flattenAsObservable { it }
            .map {
                val article = DomainArticle(
                    id = 0,
                    title = it.title,
                    author = it.author ?: "",
                    image = it.image ?: "",
                    publishedAt = it.publishedAt,
                    sourceId = it.sourceId ?: "",
                    sourceName = it.sourceName,
                    url = it.url
                )

                article
            }.toList()
    }

    private val articleDomainModelMapper: (DomainArticle) -> DomainArticle = { article ->
        DomainArticle(
            id = article.id,
            title = article.title,
            author = article.author,
            image = article.image,
            publishedAt = article.publishedAt,
            sourceId = article.sourceId,
            sourceName = article.sourceName,
            url = article.url
        )
    }
}