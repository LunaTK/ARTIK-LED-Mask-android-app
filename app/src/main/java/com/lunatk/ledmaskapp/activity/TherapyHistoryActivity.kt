package com.lunatk.ledmaskapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import com.lunatk.ledmaskapp.R
import com.lunatk.ledmaskapp.adapter.TherapyHistoryAdapter
import com.lunatk.ledmaskapp.databinding.ActivityTherapyHistoryBinding
import com.lunatk.ledmaskapp.extension.userDocument
import com.lunatk.ledmaskapp.objects.TherapyHistory

class TherapyHistoryActivity : AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<ActivityTherapyHistoryBinding>(this, R.layout.activity_therapy_history) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.therapyHistoryList = ObservableArrayList()
        setupRecyclerView()
        fetchTherapyHistories()
    }

    private fun fetchTherapyHistories() {
        userDocument.collection("therapy_histories").get().addOnCompleteListener {
            val therapyHistories = ObservableArrayList<TherapyHistory>()
            if (it.isSuccessful) {
                for (document in it.result) {
                    therapyHistories.add(TherapyHistory(document))
                }
            }
            binding.therapyHistoryList = therapyHistories
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvTherapyHistory){
            adapter = TherapyHistoryAdapter()
            layoutManager = LinearLayoutManager(this@TherapyHistoryActivity)
        }
    }
}
