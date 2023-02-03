package com.thoughtworks.androidtrain.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.thoughtworks.androidtrain.helloworld.fragment.AndroidFragment
import com.thoughtworks.androidtrain.helloworld.fragment.JavaFragment
import com.thoughtworks.androidtrain.helloworld.utils.UIUtils

class MyFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_fragment_layout)
        initUI()
    }

    private fun initUI() {
        val btnAn: Button = findViewById(R.id.btnAndroid)
        btnAn.setOnClickListener {
            UIUtils.replaceFragment(supportFragmentManager, AndroidFragment(), R.id.content, null)
        }

        val btnJa: Button = findViewById(R.id.btnJava)
        btnJa.setOnClickListener {
            UIUtils.replaceFragment(supportFragmentManager, JavaFragment(), R.id.content, null)
        }
    }
}