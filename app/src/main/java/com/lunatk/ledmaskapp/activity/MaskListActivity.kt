package com.lunatk.ledmaskapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.integration.android.IntentIntegrator
import com.lunatk.ledmaskapp.R
import com.lunatk.ledmaskapp.adapter.LEDMaskAdapter
import com.lunatk.ledmaskapp.databinding.ActivityMaskListBinding
import com.lunatk.ledmaskapp.livedata.LEDMaskViewModel
import com.lunatk.ledmaskapp.objects.LEDMask
import android.widget.Toast
import com.google.zxing.integration.android.IntentResult
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.lunatk.ledmaskapp.extension.*
import kotlinx.android.synthetic.main.dialog_new_mask.view.*
import java.util.*
import kotlin.collections.ArrayList


class MaskListActivity : AppCompatActivity() {

    private val binding: ActivityMaskListBinding by lazy {DataBindingUtil.setContentView<ActivityMaskListBinding>(this,R.layout.activity_mask_list) }
    private val ledMaskViewModel by lazy { ViewModelProviders.of(this).get(LEDMaskViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.maskList = ObservableArrayList()

        setupRecyclerView()

        setObservers()
        updateMaskList()
    }

    private fun setupRecyclerView() {
        binding.rvMaskList.adapter = LEDMaskAdapter().apply {
            onItemClickListener =  {
                val mask = masks!![binding.rvMaskList.getChildAdapterPosition(it)]
                fetchMaskDocumentAndStartControlActivity(mask.id, mask.nickname)
            }
        }
        binding.rvMaskList.layoutManager = LinearLayoutManager(this)

    }

    private fun setObservers() {
        ledMaskViewModel.maskList.observe(this, Observer<ObservableArrayList<LEDMask>>() {
            it?.let {
                binding.maskList = it!!
            }
        })
    }

    private fun updateMaskList() {
        ledMaskViewModel.fetchMaskList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                showNewMaskDialog(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun addByQRCode(v: View) {
        val integrator = IntentIntegrator(this)
        integrator.setBeepEnabled(false);
        integrator.setPrompt("마스크의 QR 코드를 인식해주세요");
        integrator.setOrientationLocked(true)
        integrator.initiateScan()
    }

    fun addByManual(v: View) {
        showNewMaskDialog()
    }

    private fun showNewMaskDialog(mask_uid: String? = null) {
        val view = layoutInflater.inflate(R.layout.dialog_new_mask, null)
        mask_uid?.let {
            view.tv_mask_uid.apply {
                setText(mask_uid)
                isEnabled = false
            }
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("New Mask")
        builder.setView(view)
        builder.setPositiveButton("추가", { dialog, which -> addNewMaskToUser(view.tv_mask_nickname.text.toString(), view.tv_mask_uid.text.toString())})
        builder.setNegativeButton("취소", { dialog, which -> })
        builder.show()
    }

    private fun fetchMaskDocumentAndStartControlActivity(mask_uid: String, mask_nickname: String) {
        db.collection("masks").document(mask_uid).get().addOnCompleteListener {
            with(it.result) {
                val ledMask = LEDMask(mask_uid, mask_nickname, get("artik_did") as String, get("artik_token") as String)
                val intent = Intent(this@MaskListActivity, ControlActivity::class.java)
                intent.putExtra("ledMask", ledMask)
                startActivity(intent)
            }
        }
    }

    private fun addNewMaskToUser(nickname: String, id: String) {

        val currentMaskList: ArrayList<DocumentReference> = ledMaskViewModel.maskData!!.get("masks") as? ArrayList<DocumentReference> ?: ArrayList()
        currentMaskList.add(db.collection("masks").document(id))

        val currentMaskNicknameList: ArrayList<String> = ledMaskViewModel.maskData!!.get("mask_nicknames") as? ArrayList<String> ?: ArrayList()
        currentMaskNicknameList.add(nickname)

        val newData = HashMap<String, Any>()
        newData.put("masks",currentMaskList)
        newData.put("mask_nicknames", currentMaskNicknameList)

        userDocument.update(newData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Mask Added", Toast.LENGTH_SHORT).show();
                    ledMaskViewModel.fetchMaskList()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed! : ${it.message}", Toast.LENGTH_SHORT).show();
                }

        db.collection("masks").document(id).get().addOnSuccessListener {
            val currentUserList: ArrayList<DocumentReference> = it.get("users") as? ArrayList<DocumentReference> ?: ArrayList()
            currentUserList.add(userDocument)

            val currentUserNameList: ArrayList<String> = it.get("user_names") as? ArrayList<String> ?: ArrayList()
            currentUserNameList.add(currentUser!!.displayName!!)

            val newData = HashMap<String, Any>()
            newData.put("users",currentUserList!!)
            newData.put("user_names", currentUserNameList!!)
            db.collection("masks").document(id).update(newData)
        }
    }

}
