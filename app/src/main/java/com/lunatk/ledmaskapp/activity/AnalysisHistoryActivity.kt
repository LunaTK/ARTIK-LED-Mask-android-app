package com.lunatk.ledmaskapp.activity

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lunatk.ledmaskapp.objects.AnalysisHistory
import com.lunatk.ledmaskapp.adapter.AnalysisHistoryAdapter
import com.lunatk.ledmaskapp.R
import com.lunatk.ledmaskapp.databinding.ActivityAnalysisHistoryBinding
import com.lunatk.ledmaskapp.extension.*

class AnalysisHistoryActivity: AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<ActivityAnalysisHistoryBinding>(this, R.layout.activity_analysis_history) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
        val historyList = ObservableArrayList<AnalysisHistory>()

        analysisHistories.get().addOnCompleteListener {
            if (it.isSuccessful) it.result.forEach { historyList.add(AnalysisHistory(it)); logi("new history : ${it.id}")  }
        }
        binding.analysisHistoryList = historyList
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            adapter = AnalysisHistoryAdapter().apply { onItemClickListener = ::showHistoryDetailWithTransition }
            layoutManager = GridLayoutManager(this@AnalysisHistoryActivity, 2, GridLayoutManager.VERTICAL, false)
            addItemDecoration(object: RecyclerView.ItemDecoration(){
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.set(25,25,25,25)
                }
            })
        }
    }

    private fun showHistoryDetailWithTransition(index: Int) {
        logi("showHistoryDetailWithTransition($index)")
        val intent = Intent(this, ImageDetailActivity::class.java)
        val viewHolder = binding.recyclerView.findViewHolderForAdapterPosition(index) as AnalysisHistoryAdapter.HistoryViewHolder
        val imageViewRef = androidx.core.util.Pair<View,String>(viewHolder.binding.ivPhoto, "analysisImage")

        intent.putExtra("document_id", binding.analysisHistoryList!![index].document.id)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageViewRef)
        startActivity(intent, options.toBundle())
    }
}