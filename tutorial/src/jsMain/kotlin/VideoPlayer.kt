import emotion.react.css
import react.FC
import react.Props
import react.create
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h3
import web.cssom.Display
import web.cssom.NamedColor
import web.cssom.Position
import web.cssom.px

external interface VideoPlayerProps : Props {
    var video: Video
    var onWatchedButtonPressed: (Video) -> Unit
    var unwatchedVideo: Boolean
}

val VideoPlayer = FC<VideoPlayerProps> { props ->
    div {
        css {
            position = Position.absolute
            top = 10.px
            right = 10.px
        }
        EmailShareButton {
            url = props.video.videoUrl
            children = EmailIcon.create {
                size = 32
                round = true
            }
        }
        TelegramShareButton {
            url = props.video.videoUrl
            children = TelegramIcon.create {
                size = 32
                round = true
            }
        }
        h3 {
            +"${props.video.speaker}: ${props.video.title}"
        }
        button {
            css {
                display = Display.block
                background = if (props.unwatchedVideo) NamedColor.lightgreen else NamedColor.red
            }
            onClick = {
                props.onWatchedButtonPressed(props.video)
            }
            if (props.unwatchedVideo) {
                +"Mark as watched"
            } else {
                +"Mark as unwatched"
            }
        }
        ReactPlayer {
            url = props.video.videoUrl
            controls = true
        }
    }
}
