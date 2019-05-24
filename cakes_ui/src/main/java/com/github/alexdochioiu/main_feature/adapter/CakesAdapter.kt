package com.github.alexdochioiu.main_feature.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.alexdochioiu.core.di.Feature_UiScope
import com.github.alexdochioiu.main_feature.R
import com.github.alexdochioiu.main_feature_common_objects.Cake
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cake.view.*
import javax.inject.Inject

@Feature_UiScope
class CakesAdapter @Inject constructor(private val picasso: Picasso, private val listener: CakesListener)
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
            picasso.load(cake.imageUrl).fit().centerCrop().into(view.item_cake_ivCake)
        }

        override fun onClick(v: View?) {
            listener.onCakeSelected(cakes[adapterPosition])
        }
    }

    interface CakesListener {
        fun onCakeSelected(cake: Cake)
    }
}