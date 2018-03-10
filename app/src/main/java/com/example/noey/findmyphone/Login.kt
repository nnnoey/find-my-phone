package com.example.noey.findmyphone

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by noey on 10/3/2018 AD.
 */
class Login: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        btn_register.setOnClickListener {
            val usersData = UsersData(this)
            usersData.savePhoneNumber(edt_phone_number.text.toString())

            finish()
        }
    }

}