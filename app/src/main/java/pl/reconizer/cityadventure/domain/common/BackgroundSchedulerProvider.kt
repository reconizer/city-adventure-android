package pl.reconizer.cityadventure.domain.common

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class BackgroundSchedulerProvider : ISchedulerProvider {

    override fun provide(): Scheduler {
        return Schedulers.io()
    }

}