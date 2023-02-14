package com.thoughtworks.androidtrain.helloworld.utils.schedulers

import io.reactivex.rxjava3.core.Scheduler

interface SchedulerProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
}