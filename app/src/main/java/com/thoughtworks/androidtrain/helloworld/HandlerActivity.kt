package com.thoughtworks.androidtrain.helloworld

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HandlerActivity : AppCompatActivity() {
    var mainHandler: Handler = Handler()
    private var workHandler: Handler = Handler()
    val handlerThread: HandlerThread = HandlerThread("handlerThread")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.handler_layout)

        //启动线程
        handlerThread.start()
        //创建工作线程Handler & 复写handleMessage（）
        //作用：关联HandlerThread的Looper对象、实现消息处理操作 & 与其他线程进行通信
        workHandler = object : Handler(handlerThread.looper) {
            lateinit var toast: Toast
            val duration = Toast.LENGTH_LONG
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                //设置了两种消息处理操作,通过msg来进行识别
                when (msg.what) {
                    1 -> {
                        mainHandler.post {
                            runOnUiThread {
                                val text = "Message 1"
                                toast = Toast.makeText(applicationContext, text, duration)
                                toast.show()
                            }
                        }
                    }
                    2 -> {
                        mainHandler.post {
                            runOnUiThread {
                                val text = "Message 2"
                                toast = Toast.makeText(applicationContext, text, duration)
                                toast.show()
                            }
                        }
                    }
                }
            }
        }
        //使用工作线程Handler向工作线程的消息队列发送消息
        //在工作线程中，当消息循环时取出对应消息 & 在工作线程执行相关操作
        sendMessage(R.id.Msg1, 1, "A")
        sendMessage(R.id.Msg2, 2, "B")
    }

    private fun sendMessage(btnId: Int, msgWhat: Int, msgObj: String) {
        val btn: Button = findViewById(btnId)
        btn.setOnClickListener {
            val msg = Message.obtain()
            msg.what = msgWhat
            msg.obj = msgObj
            workHandler.sendMessage(msg)
        }
    }
}
