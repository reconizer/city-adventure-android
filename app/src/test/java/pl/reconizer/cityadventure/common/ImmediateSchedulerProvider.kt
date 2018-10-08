package pl.reconizer.cityadventure.common

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import pl.reconizer.cityadventure.domain.common.ISchedulerProvider

class ImmediateSchedulerProvider : ISchedulerProvider {

    override fun provide(): Scheduler {
        return Schedulers.trampoline()
    }

}