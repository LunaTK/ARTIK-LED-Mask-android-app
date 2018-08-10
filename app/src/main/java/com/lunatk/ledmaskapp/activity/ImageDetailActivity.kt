package com.lunatk.ledmaskapp.activity

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.lunatk.ledmaskapp.objects.AnalysisHistory
import com.lunatk.ledmaskapp.R
import com.lunatk.ledmaskapp.databinding.ActivityImageDetailBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.lunatk.ledmaskapp.adapter.LEDPositionAdapter
import com.lunatk.ledmaskapp.extension.analysisHistories
import kotlinx.android.synthetic.main.activity_control.*


class ImageDetailActivity : AppCompatActivity() {

    private var ledPosition: String = "0000000000000000000000000"
    private lateinit var binding:ActivityImageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityImageDetailBinding>(this, R.layout.activity_image_detail)
        val documentId = intent.getStringExtra("document_id")
        analysisHistories.document(documentId).get().addOnCompleteListener { onDocumentLoaded(it) }
//        postponeEnterTransition()

    }

    private fun setupRecyclerView() {
        rv_led.layoutManager = GridLayoutManager(this, 5)
        rv_led.adapter = LEDPositionAdapter().apply{ setLEDPosition(ledPosition) }
        rv_led.addItemDecoration(object: RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.set(25,25,25,25)
            }
        })
    }

    private fun onDocumentLoaded(documentSnapshot: Task<DocumentSnapshot>) {
        binding.analysisHistory = AnalysisHistory(documentSnapshot.result)
//        ledPosition = documentSnapshot.result["ledPosition"] as String
        setupRecyclerView()
    }


}