package com.github.alexdochioiu.core.rxjava

import com.github.alexdochioiu.core.di.CoreScope
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@CoreScope
internal class SchedulersProviderImpl @Inject internal constructor() : SchedulersProvider {

    override fun getIoScheduler(): Scheduler = Schedulers.io()

}