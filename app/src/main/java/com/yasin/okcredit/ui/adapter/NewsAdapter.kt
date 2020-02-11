package com.yasin.okcredit.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.yasin.okcredit.R
import com.yasin.okcredit.data.entity.HomeNews
import com.yasin.okcredit.inflate

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

    }
}