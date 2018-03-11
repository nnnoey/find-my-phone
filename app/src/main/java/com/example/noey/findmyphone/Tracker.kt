package com.example.noey.findmyphone

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_tracker.*
import kotlinx.android.synthetic.main.contact_ticket.view.*
import kotlin.system.measureNanoTime

/**
 * Created by noey on 11/3/2018 AD.
 */
class Tracker : AppCompatActivity(){

    var listOfContact = ArrayList<UsersContact>()
    var adapter:ContactAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracker)

        dummyData()
        adapter = ContactAdapter(listOfContact, this)
        listView_contactList.adapter = adapter
    }

    fun dummyData(){
        listOfContact.add(UsersContact("noey", "1234"))
        listOfContact.add(UsersContact("noey1", "12345"))
        listOfContact.add(UsersContact("noey2", "123456"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.tracker_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.finishActivity ->{
                finish()
            }

            R.id.addContact ->{

            }

            else ->{
                return super.onOptionsItemSelected(item)

            }
        }
        return true
    }

    class ContactAdapter:BaseAdapter {

        var listOfContact = ArrayList<UsersContact>()
        var context: Context? = null

        constructor(listOfContact: ArrayList<UsersContact>, context: Context?) : super() {
            this.listOfContact = listOfContact
            this.context = context
        }


        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val usersContact = listOfContact[p0]
            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val contactTicketView = inflater.inflate(R.layout.contact_ticket, null)
            contactTicketView.tvName.text = usersContact.name
            contactTicketView.tvPhoneNumber.text = usersContact.phoneNumber

            return contactTicketView
        }

        override fun getItem(p0: Int): Any {
            return listOfContact[p0]
        }

        override fun getItemId(p0: Int): Long {
            return  p0.toLong()
        }

        override fun getCount(): Int {
            return  listOfContact.size
        }

    }
}