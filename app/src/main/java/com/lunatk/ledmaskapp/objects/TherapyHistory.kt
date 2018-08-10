package com.lunatk.ledmaskapp.objects

import com.google.firebase.firestore.DocumentSnapshot
import java.text.SimpleDateFormat
import java.util.*

data class TherapyHistory(val document: DocumentSnapshot){
    val simpleDate: String
        get() = SimpleDateFormat("yyyy-MM-dd hh:MM").format(document.get("date") as Date)
}