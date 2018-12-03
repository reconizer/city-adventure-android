package pl.reconizer.cityadventure.domain.usecases.adventure

import io.reactivex.Single
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository

class GetAdventureStartPoint(
        private val adventureRepository: IAdventureRepository
) {

    operator fun invoke(adventureId: String): Single<AdventureStartPoint> {
        return adventureRepository.getAdventure(adventureId)
    }

}