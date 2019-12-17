package app.components

import EmailIcon
import EmailShareButton
import ReactPlayer
import TelegramIcon
import TelegramShareButton
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import model.Video
import react.*
import react.dom.*
import styled.*

interface VideoPlayerProps : RProps {
    var video: Video
    var onWatchedButtonPressed: (Video) -> Unit
    var unwatchedVideo: Boolean
}

class VideoPlayer(props: VideoPlayerProps) : RComponent<VideoPlayerProps, RState>(props) {

    override fun RBuilder.render() {
        styledDiv {
            css {
                position = Position.absolute
                top = 10.px
                right = 10.px
            }
            h3 { +"${props.video.speaker}: ${props.video.title}" }
            styledButton {
                css {
                    display = Display.block
                    backgroundColor = if (props.unwatchedVideo) Color.lightGreen else Color.red
                }
                attrs { onClickFunction = { props.onWatchedButtonPressed(props.video) } }

                +"Mark as ${if (props.unwatchedVideo) "watched" else "unwatched"}"
            }

            styledDiv {
                css {
                    display = Display.flex
                    marginBottom = 10.px
                    marginTop = 10.px
                    marginLeft = 10.px
                }
                EmailShareButton {
                    attrs.url = props.video.videoUrl
                    EmailIcon {
                        attrs.size = 32
                        attrs.round = true
                    }
                }

                TelegramShareButton {
                    attrs.url = props.video.videoUrl
                    TelegramIcon {
                        attrs.size = 32
                        attrs.round = true
                    }
                }
            }

            ReactPlayer {
                attrs.url = props.video.videoUrl
            }
        }
    }
}

fun RBuilder.videoPlayer(handler: VideoPlayerProps.() -> Unit): ReactElement =
        child(VideoPlayer::class) { this.attrs(handler) }