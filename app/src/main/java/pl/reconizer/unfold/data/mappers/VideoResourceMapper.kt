package pl.reconizer.unfold.data.mappers

import pl.reconizer.unfold.data.entities.VideoResourceResponse
import pl.reconizer.unfold.domain.common.Mapper
import pl.reconizer.unfold.domain.entities.VideoResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoResourceMapper @Inject constructor() : Mapper<VideoResourceResponse, VideoResource>() {

    override fun map(from: VideoResourceResponse): VideoResource {
        return VideoResource(
                name = from.name,
                url = from.url
        )
    }

}