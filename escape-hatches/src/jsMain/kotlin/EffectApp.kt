import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.video
import react.useEffect
import react.useEffectOnce
import react.useRef
import react.useState
import web.html.HTMLVideoElement

external interface VideoPlayerProps : Props {
    var src: String
    var isPlaying: Boolean
}

val VideoPlayer = FC<VideoPlayerProps> { props ->
    val ref = useRef<HTMLVideoElement>(null)

    useEffect(props.isPlaying) {
        if (props.isPlaying) {
            console.log("Calling video.play()")
            ref.current!!.playAsync()
        } else {
            console.log("Calling video.pause()")
            ref.current!!.pause()
        }
    }

    video {
        this.ref = ref
        src = props.src
    }
}

val EffectApp = FC {
    var isPlaying: Boolean by useState(false)
    var text: String by useState("")

    input {
        value = text
        onChange = { text = it.target.value }
    }
    button {
        onClick = { isPlaying = !isPlaying }
        +if (isPlaying) "Pause" else "Play"
    }
    VideoPlayer {
        this.isPlaying = isPlaying
        src = "https://interactive-examples.mdn.mozilla.net/media/cc0-videos/flower.mp4"
    }
}

class Connection {
    fun connect() {
        console.log("✅ Connecting...")
    }

    fun disconnect() {
        console.log("❌ Disconnected.")
    }
}

fun createConnection(): Connection {
    return Connection()
}

val ChatRoom = FC {
    useEffectOnce {
        val connection = createConnection()
        connection.connect()
        cleanup {
            connection.disconnect()
        }
    }

    h1 { +"Welcome to the chat!" }
}
