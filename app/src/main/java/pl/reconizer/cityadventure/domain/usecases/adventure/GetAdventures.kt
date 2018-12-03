package pl.reconizer.cityadventure.domain.usecases.adventure

import io.reactivex.Single
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.Position
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository

class GetAdventures(
        private val adventureRepository: IAdventureRepository
) {

    operator fun invoke(position: Position): Single<List<Adventure>> {
        return adventureRepository.getAdventures(position.lat, position.lng)
    }

}