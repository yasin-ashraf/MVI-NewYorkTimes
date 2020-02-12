package com.yasin.okcredit.ui.adapter

import android.graphics.*
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import com.yasin.okcredit.R
import com.yasin.okcredit.data.entity.HomeNews
import com.yasin.okcredit.inflate
import com.yasin.okcredit.utils.RoundedTransformation
import kotlinx.android.synthetic.main.list_item.view.*

/**
 * Created by Yasin on 10/2/20.
 */
class NewsAdapter : ListAdapter<HomeNews, NewsItemViewHolder>(NewsItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(parent.inflate(R.layout.list_item))
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val news = currentList[position]
        holder.itemView.tv_author.text = news.author
        holder.itemView.tv_title.text = news.title
        Picasso.get()
            .load(news.thumbnail)
            .transform(RoundedTransformation(4f))
            .into(holder.itemView.image)
    }
}