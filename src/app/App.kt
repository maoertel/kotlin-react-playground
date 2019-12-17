package app

import app.components.VideoListComponent
import app.components.VideoListProps
import app.components.videoPlayer
import data.VideoRepository
import data.VideoRepositoryImpl
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import model.Video
import react.*
import react.dom.div
import react.dom.h1
import react.dom.h3

interface AppState : RState {
    var currentVideo: Video?
    var unwatchedVideos: List<Video>
    var watchedVideos: List<Video>
}

class App : RComponent<RProps, AppState>() {

    private val videoRepository: VideoRepository = VideoRepositoryImpl()

    override fun AppState.init() {
        unwatchedVideos = listOf()
        watchedVideos = listOf()

        val mainScope = MainScope()
        mainScope.launch {
            val videos = videoRepository.fetchVideos()
            setState { unwatchedVideos = videos }
        }
    }

    override fun RBuilder.render() {
        h1 { +"KotlinConf Explorer" }

        div {
            h3 { +"Videos to watch" }
            videoList {
                videos = state.watchedVideos
                selectedVideo = state.currentVideo
                onSelectVideo = { video -> setState { currentVideo = video } }
            }

            h3 { +"Videos watched" }
            videoList {
                videos = state.unwatchedVideos
                selectedVideo = state.currentVideo
                onSelectVideo = { video -> setState { currentVideo = video } }
            }
        }

        state.currentVideo?.let { currentVideo ->
            videoPlayer {
                video = currentVideo
                unwatchedVideo = currentVideo in state.unwatchedVideos
                onWatchedButtonPressed = {
                    if (video in state.unwatchedVideos)
                        setState {
                            unwatchedVideos -= video
                            watchedVideos += video
                        }
                    else
                        setState {
                            watchedVideos -= video
                            unwatchedVideos += video
                        }
                }
            }
        }
    }

    private fun RBuilder.videoList(handler: VideoListProps.() -> Unit): ReactElement =
            child(VideoListComponent::class) { this.attrs(handler) }

}