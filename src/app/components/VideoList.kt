package app.components

import kotlinx.html.js.onClickFunction
import model.Video
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.p

interface VideoListProps : RProps {
    var videos: List<Video>
    var selectedVideo: Video?
    var onSelectVideo: (Video) -> Unit
}

class VideoListComponent : RComponent<VideoListProps, RState>() {

    override fun RBuilder.render() = props.videos.forEach { video ->
        p {
            key = video.id.toString()
            attrs { onClickFunction = { props.onSelectVideo(video) } }
            if (video == props.selectedVideo) {
                +"▶ "
            }
            +"${video.speaker}: ${video.title}"
        }
    }

}