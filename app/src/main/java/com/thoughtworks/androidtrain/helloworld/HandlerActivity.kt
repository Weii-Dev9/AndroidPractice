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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.handler_layout)
        val handlerThread: HandlerThread = HandlerThread("handlerThread")

        //启动线程
        handlerThread.start()
        //创建工作线程Handler & 复写handleMessage（）
        //作用：关联HandlerThread的Looper对象、实现消息处理操作 & 与其他线程进行通信
        workHandler = object : Handler(handlerThread.looper) {
            lateinit var toast : Toast
            val duration = Toast.LENGTH_LONG
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                //设置了两种消息处理操作,通过msg来进行识别
                when (msg.what) {
                    1 -> {
                        mainHandler.post(Runnable() {
                            runOnUiThread {
                                val text = "Message 1"
                                toast = Toast.makeText(applicationContext, text, duration)
                                toast.show()
                            }
                        })
                    }
                    2 -> {
                        mainHandler.post(Runnable() {
                            runOnUiThread {
                                val text = "Message 2"
                                toast = Toast.makeText(applicationContext, text, duration)
                                toast.show()
                            }
                        })
                    }
                }
            }
        }

        //使用工作线程Handler向工作线程的消息队列发送消息
        //在工作线程中，当消息循环时取出对应消息 & 在工作线程执行相关操作
        val btnM1: Button = findViewById(R.id.Msg1)
        btnM1.setOnClickListener{
            val msg = Message.obtain()
            msg.what = 1 //消息的标识
            msg.obj = "A" //// 消息的存放
            // 通过Handler发送消息到其绑定的消息队列
            workHandler.sendMessage(msg);
        }

        val btnM2: Button = findViewById(R.id.Msg2)
        btnM2.setOnClickListener{
            val msg = Message.obtain()
            msg.what = 2
            msg.obj = "B"
            workHandler.sendMessage(msg)
        }
    }
}
