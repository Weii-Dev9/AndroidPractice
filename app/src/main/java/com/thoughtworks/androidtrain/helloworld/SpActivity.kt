package com.thoughtworks.androidtrain.helloworld

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.thoughtworks.androidtrain.helloworld.utils.SharedPreferenceUtils

private const val KEY = "isKnown"
class SpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sp_layout)
        val isKnown = SharedPreferenceUtils.readBoolean(this, KEY, false)
        isKnown?.let { initUI(it) }
    }

    @SuppressLint("SetTextI18n")
    private fun initUI(isKnown: Boolean) {
        val btn:Button = findViewById(R.id.btn_know)
        val tvText:TextView = findViewById(R.id.tv_text)
        if(!isKnown){
            tvText.text = resources.getString(R.string.tip)
            btn.setOnClickListener{
                SharedPreferenceUtils.writeBoolean(this, KEY,true)
                refresh(tvText, btn)
            }
        }else{
            refresh(tvText,btn)
        }
    }

    private fun refresh(tv_text: TextView, btn: Button) {
        tv_text.text = resources.getString(R.string.welcome_back)
        btn.visibility = View.GONE
    }

}