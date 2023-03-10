package com.thoughtworks.androidtrain.helloworld

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.androidtrain.helloworld.compose.ComposeActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCompose: Button = findViewById(R.id.btn1)
        btnCompose.setOnClickListener {
            startActivity(Intent(this@MainActivity, ComposeActivity::class.java))
        }
    }

}
