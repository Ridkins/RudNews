package com.rud.news.di

import android.content.Context
import com.rud.data.NewsServices
import com.rud.data.local.ArticleDao
import com.rud.data.repository.NewsRepositoryImpl
import com.rud.domain.NewsRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideNewsRepo(newsServices: NewsServices,
                        articleDao: ArticleDao,
                        context: Context): NewsRepository = NewsRepositoryImpl(newsServices, articleDao, context)
}