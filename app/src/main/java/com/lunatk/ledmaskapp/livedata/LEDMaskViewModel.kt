package com.lunatk.ledmaskapp.livedata

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.lunatk.ledmaskapp.extension.currentUser
import com.lunatk.ledmaskapp.extension.db
import com.lunatk.ledmaskapp.extension.userDocument
import com.lunatk.ledmaskapp.objects.LEDMask

class LEDMaskViewModel: ViewModel() {
    private var _maskList: MutableLiveData<ObservableArrayList<LEDMask>> = MutableLiveData()
    var maskList: LiveData<ObservableArrayList<LEDMask>> = _maskList

    var maskData: DocumentSnapshot? = null
    private set

    fun fetchMaskList() {
        userDocument.get().addOnCompleteListener {
            maskData = it.result
            val mask_ids = it.result["masks"] as ArrayList<DocumentReference>
            val mask_nicknames = it.result["mask_nicknames"] as ArrayList<String>
            val maskList = ObservableArrayList<LEDMask>()
            for (i in mask_ids.indices) {
                maskList.add(LEDMask(mask_nicknames[i], mask_ids[i].id))
            }
            _maskList.postValue(maskList)
        }
    }
}