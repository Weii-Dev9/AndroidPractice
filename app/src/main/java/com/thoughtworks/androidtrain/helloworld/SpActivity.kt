package com.thoughtworks.androidtrain.helloworld

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.thoughtworks.androidtrain.helloworld.utils.SharedPreferenceUtils

class SpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sp_layout)
        val isKnown = SharedPreferenceUtils.readBoolean(this, "isKnown", false)
        isKnown?.let { initUI(it) }
    }

    @SuppressLint("SetTextI18n")
    private fun initUI(boolean: Boolean) {
        val btn:Button = findViewById(R.id.btn_know)
        val tv_text:TextView = findViewById(R.id.tv_text)
        if(!boolean){
            tv_text.text = "提示信息：\n点击\"知道了\"不再提示。"
            btn.setOnClickListener{
                SharedPreferenceUtils.writeBoolean(this,"isKnown",true)
                reFresh(tv_text, btn)
            }
        }else{
            reFresh(tv_text,btn)
        }
    }

    private fun reFresh(tv_text: TextView, btn: Button) {
        tv_text.text = "欢迎您回来"
        btn.visibility = View.GONE
    }

}