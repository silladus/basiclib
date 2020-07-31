package com.example.mvvm_hilt.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.example.mvvm_hilt.GlideApp
import com.example.mvvm_hilt.databinding.ItemPokemonBinding
import com.example.mvvm_hilt.entity.Pokemon

/**
 * create by silladus 2020/7/23
 * github:https://github.com/silladus
 * des:
 */
class PokeItemViewBinder(val onItemClickListener: (View, Pokemon) -> Unit) : ItemViewBinder<Pokemon, PokeItemViewBinder.ViewHolder>() {
    class ViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, item: Pokemon) {
        holder.binding.apply {
            tvName.text = item.name
            GlideApp.with(ivIdle.context)
                    .asDrawable()
                    .load(item.getImageUrl())
                    .into(ivIdle)

            root.apply {
                transitionName = "anim"
                setOnClickListener {
                    onItemClickListener(it, item)
                }
            }
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(ItemPokemonBinding.inflate(inflater, parent, false))
    }
}