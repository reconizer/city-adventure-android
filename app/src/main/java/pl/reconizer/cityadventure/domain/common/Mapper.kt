package pl.reconizer.cityadventure.domain.common

import io.reactivex.Single

abstract class Mapper<in E, T> {

    abstract fun map(from: E): T

    fun asyncMap(from: E): Single<T> {
        return Single.fromCallable { map(from) }
    }

}