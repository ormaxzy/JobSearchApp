package com.example.ui.adapters

import android.annotation.SuppressLint
import com.example.domain.models.Offer
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter


class RecommendationAdapter :
    ListDelegationAdapter<List<Offer>>(recommendationAdapterDelegate()) {
    init {
        items = emptyList()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: List<Offer>) {
        items = data
        notifyDataSetChanged()
    }
}
