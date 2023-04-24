package com.thoughtworks.androidtrain.helloworld

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.AppDatabase
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.SenderEntity
import com.thoughtworks.androidtrain.helloworld.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var dataSource: DataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            if (db.senderDao().getByUsername("Weii") == null) {
                db.senderDao().insert(
                    SenderEntity(
                        UUID.randomUUID().toString(), "Weii", "Weii", "own_pic", true
                    )
                )
            }
            if (!db.tweetDao().getCountTweet()) {
                dataSource.fetchRemoteTweets()
            }
        }

        setContent {
            NavGraph()
        }
    }
}
