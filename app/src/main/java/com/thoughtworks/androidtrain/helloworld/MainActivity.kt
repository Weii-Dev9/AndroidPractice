package com.thoughtworks.androidtrain.helloworld

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

const val REQUEST_SELECT_CONTACT = 1
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnConstraintLayout: Button = findViewById(R.id.btn1)
        btnConstraintLayout.setOnClickListener {
            startActivity(Intent(this@MainActivity, ConstraintActivity::class.java))
        }

        val btnLogin: Button = findViewById(R.id.btn2)
        btnLogin.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        val btnPickContact: Button = findViewById(R.id.btn3)
        btnPickContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, REQUEST_SELECT_CONTACT)
            }
        }

        val btnFragment: Button = findViewById(R.id.btn4)
        btnFragment.setOnClickListener {
            startActivity(Intent(this@MainActivity, MyFragmentActivity::class.java))
        }

        val btnTweets: Button = findViewById(R.id.btn5)
        btnTweets.setOnClickListener {
            startActivity(Intent(this@MainActivity, TweetsActivity::class.java))
        }

        val btnThread: Button = findViewById(R.id.btn6)
        btnThread.setOnClickListener {
            startActivity(Intent(this@MainActivity, ThreadActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            val contactUri: Uri = data?.data!!
            val projection: Array<String> = arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )
            contentResolver.query(contactUri, projection, null, null, null).use { cursor ->
                // If the cursor returned is valid, get the phone number
                if (cursor?.moveToFirst() == true) {
                    val columnIndex =
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    val name = cursor.getString(columnIndex)
                    val numberIndex =
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val number = cursor.getString(numberIndex)
                    // Do something with the phone number
                    Toast.makeText(this, "name: $name \nmobile phone: $number", Toast.LENGTH_LONG)
                        .show();
                    //dialog
                    //PurchaseConfirmationDialogFragment().show(
                    //childFragmentManager, "name: $name \n mobile phone: $number")
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }
}