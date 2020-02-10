package com.yasin.okcredit.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.yasin.okcredit.data.entity.News

class NewsItemDiffCallBack : DiffUtil.ItemCallback<News>(){
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }

}
