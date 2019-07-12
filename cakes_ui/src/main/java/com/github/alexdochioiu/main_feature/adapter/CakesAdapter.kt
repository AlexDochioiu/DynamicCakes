/*
 * Copyright 2019 Alexandru Iustin Dochioiu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.alexdochioiu.main_feature.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.github.alexdochioiu.core.di.Feature_UiScope
import com.github.alexdochioiu.main_feature.R
import com.github.alexdochioiu.main_feature_common_objects.Cake
import kotlinx.android.synthetic.main.item_cake.view.*
import javax.inject.Inject

@Feature_UiScope
class CakesAdapter @Inject internal constructor(private val listener: CakesListener)
    : RecyclerView.Adapter<CakesAdapter.CakesHolder>() {

    private var cakes: List<Cake> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakesHolder =
        CakesHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cake, parent, false)
        )

    override fun getItemCount(): Int = cakes.size

    override fun onBindViewHolder(holder: CakesHolder, position: Int) {
        holder.bind(cakes[position])
    }

    fun replaceItemsAndNotify(newCakes: List<Cake>) {
        cakes = newCakes
        notifyDataSetChanged()
    }

    fun clearItemsAndNotify() {
        cakes = emptyList()
        notifyDataSetChanged()
    }

    inner class CakesHolder constructor(private val view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        fun bind(cake: Cake) {
            view.setOnClickListener(this)

            view.item_cake_tvTitle.text = cake.title

            Glide.with(view).load(cake.imageUrl)
                .centerCrop()
                .placeholder(CircularProgressDrawable(view.context))
                .into(view.item_cake_ivCake)
        }

        override fun onClick(v: View?) {
            listener.onCakeSelected(cakes[adapterPosition])
        }
    }

    interface CakesListener {
        fun onCakeSelected(cake: Cake)
    }
}