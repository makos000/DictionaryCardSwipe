package com.example.dictionarycardswipe.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionarycardswipe.R
import com.example.dictionarycardswipe.models.Lf
import kotlinx.android.synthetic.main.recycler_layout.view.*

class AcronymAdapter(var items: MutableList<Lf>, val context: Context): RecyclerView.Adapter<AcronymAdapter.RecyclerViewHolder>() {
    class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val curItem = items[position]
        holder.itemView.apply {
            freqV.text = curItem.freq.toString()
            definitionV.text = curItem.lf
            sinceV.text = curItem.since.toString()
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}