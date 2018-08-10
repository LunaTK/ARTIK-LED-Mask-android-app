package com.lunatk.ledmaskapp.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lunatk.ledmaskapp.*
import com.lunatk.ledmaskapp.adapter.LEDPositionAdapter
import com.lunatk.ledmaskapp.extension.currentUser
import com.lunatk.ledmaskapp.livedata.StatusViewModel
import com.lunatk.ledmaskapp.objects.LEDMask
import kotlinx.android.synthetic.main.activity_control.*
import okhttp3.*
import java.io.IOException

class ControlActivity : AppCompatActivity() {

    private val statusViewModel: StatusViewModel by lazy { ViewModelProviders.of(this).get(StatusViewModel::class.java) }
    private val ledPositionAdapter = LEDPositionAdapter()
    private lateinit var ledMask: LEDMask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        ledMask = intent.getSerializableExtra("ledMask") as? LEDMask ?: LEDMask("Mask Not Found", "")

        setObservers()
        updateStatus()

        setupRecyclerView()
    }

    fun startHistoryActivity(v: View) {
        startActivity(Intent(this, AnalysisHistoryActivity::class.java))
    }

    fun startAnalysis(v: View) {

        postStartAnalysis(ledMask, currentUser!!.uid, object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {
            }

            override fun onResponse(call: Call?, response: Response?) {
            }
        })
    }

    fun startOperating(v: View) {
        postTurnOnLED(ledMask, object: Callback{
            override fun onFailure(call: Call?, e: IOException?) {
            }

            override fun onResponse(call: Call?, response: Response?) {
            }

        })
    }

    fun stopOperating(v: View) {
        postTurnOffLED(ledMask, object: Callback{
            override fun onFailure(call: Call?, e: IOException?) {
            }

            override fun onResponse(call: Call?, response: Response?) {
            }

        })
    }

    fun startTherapyHistoryActivity(v: View) {
        startActivity(Intent(this, TherapyHistoryActivity::class.java))
    }


    private fun setupRecyclerView() {
        rv_led.layoutManager = GridLayoutManager(this, 5)
        rv_led.adapter = ledPositionAdapter
        rv_led.addItemDecoration(object: RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.set(25,25,25,25)
            }
        })
    }


    private fun setObservers() {
        Observer<Boolean>() { connected ->
            tv_connectivity.text = if (connected ?: false) "Connected" else "Disconnected"
            tv_connectivity.setTextColor( if (connected ?: false) Color.GREEN else Color.RED )

            iv_connectivity.setImageResource( if (connected ?: false) R.drawable.online else R.drawable.offline )
        }.apply { statusViewModel.connectivity.observe(this@ControlActivity, this) }

        Observer<String> { ledPosition ->
            ledPositionAdapter.setLEDPosition(ledPosition)
        }.apply { statusViewModel.ledPosition.observe(this@ControlActivity, this) }
    }

    private fun updateStatus() {
        statusViewModel.fetchStatus()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.refresh_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.btn_refresh) {
            updateStatus()
        }
        return true
    }
}
