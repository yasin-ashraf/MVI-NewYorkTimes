package com.yasin.okcredit.ui.movies

import androidx.recyclerview.widget.DiffUtil
import com.yasin.okcredit.data.entity.MovieNews

class MovieNewsItemDiffCallBack: DiffUtil.ItemCallback<MovieNews>() {
    override fun areItemsTheSame(oldItem: MovieNews, newItem: MovieNews): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieNews, newItem: MovieNews): Boolean {
        return oldItem == newItem
    }
}
