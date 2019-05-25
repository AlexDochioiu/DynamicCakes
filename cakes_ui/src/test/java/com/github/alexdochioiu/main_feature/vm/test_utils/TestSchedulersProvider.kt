package com.github.alexdochioiu.main_feature.vm.test_utils

import com.github.alexdochioiu.core.rxjava.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulersProvider : SchedulersProvider {

    override fun getIoScheduler(): Scheduler = Schedulers.trampoline()

}