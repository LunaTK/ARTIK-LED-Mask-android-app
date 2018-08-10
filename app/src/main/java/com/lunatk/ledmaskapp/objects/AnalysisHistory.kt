package com.lunatk.ledmaskapp.objects

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.storage.StorageReference
import com.lunatk.ledmaskapp.extension.currentUser
import com.lunatk.ledmaskapp.extension.storageRef
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

data class AnalysisHistory(val document: DocumentSnapshot) {
    val simpleDate: String
    get() = SimpleDateFormat("yyyy-MM-dd").format(document.get("date") as Date)

    val storageReference: StorageReference
    get() = storageRef.child(currentUser!!.uid).child(document.get("image") as String)

}