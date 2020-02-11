package com.yasin.okcredit.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.yasin.okcredit.data.entity.HomeNews

class NewsItemDiffCallBack : DiffUtil.ItemCallback<HomeNews>(){
    override fun areItemsTheSame(oldItem: HomeNews, newItem: HomeNews): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HomeNews, newItem: HomeNews): Boolean {
        return oldItem == newItem
    }

}
