package com.example.noey.findmyphone

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tracker.*
import kotlinx.android.synthetic.main.contact_ticket.view.*
import kotlin.system.measureNanoTime

/**
 * Created by noey on 11/3/2018 AD.
 */
class Tracker : AppCompatActivity(){

    val REQUEST_CODE_CONTACT = 111;
    val REQUEST_CODE_PICK_CONTACT = 222;

    var listOfContact = ArrayList<UsersContact>()
    var adapter:ContactAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracker)

//        dummyData()
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
                checkPermission()
            }

            else ->{
                return super.onOptionsItemSelected(item)

            }
        }
        return true
    }

    fun checkPermission(){
        if (Build.VERSION.SDK_INT >= 23){
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS), REQUEST_CODE_CONTACT)
                return
            }

        }

        pickContact()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode){
            REQUEST_CODE_CONTACT ->{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickContact()
                } else{
                    Toast.makeText(this, "Cannot access to contact ", Toast.LENGTH_LONG)
                            .show()
                }
            }

            else ->{
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    private fun pickContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_CONTACT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            REQUEST_CODE_PICK_CONTACT ->{
                if(resultCode == Activity.RESULT_OK){
                    val contactData = data!!.data
                    val content = contentResolver.query(contactData, null, null, null, null)

                    if(content.moveToFirst()){
                        val id = content.getString(content.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                        val hasPhoneNumner = content.getString(content.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                        if (hasPhoneNumner.equals("1")){
                            val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" +id,
                                    null,
                                    null)

                            phones.moveToFirst()
                            val phoneNumber = phones.getString(phones.getColumnIndex("data1"))
                            val name = phones.getString(content.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                            listOfContact.add(UsersContact(name, phoneNumber))
                            adapter!!.notifyDataSetChanged()

                        }
                    }
                }
            }

            else ->{
                super.onActivityResult(requestCode, resultCode, data)

            }
        }
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