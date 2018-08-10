package com.lunatk.ledmaskapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.lunatk.ledmaskapp.R

class LEDPositionAdapter: RecyclerView.Adapter<LEDPositionAdapter.ViewHolder>() {

    var positionString = "0000000000000000000000000"

    fun setLEDPosition(positionString: String?) {
        this.positionString = positionString ?: "0000000000000000000000000"
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val imageView = LayoutInflater.from(parent.context).inflate(R.layout.item_led_position, parent, false)
        return ViewHolder(imageView = imageView)
    }

    override fun getItemCount(): Int {
        return positionString.length
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as ImageView).setImageResource(if (positionString.elementAt(position)=='0') R.drawable.led_off else R.drawable.led_on)
    }

    class ViewHolder(imageView: View): RecyclerView.ViewHolder(imageView) {

    }
}