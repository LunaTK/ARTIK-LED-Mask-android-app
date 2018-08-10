package com.lunatk.ledmaskapp.extension

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

val Any.currentUser: FirebaseUser?
    get() = FirebaseAuth.getInstance().currentUser

val Any.storageRef: StorageReference
    get() = FirebaseStorage.getInstance().reference

val Any.db: FirebaseFirestore
    get() = FirebaseFirestore.getInstance()

val Any.analysisHistories: CollectionReference
    get() = userDocument.collection("analysis_histories")

val Any.userDocument: DocumentReference
    get() = db.collection("users").document(currentUser!!.uid)