package com.rud.news.view.newslist

import android.util.Log
import com.bumptech.glide.Glide
import com.rud.domain.models.DomainArticle
import com.rud.news.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_news.view.*

interface NewsListener {
    fun onNewsClick(domainArticle: DomainArticle)
}

class NewsItem(private val domainArticle: DomainArticle,
               private val listener: NewsListener) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvTitle.text = domainArticle.title
        viewHolder.itemView.tvSource.text = domainArticle.sourceName

        Log.d("tag", "url gambar -> ${domainArticle.image}")


        if (domainArticle.image.isNotEmpty()) {
            Glide.with(viewHolder.itemView.context)
                    .load(domainArticle.image)
                    .into(viewHolder.itemView.imgNews)
        }

        viewHolder.itemView.setOnClickListener {
            listener.onNewsClick(domainArticle)
        }
    }

    override fun getLayout(): Int = R.layout.item_news
}