package pl.reconizer.cityadventure.data.mappers

import pl.reconizer.cityadventure.data.entities.VideoResourceResponse
import pl.reconizer.cityadventure.domain.common.Mapper
import pl.reconizer.cityadventure.domain.entities.VideoResource
import javax.inject.Inject

class VideoResourceMapper @Inject constructor() : Mapper<VideoResourceResponse, VideoResource>() {

    override fun map(from: VideoResourceResponse): VideoResource {
        return VideoResource(
                name = from.name,
                url = from.url
        )
    }

}