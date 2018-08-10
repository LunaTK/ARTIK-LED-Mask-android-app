package com.lunatk.ledmaskapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.app.Activity
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.lunatk.ledmaskapp.R
import com.lunatk.ledmaskapp.extension.currentUser

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
//          val response = IdpResponse.fromResultIntent(data)
            startMaskListActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        startFirebaseLogin(null)

        if (currentUser?.email != null) startMaskListActivity()

    }

    private fun startMaskListActivity(){
        startActivity(Intent(this, MaskListActivity::class.java))
        finish()
    }

    fun startFirebaseLogin(v: View?){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(listOf(AuthUI.IdpConfig.EmailBuilder().build()))
                        .build(),
                0)
    }

}
