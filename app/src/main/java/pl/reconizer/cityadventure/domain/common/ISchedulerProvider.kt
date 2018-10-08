package pl.reconizer.cityadventure.domain.common

import io.reactivex.Scheduler

interface ISchedulerProvider {

    fun provide(): Scheduler

}