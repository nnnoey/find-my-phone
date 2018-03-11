package com.example.noey.findmyphone

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by noey on 10/3/2018 AD.
 */
class UsersData{

    var context:Context?=null
    var sharePref:SharedPreferences?=null

    constructor(context: Context?) {
        this.context = context
        this.sharePref = context!!.getSharedPreferences("usersData", Context.MODE_PRIVATE)
    }

    fun savePhoneNumber(phoneNumber:String){
        val editor = sharePref!!.edit()
        editor.putString("phoneNumber", phoneNumber)
        editor.commit()
    }

    fun loadPhoneNumber():String{
        val phoneNumber = sharePref!!.getString("phoneNumber", "empty")
        if (phoneNumber.equals("empty")){
            val initent = Intent(context, Login::class.java)
            initent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context!!.startActivity(initent)
        }

        return phoneNumber
    }

    fun saveContactInfo(){
        var listOfTrackers=""
        for ((key,value) in tracker){

            if (listOfTrackers.length ==0 ){
                listOfTrackers = key + "%" + value
            }else{
                listOfTrackers += "%"+ key + "%" + value
            }
        }

        if (listOfTrackers.length ==0 ){
            listOfTrackers ="empty"
        }


        val editor = sharePref!!.edit()
        editor.putString("listOfTrackers",listOfTrackers)
        editor.commit()
    }

    fun loadContactInfo(){
        tracker.clear()
        val listOfTrackers = sharePref!!.getString("listOfTrackers","empty")

        if (!listOfTrackers.equals("empty")){
            val usersInfo=listOfTrackers.split("%").toTypedArray()
            var i=0
            while(i<usersInfo.size){

                tracker.put(usersInfo[i],usersInfo[i+1])
                i += 2
            }
        }
    }

    companion object {
        var tracker: MutableMap<String, String> = HashMap()
        fun formatPhoneNumber(phoneNumber:String):String {
            var onlyNumber= phoneNumber.replace("[^0-9]".toRegex(),"")
            if (phoneNumber[0] == '+') {
                onlyNumber ="+"+ phoneNumber
            }

            return  onlyNumber
        }

    }
}