package com.lunatk.ledmaskapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.lunatk.ledmaskapp.databinding.ItemLedMaskBinding
import com.lunatk.ledmaskapp.objects.LEDMask

class LEDMaskAdapter: RecyclerView.Adapter<LEDMaskAdapter.ViewHolder>() {

    var onItemClickListener: ((View) -> Unit)? = null

    private var _masks: ObservableArrayList<LEDMask>? = null
    var masks: ObservableArrayList<LEDMask>?
    get() = _masks
    set(value) {
        if(_masks!=value) _masks = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLedMaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = _masks?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.ledMask = _masks!!.get(position)
        holder.itemView.setOnClickListener { onItemClickListener?.invoke(it) }
    }

    data class ViewHolder(val binding: ItemLedMaskBinding): RecyclerView.ViewHolder(binding.root)
}