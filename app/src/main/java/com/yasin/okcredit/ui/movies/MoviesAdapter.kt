package com.yasin.okcredit.ui.movies

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import com.yasin.okcredit.R
import com.yasin.okcredit.data.entity.MovieNews
import com.yasin.okcredit.inflate
import com.yasin.okcredit.ui.adapter.NewsItemViewHolder
import com.yasin.okcredit.ui.details.DetailsActivity
import com.yasin.okcredit.utils.RoundedTransformation
import kotlinx.android.synthetic.main.list_item.view.*

/**
 * Created by Yasin on 17/2/20.
 */
class MoviesAdapter(private val context: Context,
                    private val newsType : String) : ListAdapter<MovieNews, NewsItemViewHolder>(MovieNewsItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(parent.inflate(R.layout.list_item))
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val news = currentList[position]
        holder.itemView.tv_author.text = news.author
        holder.itemView.tv_title.text = news.title
        if(!news.thumbnail.isNullOrEmpty()){
            Picasso.get()
                .load(news.thumbnail)
                .transform(RoundedTransformation(4f))
                .into(holder.itemView.image)
        }
        holder.itemView.setOnClickListener {
            context
                .startActivity(
                    DetailsActivity.getNewIntent(
                        news.id,
                        context,
                        newsType
                    ))
        }
    }
}