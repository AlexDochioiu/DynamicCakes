package com.github.alexdochioiu.main_feature.vm.test_utils

import com.github.alexdochioiu.core.rxjava.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulersProvider : SchedulersProvider {

    //todo this can be modified so we can track the requested schedulers. This can be then used to prove in unit tests
    // that the right schedulers are requested. However, this would probably couple the tests to implementation too
    // closely, hence refactoring will become rather painful
    override fun getIoScheduler(): Scheduler = Schedulers.trampoline()

}