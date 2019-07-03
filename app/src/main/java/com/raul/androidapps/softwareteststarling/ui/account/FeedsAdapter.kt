package com.raul.androidapps.softwareteststarling.ui.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.softwareteststarling.R
import com.raul.androidapps.softwareteststarling.databinding.FeedItemBinding
import com.raul.androidapps.softwareteststarling.databinding.StarlingBindingComponent
import com.raul.androidapps.softwareteststarling.extensions.getPotentialSavings
import com.raul.androidapps.softwareteststarling.extensions.getValueWithTwoDecimalsPrecisionInStringFormat
import com.raul.androidapps.softwareteststarling.model.Feed
import com.raul.androidapps.softwareteststarling.model.SavingState
import com.raul.androidapps.softwareteststarling.resources.ResourcesManager


class FeedsAdapter constructor(
    private val resourcesManager: ResourcesManager,
    private val starlingBindingComponent: StarlingBindingComponent
) :
    RecyclerView.Adapter<FeedsAdapter.FeedViewHolder>() {

    private var items: List<Feed> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<FeedItemBinding>(
            inflater, R.layout.feed_item, parent,
            false, starlingBindingComponent
        )

        return FeedViewHolder(binding = binding)

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(items[position], resourcesManager)
    }

    fun updateItems(items: List<Feed>) {
        this.items = items
        notifyDataSetChanged()
    }

    class FeedViewHolder constructor(
        private val binding: FeedItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: Feed, resourcesManager: ResourcesManager) {
            binding.feed = feed
            binding.resources = resourcesManager
            feed.amount?.let {
                val value = it.minorUnits.toFloat() / 100
                val textAmount = " ${value.getValueWithTwoDecimalsPrecisionInStringFormat()} ${it.currency}"
                binding.amount = textAmount
                if (feed.availableForSaving == SavingState.AVAILABLE) {
                    binding.savingsText.visibility = View.VISIBLE
                    binding.savingsValue.visibility = View.VISIBLE
                    it.getPotentialSavings()?.let { saving ->
                        val savingValue = saving.minorUnits.toFloat() / 100
                        val textSaving =
                            " ${savingValue.getValueWithTwoDecimalsPrecisionInStringFormat()} ${saving.currency}"
                        binding.saving = textSaving
                    }
                } else {
                    binding.savingsText.visibility = View.GONE
                    binding.savingsValue.visibility = View.GONE
                }
            }
            binding.executePendingBindings()
        }
    }
}