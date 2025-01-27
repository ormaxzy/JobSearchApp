package com.example.ui.adapters

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.domain.models.Offer
import com.example.ui.R
import com.example.ui.databinding.ItemRecommendationBinding
import com.example.ui.utils.RecommendationType
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding


fun recommendationAdapterDelegate() =
    adapterDelegateViewBinding<Offer, Offer, ItemRecommendationBinding>(
        { layoutInflater, root -> ItemRecommendationBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            with(binding) {
                recommendationTitle.text = item.title
                recommendationButtonText.text = item.buttonText ?: ""
                recommendationButtonText.visibility =
                    if (item.buttonText != null) View.VISIBLE else View.GONE

                val recommendationType =
                    item.id?.let { RecommendationType.fromId(it) } ?: RecommendationType.UNKNOWN

                if (recommendationType == RecommendationType.UNKNOWN) {
                    iconContainer.visibility = View.GONE
                } else {
                    iconContainer.visibility = View.VISIBLE
                    recommendationIcon.setImageResource(recommendationType.iconRes)
                    val color = ContextCompat.getColor(context, recommendationType.backgroundRes)
                    circleBackground.backgroundTintList = ColorStateList.valueOf(color)
                }

                root.setOnClickListener {
                    try {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
                        context.startActivity(browserIntent)
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.failed_open_link),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }
