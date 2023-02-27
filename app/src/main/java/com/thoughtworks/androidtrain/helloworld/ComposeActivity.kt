package com.thoughtworks.androidtrain.helloworld

import android.graphics.Paint.Align
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.helloworld.data.model.Sender
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.utils.FileUtils
import java.util.ArrayList

class ComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {}
    }

    @Composable
    fun Tweets(tweets: List<Tweet>) {
        LazyColumn {

            //顶部 item{}
            //内容
            items(tweets) { tweet ->
                tweetItem(tweet)
            }
            //底部
            item {
                BottomText()
            }
        }
    }

    @Composable
    fun tweetItem(tweet: Tweet) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(R.mipmap.avatar),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                tweet.sender?.nick?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.secondaryVariant,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = tweet.content, style = MaterialTheme.typography.body2
                )
            }
        }
    }

    @Composable
    fun BottomText() {
        Box(
            modifier = Modifier.fillMaxSize(), Alignment.BottomStart
        ) {
            Text(
                "到底了",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(60.dp)
                    .background(Color.LightGray)
            )
        }
    }


    @Preview
    @Composable
    fun PreviewTweets() {
        val gson = Gson()
//        val tweetsStr = FileUtils.readFileFromRaw(applicationContext,R.raw.tweets)

        val tweetsStr = """[
  {
    "id": 1,
    "content": "沙发！",
    "sender": {
      "username": "cyao",
      "nick": "Cheng Yao",
      "avatar": "https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/001.jpeg"
    }
  },
  {
    "id": 6,
    "content": "Unlike many proprietary big data processing platform different, Spark is built on a unified abstract RDD, making it possible to deal with different ways consistent with large data processing scenarios, including MapReduce, Streaming, SQL, Machine Learning and Graph so on. To understand the Spark, you have to understand the RDD. ",
    "sender": {
      "username": "weifan",
      "nick": "Wei Fan",
      "avatar": "https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/006.jpeg"
    }
  },
  {
    "id": 7,
    "content": "这是第二页第一条",
    "sender": {
      "username": "xinguo",
      "nick": "Xin Guo",
      "avatar": "https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/007.jpeg"
    }
  },
  {
    "id": 14,
    "content": "As a programmer, we should as far as possible away from the Windows system. However, the most a professional programmer, and very difficult to bypass Windows this wretched existence but in fact very real, then how to quickly build a simple set of available windows based test environment? See Qiu Juntao's blog. ",
    "sender": {
      "username": "jianing",
      "nick": "Jianing Zheng",
      "avatar": "https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/004.jpeg"
    }
  },
  {
    "id": 15,
    "content": "第9条！",
    "sender": {
      "username": "jianing",
      "nick": "Jianing Zheng",
      "avatar": "https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/006.jpeg"
    }
  },
  {
    "id": 16,
    "content": "第10条！",
    "sender": {
      "username": "xinguo",
      "nick": "Xin Guo",
      "avatar": "https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/007.jpeg"
    }
  },
  {
    "id": 17,
    "content": "楼下保持队形，第11条",
    "sender": {
      "username": "yanzi",
      "nick": "Yanzi Li",
      "avatar": "https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/008.jpeg"
    }
  },
  {
    "id": 18,
    "content": "第12条",
    "sender": {
      "username": "xinguo",
      "nick": "Xin Guo",
      "avatar": "https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/001.jpeg"
    }
  },
  {
    "id": 19,
    "content": "第13条",
    "sender": {
      "username": "yanzili",
      "nick": "Yanzi Li",
      "avatar": "https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/002.jpeg"
    }
  },
  {
    "id": 20,
    "content": "第14条",
    "sender": {
      "username": "xinguo",
      "nick": "Xin Guo",
      "avatar": "https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/003.jpeg"
    }
  },
  {
    "id": 22,
    "content": "- The data json will be hosted in http://thoughtworks-ios.herokuapp.com/- Nibs or storyboards are prohibited- Implement cache mechanism by you own if needed- Unit tests is appreciated.- Functional programming is appreciated- Deployment Target should be 7.0- Utilise Git for source control, for git log is the directly evidence of the development process- Utilise GCD for multi-thread operation- Only binary, framework or cocopods dependency is allowed. do not copy other developer's source code(*.h, *.m) into your project- Keep your code clean as much as possible",
    "sender": {
      "username": "hengzeng",
      "nick": "Huan Huan",
      "avatar": "https://thoughtworks-mobile-2018.herokuapp.com/images/user/avatar/004.jpeg"
    }
  }
]"""
        val tweets: List<Tweet> = gson.fromJson(
            tweetsStr, object : TypeToken<List<Tweet>>() {}.type
        )

        Tweets(tweets)
    }
}

