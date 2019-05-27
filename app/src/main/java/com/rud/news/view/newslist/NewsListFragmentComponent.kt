package com.rud.news.view.newslist

import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Subcomponent
interface NewsListFragmentSubcomponent : AndroidInjector<NewsListFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<NewsListFragment>()
}

@Module(subcomponents = arrayOf(NewsListFragmentSubcomponent::class))
abstract class NewsListFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(NewsListFragment::class)
    abstract fun bindCryptoListFragmentInjectorFactory(builder: NewsListFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>
}