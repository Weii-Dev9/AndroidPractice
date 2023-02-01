package com.thoughtworks.androidtrain.helloworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
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
    }
}