package com.lunatk.ledmaskapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.lunatk.ledmaskapp.databinding.ItemAnalysisHistoryBinding
import com.lunatk.ledmaskapp.databinding.ItemTherapyHistoryBinding
import com.lunatk.ledmaskapp.extension.analysisHistories
import com.lunatk.ledmaskapp.objects.AnalysisHistory

class AnalysisHistoryAdapter(): RecyclerView.Adapter<AnalysisHistoryAdapter.HistoryViewHolder>() {

    private var _historyList: ObservableArrayList<AnalysisHistory>? = null
    var historyList: ObservableArrayList<AnalysisHistory>?
        get() = _historyList
        set(newValue) {
            if(_historyList!=newValue) _historyList = newValue
            notifyDataSetChanged()
        }

    var onItemClickListener: ((Int)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemAnalysisHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _historyList?.size ?: 0
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        with(holder.binding){
            analysisHistory = _historyList!![position]
            holder.itemView.setOnClickListener { onItemClickListener?.invoke(position) }
        }
    }

    data class HistoryViewHolder(val binding: ItemAnalysisHistoryBinding): RecyclerView.ViewHolder(binding.root);
}