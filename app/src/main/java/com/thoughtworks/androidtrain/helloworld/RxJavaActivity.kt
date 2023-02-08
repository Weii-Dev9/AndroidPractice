package com.thoughtworks.androidtrain.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RxJavaActivity : AppCompatActivity() {
    private val composite: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rx_java_layout)
        val btn: Button = findViewById(R.id.rx_btn)
        btn.setOnClickListener {
            initData()
        }
    }


    private fun initData() {
        val btn: Button = findViewById(R.id.rx_btn)
        val subscribe: Disposable = Observable.create(ObservableOnSubscribe<String> { emmiter ->
            var i = -1;
            while (i < 11) {
                i += 1
                emmiter.onNext(i.toString())
                SystemClock.sleep(1000)
            }
            emmiter.onComplete()
        }).map { s -> "the number is $s" }
            //io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率
            .subscribeOn(Schedulers.io())//指定 subscribe() 发生在 IO 线程【数据处理过程在io】
            .observeOn(AndroidSchedulers.mainThread())//指定的操作（Subscriber 的回调）将在 Android 主线程运行【结果使用在主线程】
            //observer【通过subscribe()与被观察者i创造联系，i一变化，这里就触发】
            .subscribe { i ->
                btn.text = i
                if (btn.text == "the number is 11") {
                    btn.isEnabled = true
                    btn.text = "Click"
                }
            }
        btn.isEnabled = false
        composite.add(subscribe)
    }

    override fun onDestroy() {
        super.onDestroy()
        composite.clear()
    }
}
