package com.thoughtworks.androidtrain.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ThreadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thread_layout)

        val btn: Button = findViewById(R.id.thread)
        var num: Int = 0
        btn.text = num.toString()
        btn.setOnClickListener {
            btn.isEnabled = false
             val th = Thread {
                while (num < 10) {
                    Thread.sleep(1000)
                    num += 1
                    btn.text = num.toString()
                }
                runOnUiThread {
                    if (num == 10) {
                        Thread.sleep(1000)
                        btn.isEnabled = true
                        num = 0
                        btn.text = "0"
                    }
                }
            }
            th.start()

        }//如何判断终止？


    }
}