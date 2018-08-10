package com.lunatk.ledmaskapp

import com.lunatk.ledmaskapp.objects.LEDMask
import okhttp3.*
import java.io.IOException

fun postStartAnalysis(ledMask: LEDMask, userId: String, callback: Callback) {
    val client = OkHttpClient()
    val body = RequestBody.create(MediaType.parse("application/json"), "{\n" +
            "  \"ddid\": \"${ledMask.did}\",\n" +
            "  \"sdid\": \"${ledMask.did}\",\n" +
            "  \"type\": \"action\",\n" +
            "  \"data\": {\n" +
            "    \"actions\": [\n" +
            "      {\n" +
            "        \"name\": \"startAnalysis\",\n" +
            "        \"parameters\": {\"user_id\": \"${userId}\"}\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}")
    val request = Request.Builder()
            .url("https://api.artik.cloud/v1.1/actions")
            .addHeader("Authorization", "Bearer ${ledMask.token}")
            .post(body)
            .build()

    client.newCall(request).enqueue(callback)
}

fun postTurnOnLED(ledMask: LEDMask, callback: Callback) {
    val client = OkHttpClient()
    val body = RequestBody.create(MediaType.parse("application/json"), "{\n" +
            "  \"ddid\": \"${ledMask.did}\",\n" +
            "  \"sdid\": \"${ledMask.did}\",\n" +
            "  \"type\": \"action\",\n" +
            "  \"data\": {\n" +
            "    \"actions\": [\n" +
            "      {\n" +
            "        \"name\": \"turnOnLED\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}")
    val request = Request.Builder()
            .url("https://api.artik.cloud/v1.1/actions")
            .addHeader("Authorization", "Bearer ${ledMask.token}")
            .post(body)
            .build()

    client.newCall(request).enqueue(callback)
}

fun postTurnOffLED(ledMask: LEDMask, callback: Callback) {
    val client = OkHttpClient()
    val body = RequestBody.create(MediaType.parse("application/json"), "{\n" +
            "  \"ddid\": \"${ledMask.did}\",\n" +
            "  \"sdid\": \"${ledMask.did}\",\n" +
            "  \"type\": \"action\",\n" +
            "  \"data\": {\n" +
            "    \"actions\": [\n" +
            "      {\n" +
            "        \"name\": \"turnOffLED\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}")
    val request = Request.Builder()
            .url("https://api.artik.cloud/v1.1/actions")
            .addHeader("Authorization", "Bearer ${ledMask.token}")
            .post(body)
            .build()

    client.newCall(request).enqueue(callback)
}
