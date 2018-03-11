package com.example.noey.findmyphone

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by noey on 10/3/2018 AD.
 */
class Login: AppCompatActivity(){

    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()

        firebaseAuth = FirebaseAuth.getInstance()
        signinAnonymous()
    }

    private fun initView() {
        btn_register.setOnClickListener {
            val usersData = UsersData(this)
            usersData.savePhoneNumber(edt_phone_number.text.toString())

            finish()
        }
    }

    fun signinAnonymous(){
        firebaseAuth!!.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(applicationContext, "Authentication success.",
                                Toast.LENGTH_SHORT).show()
                        val user = firebaseAuth!!.getCurrentUser()

                    } else {
                        Toast.makeText(applicationContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                    }
                }
    }

}