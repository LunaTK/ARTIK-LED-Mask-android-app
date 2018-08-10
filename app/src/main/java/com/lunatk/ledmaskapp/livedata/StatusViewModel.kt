package com.lunatk.ledmaskapp.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonParser
import com.lunatk.ledmaskapp.extension.loge
import com.lunatk.ledmaskapp.extension.logi
import okhttp3.*
import java.io.IOException

class StatusViewModel: ViewModel() {
    private var _connectivity: MutableLiveData<Boolean> = MutableLiveData()
    var connectivity: LiveData<Boolean> = _connectivity

    private var _ledStatus: MutableLiveData<Boolean> = MutableLiveData()
    var ledStatus: LiveData<Boolean> = _ledStatus

    private var _ledPosition: MutableLiveData<String> = MutableLiveData()
    var ledPosition: LiveData<String> = _ledPosition

    fun fetchStatus() {
        val client = OkHttpClient()

        val request = Request.Builder()
                .url("https://api.artik.cloud/v1.1/devices/ad14044bb436423d8dad442a449e119c/status")
                .addHeader("Authorization", "Bearer ea50d3f5ee8a4d6680aff7cfd62253a7")
                .get()
                .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body()!!.string()
                val parser = JsonParser()
                val element = parser.parse(jsonString)

                val connectivity = element?.asJsonObject?.get("data")?.asJsonObject?.get("availability")?.asJsonPrimitive?.asString == "online"
                _connectivity.postValue(connectivity)

                val ledStatus = element?.asJsonObject?.get("data")?.asJsonObject?.get("snapshot")?.asJsonObject?.get("ledState")?.asJsonObject?.get("value")?.asJsonPrimitive?.asBoolean
                _ledStatus.postValue(ledStatus)

                val ledPosition = element?.asJsonObject?.get("data")?.asJsonObject?.get("snapshot")?.asJsonObject?.get("ledPosition")?.asJsonObject?.get("value")?.asJsonPrimitive?.asString
                _ledPosition.postValue(ledPosition)
            }

        })
    }

}