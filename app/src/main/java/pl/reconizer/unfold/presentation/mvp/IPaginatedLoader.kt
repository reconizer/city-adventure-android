package pl.reconizer.unfold.presentation.mvp

import io.reactivex.Single
import pl.reconizer.unfold.domain.entities.ICollectionContainer

interface IPaginatedLoader<T> {

    fun load(page: Int): Single<ICollectionContainer<T>>

}
