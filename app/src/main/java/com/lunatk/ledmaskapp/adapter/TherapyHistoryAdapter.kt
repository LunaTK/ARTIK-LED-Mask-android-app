package com.lunatk.ledmaskapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.lunatk.ledmaskapp.databinding.ItemTherapyHistoryBinding
import com.lunatk.ledmaskapp.objects.TherapyHistory

class TherapyHistoryAdapter: RecyclerView.Adapter<TherapyHistoryAdapter.ViewHolder>() {

    var therapyHistories: ObservableArrayList<TherapyHistory>? = ObservableArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTherapyHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return therapyHistories?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.therapyHistory = therapyHistories!![position]
    }

    fun setTherapyHistoryList(therapyHistoryList: ObservableArrayList<TherapyHistory>) {
        therapyHistories = therapyHistoryList
        notifyDataSetChanged()
    }

    data class ViewHolder(val binding: ItemTherapyHistoryBinding): RecyclerView.ViewHolder(binding.root);
}