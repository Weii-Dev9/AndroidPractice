package com.thoughtworks.androidtrain.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.helloworld.adapters.TweetAdapter
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet

class TweetsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tweets_layout)
        initUI()
    }

    private fun initUI() {
        // Lookup the recyclerview in activity layout
        val rvContents = findViewById<RecyclerView>(R.id.rvContent)
        // Initialize tweets
        val gson = Gson()
        val tweet1 = """[{"content":"Jack", "sender":{"username":"Wang","nick":"Wei", 
                        avatar:"http://hawksaloft.org/wp-content/uploads/2012/08/614612316_20090805-_mg_3411-rufous-hummingbird-5x7.jpg"}},
                         {"content":"Jack", "sender":{"username":"Wang","nick":"Wei",
                        avatar:"https://alifei01.cfp.cn/creative/vcg/800/new/VCG211330609486-DIM.jpg"}},
                         {"content":"Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smartphones and tablets.", "sender":{"username":"Wang","nick":"Wei",
                         avatar:"https://alifei01.cfp.cn/creative/vcg/800/new/VCG41N1354790729.jpg"}},
                         {"content":"Hellos", "sender":{"username":"Wang","nick":"Wei",
                         avatar:"https://tenfei02.cfp.cn/creative/vcg/800/new/VCG41N1318342788.jpg"}}
                         ]"""
            .trimMargin()
        val tweets2: ArrayList<Tweet> =
            gson.fromJson(tweet1, object : TypeToken<List<Tweet?>?>() {}.type)

//        val tw1 = DataModel.Tweet(content = "Jack",
//            sender = Sender(username = "Wang", nick = "Wei", ""),
//            images = null,
//            comments = null,
//            error = null,
//            unknownError = "")
//        val tw2 = tw1.copy(content = "Tom", error = "yo")
//        val tw3 = tw1.copy(content = "A")
//        val tw4 = tw1.copy(content = "b")
//        val tw5 = tw1.copy(content = "Android is a mobile operating system based on a modified version of the Linux kernel and other\n" +
//                "open source software, designed primarily for touchscreen mobile devices such as smartphones and\n" +
//                "tablets.")
//        val tw6 = tw1.copy(content = "d")
//        val tw7 = tw1.copy(content = "e")
//        val tw8 = tw1.copy(content = "e")
//        val tw9 = tw1.copy(content = "e")
//        val tw10 = tw1.copy(content = "e")
//        val tw11 = tw1.copy(content = "Android is a mobile operating system based on a modified version of the Linux kernel and other\n" +
//                "open source software, designed primarily for touchscreen mobile devices such as smartphones and\n" +
//                "tablets.")
//        val tw12 = DataModel.Bottom(text = "到底了")
//        val tweets2: List<DataModel> = listOf(tw1,tw2,tw3,tw4,tw5,tw6,tw7,tw8,tw9,tw10,tw11,tw12)

        // Create adapter passing in the sample user data
        val adapter = TweetAdapter(tweets2,applicationContext)
        // Attach the adapter to the recyclerview to populate items
        rvContents.adapter = adapter
        // Set layout manager to position the items
        rvContents.layoutManager = LinearLayoutManager(this)
        // That's all!
    }
}