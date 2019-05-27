package com.rud.news.view.home

import com.rud.news.view.newslist.NewsListFragmentModule
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = arrayOf(NewsListFragmentModule::class))
interface HomeActivitySubcomponent : AndroidInjector<HomeActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<HomeActivity>()
}