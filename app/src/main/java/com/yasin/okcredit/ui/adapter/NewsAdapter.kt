package com.yasin.okcredit.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.yasin.okcredit.R
import com.yasin.okcredit.data.entity.HomeNews
import com.yasin.okcredit.inflate
import kotlinx.android.synthetic.main.list_item.view.*
import timber.log.Timber
import java.lang.Exception

/**
 * Created by Yasin on 10/2/20.
 */
class NewsAdapter : ListAdapter<HomeNews, NewsItemViewHolder>(
    NewsItemDiffCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(parent.inflate(R.layout.list_item))
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val news = currentList[position]
        holder.itemView.tv_author.text = news.author
        holder.itemView.tv_title.text = news.title
        Picasso.get()
            .load(news.thumbnail)
            .into(holder.itemView.image, object : Callback {
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {
                    Timber.e(e)
                }

            })
    }
}