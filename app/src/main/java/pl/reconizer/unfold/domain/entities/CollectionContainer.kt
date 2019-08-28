package pl.reconizer.unfold.domain.entities

data class CollectionContainer<T>(
        override val collection: List<T>
) : ICollectionContainer<T>